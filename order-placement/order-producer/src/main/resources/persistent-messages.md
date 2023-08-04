For you to send messages that are supposed to be saved in a storage, the following change would be necessary:

## Producer changes
The delivery mode is per producer.

This, without any further configuration, will use the default KahaDB storage provided by ActiveMQ.

```
producer.setDeliveryMode(DeliveryMode.PERSISTENT);
```

## Storage options for the broker
Here are the possible options:

- **KahaDB**: The default and most commonly used persistence mechanism in ActiveMQ is KahaDB. It's a file-based store 
that organizes data in journal files and index files. The journal files store the actual messages, while the index files 
enable quick look-up. When a message is sent as persistent, it's written to a journal file, and the corresponding index 
is updated.
- **JDBC-based persistence**: ActiveMQ can be configured to use a relational database for persistence. It can connect to
popular databases like MySQL, PostgreSQL, Oracle, etc. The messages and related data are stored in database tables.
- **LevelDB**: Another file-based persistence option that used to be available, though its support was deprecated and 
removed in later versions of ActiveMQ.
- **Custom persistence adapters**: You can write your own custom persistence adapter if you have a particular need 
that's not met by the built-in options.

As a recap of journals, a journal is a type of storage model that can be implemented using different underlying storage 
mechanisms. It's a concept rather than a specific technology. With ActiveMQ's KahaDB, for example, the journal is a 
series of log files written to disk. These files contain the actual message data, and they're written in an append-only 
fashion for performance reasons.

Other systems might implement journaling using different underlying technologies, such as a relational database or 
distributed file system. The key idea is the same, though: messages are written to a durable store in the order they 
arrive, providing a reliable record that can be used to recreate the system's state if needed.

Here is a few reasons to why choose one or the other:
- **KahaDB** (or other file-based stores):
-- **Performance**: Typically offers high throughput and low latency since it's optimized for append-only writes.
-- **Complexity**: Can be simpler to set up and maintain, particularly in environments where a separate RDBMS might be overkill.
-- **Reliability**: Provides good durability guarantees, assuming that the underlying file system is itself reliable.
-- **Scalability**: May have limitations in terms of scaling horizontally across multiple nodes, though this can often 
be mitigated through careful design and configuration.

- **Relational Database (e.g., MySQL, Postgres):**
-- **Performance**: May not be as fast as file-based stores for certain workloads, since RDBMSs are not typically 
optimized for append-only write patterns.
-- **Complexity**: Requires more setup and ongoing maintenance, such as schema management, index tuning, etc.
-- **Reliability**: Can be extremely reliable, particularly if you're using a well-known and thoroughly tested RDBMS.
-- **Scalability**: Potentially offers more options for scaling, both vertically (e.g., by adding more resources to a 
single database server) and horizontally (e.g., through replication and sharding).

Internally, the differences would manifest in how messages are written, read, and managed. With KahaDB, the broker is 
dealing directly with files on disk, and it has been optimized for this specific use case. With an RDBMS, the broker 
would interact with the database through SQL or a similar query language, and there would be an additional layer 
of abstraction between the broker and the physical storage.

For most of ActiveMQ's storage solutions, direct access is possible but generally not recommended for normal operations.

## How to change the storage used
The base image does not have direct support for changing the type of storage used. To change it, in one way or another,
we need to change the contents of the /opt/activemq/conf/activemq.xml file. To achieve this, we have a few options.

### Run the broker as a standalone application
We can deploy the broker as its own application. This would programmatically change of file mentioned above using
native methods provided by the library. 

This is an example with MySQL.
```
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;
import org.apache.commons.dbcp2.BasicDataSource;

public class ActiveMQWithJDBC {
    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/activemq");
        dataSource.setUsername("yourusername");
        dataSource.setPassword("yourpassword");

        JDBCPersistenceAdapter persistenceAdapter = new JDBCPersistenceAdapter();
        persistenceAdapter.setDataSource(dataSource);

        broker.setPersistenceAdapter(persistenceAdapter);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
}
```

### Create a Dockerfile from the base image
Probably the most scalable and likely to be used in production approach is to create an image form the base image provided
by rmohr. This way, we can specifically replace the file with our own.

```
FROM rmohr/activemq
COPY ./activemq.xml /opt/activemq/conf/activemq.xml
```

Our own file should include the following, using MySQL for example:
```
<persistenceAdapter>
    <jdbcPersistenceAdapter dataSource="#mysql-ds"/>
</persistenceAdapter>

<bean id="mysql-ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
  <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
  <property name="url" value="jdbc:mysql://localhost/activemq"/>
  <property name="username" value="yourusername"/>
  <property name="password" value="yourpassword"/>
</bean>
```

### Use volumes along docker-compose
This is similar to the last approach and still suitable for small projects or development environments. We use the same
logic but now applied to our original docker-compose file.

```
version: '3'
services:
    message-broker:
        image: rmohr/activemq
        ports:
        - "61616:61616"
        - "8161:8161"
        volumes:
        - .activemq.xml:/opt/activemq/conf/activemq.xml
```
