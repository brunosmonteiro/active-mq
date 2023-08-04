There are mainly two ways to create destinations.

## Configuration Files
We can include specific instructions for their creation in the /opt/activemq/conf/activemq.xml file. Something like this:
```
<broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}">
    <!-- Define your queues and topics here -->
    <destinations>
        <queue physicalName="my-queue" />
        <topic physicalName="my-topic" />
    </destinations>

    <!-- Other broker configurations go here -->
</broker>
```
This way, the broker can be easily configured to always exist, regardless of it having any connections or messages.

## Programmatically Definition
The way we used to create them in our examples if the easiest and most common approach. You create objects that mirror
destinations from the broker, within your connection's session. If the destination does not physically exist, whenever a
producer or consumer try to connect to it, it will be created.

When there are no resources allocated to the specific destination (topics, queues, connections), it can be garbage 
collected.
```
ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
Connection connection = connectionFactory.createConnection();
connection.start();
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
Topic topic = session.createTopic("my-topic");
Queue queue = session.createQueue("my-queue");
```

The first approach is desired when you want more stability and guarantees for your brokers, but the second is also
perfectly suitable for applications.
