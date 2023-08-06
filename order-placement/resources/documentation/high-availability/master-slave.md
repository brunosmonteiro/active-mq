## Introduction
In a Master-Slave configuration with ActiveMQ, there's typically one master broker and one or more slave brokers. 
The master broker handles all message processing as long as it's available. If it goes down, one of the slaves takes 
over. This can help you achieve high availability (HA) and potentially also increase your system's capacity to handle 
messages.

ActiveMQ does offer other high availability (HA) and scaling mechanisms beyond the traditional master-slave configuration. 
Here's an overview:
1. **Network of Brokers**: ActiveMQ allows you to set up a network of brokers that are connected to each other. This 
enables load balancing and distribution of messages across different brokers. In this setup, producers can send messages 
to any broker in the network, and consumers can consume messages from any broker. If a broker fails, the others can 
continue to handle the load. The network of brokers can be configured in a mesh, hub-and-spoke, or other topology depending 
on your requirements. The configuration can be done via the networkConnectors element in the `activemq.xml` file. 
2. **Composite Destinations**: Composite destinations allow messages sent to a single logical destination to be forwarded to
multiple physical destinations. This can be used in combination with a network of brokers to achieve load balancing and 
fault tolerance. 
3. **Active/Active Configuration with Shared Message Store**: Another approach is to set up multiple ActiveMQ instances 
in an active/active configuration where they share the same message store (such as a database). This allows all instances 
to be actively processing messages simultaneously, providing both high availability and load balancing. 
4. **Integration with Apache ZooKeeper**: ActiveMQ can be integrated with Apache ZooKeeper to manage the cluster 
configuration and provide leader election capabilities. This can be used to dynamically manage the cluster and handle 
broker failures. 

## Initial Configuration
Here is an initial possible implementation for a master-slave configuration, using a docker-compose.yml file:
```
version: '3'
services:
  master-broker:
    image: rmohr/activemq
    volumes:
      - shared-data:/opt/activemq/data
    ports:
      - "61616:61616"
      - "8161:8161"
    environment:
      - ACTIVEMQ_OPTS=-Dactivemq.brokerid=master

  slave-broker:
    image: rmohr/activemq
    volumes:
      - shared-data:/opt/activemq/data
    ports:
      - "61617:61616"
      - "8162:8161"
    environment:
      - ACTIVEMQ_OPTS=-Dactivemq.brokerid=slave

volumes:
  shared-data:
```
This is a simple file that sets two brokers with access to the same file system.

**Notes**:
- The `ACTIVEMQ_OPTS` environment variable to set a unique broker ID for each instance. This isn't strictly necessary 
for the shared file system approach, but it might help you keep track of which broker is which;
- The file does not guarantee failover. If the master fails, the applications connected to it would need to manually
change their connection to the other one;
- We are not defining any lock on the shared volume, nothing is stopping from multiple brokers accessing and changing it.

## File System Locking
In a typical master-slave setup, you'd need to configure a locking mechanism to ensure that only one broker (the master) 
can write to the shared storage at a time. If the master fails, the lock is released, and the slave can take over.

In ActiveMQ, this can be configured using a shared file system lock or a database lock. The configuration would be done 
in the `activemq.xml` file.

Here's an example of how you might configure a shared file system lock:
```
<broker>
    ...
    <persistenceAdapter>
        <kahaDB directory="${activemq.data}/kahadb" lockKeepAlivePeriod="5000">
            <locker>
                <shared-file-locker lockFile="activemq-lock"/>
            </locker>
        </kahaDB>
    </persistenceAdapter>
    ...
</broker>
```
This configuration uses a shared file (`activemq-lock`) to control the lock. The master broker obtains the lock, and if it 
fails, the slave broker can obtain the lock and become the master. The shared docker volume in the docker-compose.yml file
would take care of it being accessible to all brokers.

## Failover
To achieve failover in its simplest form (transparent handling of failed components by internal routing), we could 
upgrade our docker-compose file to this.
```
version: '3'
services:
  message-broker-master:
    image: rmohr/activemq
    environment:
      - ACTIVEMQ_OPTS=-Dactivemq.brokerid=master
    volumes:
      - activemq-data:/data/activemq

  message-broker-slave:
    image: rmohr/activemq
    environment:
      - ACTIVEMQ_OPTS=-Dactivemq.brokerid=slave
    volumes:
      - activemq-data:/data/activemq

  haproxy:
    image: haproxy:2.3
    depends_on:
      - message-broker-master
      - message-broker-slave
    volumes:
      - ./haproxy.cfg:/usr/local/etc/haproxy/haproxy.cfg
    ports:
      - "61617:61616"

volumes:
  activemq-data:
```

With this file in hands, we will use haproxy to handle the internal routing to the appropriate broker. Since we will not
need to directly access any of the brokers, we do not need to expose their ports.

For the proxy to work, we need to correctly define the `haproxy.cfg` file.
```
defaults
    mode tcp
    timeout connect 5s
    timeout client 50s
    timeout server 50s

frontend activemq
    bind *:61616
    default_backend activemq_servers

backend activemq_servers
    option tcp-check
    server master-broker message-broker-master:61616 check inter 2s fall 3
    server slave-broker message-broker-slave:61616 check inter 2s fall 3 backup
```

With this file, haproxy treats master as the primary broker and the slave as backup. Notice the use of the keyword `backup`,
without it, there would be a round-robin load balancing. The proxy can access both of the container's through their
service names because docker compose provides a default network with this ability to do so.

- `check inter 2s` means the health is checked every two seconds.
- `fall 3` means that the proxy will be changed if there are three consecutive fails in the health check.