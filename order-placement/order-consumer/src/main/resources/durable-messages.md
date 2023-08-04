In a typical publish-subscribe model with topics, if a consumer (subscriber) is not online or actively listening to the
topic when a message is published, the message will be missed by that consumer. This behavior aligns with the notion of
a "broadcast" where messages are sent to all currently active subscribers.

ActiveMQ, like many other messaging systems, supports "durable" subscriptions. With a durable subscription, the broker
retains messages sent to the topic, even if a particular subscriber is not currently connected. When the subscriber 
reconnects, it receives all the messages that were sent to the topic while it was disconnected.

To create a durable subscription in ActiveMQ, you would typically need to set a client ID on the connection and a 
subscriber name on the subscription itself. Here's a rough example:

```
connection.setClientID("myClientID");
session.createDurableSubscriber(topic, "mySubscriptionName");
```

This tells the broker to keep track of messages sent to the specified topic on behalf of this particular subscriber, 
identified by the combination of the client ID and the subscription name. This causes implications for resource usage
and performance, as there is extra effort to keep track of consumers and messages, as well as to keep more journals
running, as messages are only deleted when there is not any pending consumer.

The mechanism for tracking which messages have been delivered to which subscribers can be somewhat complex, as it 
involves managing the state of multiple subscribers and the potential for subscribers to connect and disconnect at any 
time. 