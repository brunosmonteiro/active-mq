## Transactions in the Consumer
In the consumer side, transactions govern the acknowledgment of messages. If a consumer is part of a transaction, it 
can consume messages, but those messages will not be acknowledged (and thus removed from the queue or topic) until the 
transaction is committed. If the transaction is rolled back, the messages are returned to the queue or topic and can be 
consumed again.

1. **Begin Transaction**: Like with the producer, a transactional session is created for the consumer by setting the 
first parameter to true when creating the session.
2. **Message Processing**:  The consumer processes the message within the transaction. If the processing is successful,
the acknowledgment of receipt can be included in the transaction.
3. **Commit Transaction**: If processing is successful, the consumer commits the transaction using session.commit(), 
which sends an acknowledgment to the broker that the message was successfully processed.
4. **Rollback Transaction**: If something goes wrong during processing, the consumer can rollback the transaction using 
session.rollback(). This will cause the message to be redelivered (based on redelivery policy), as the broker has not 
received an acknowledgment.
   Message listeners in JMS are not typically used in conjunction with transactions, and handling transactions within a message listener can be complex.

In a transactional scenario, you would usually consume messages using the `receive()` method rather than a listener, and 
then explicitly call commit or rollback on the session, depending on whether you want to acknowledge or reject the messages.
Message listeners in JMS are not typically used in conjunction with transactions, and handling transactions within a 
message listener can be complex.

A simple example could be:
```
Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
Queue queue = session.createQueue("notification-queue");
MessageConsumer consumer = session.createConsumer(queue);

while (true) { // Infinite loop to keep receiving messages
    try {
        Message message = consumer.receive();
        if (message instanceof TextMessage textMessage) {
            System.out.println("Received message: " + textMessage.getText());
            // Commit the transaction, acknowledging the message
            session.commit();
        }
    } catch (Exception e) {
        System.out.println("Caught exception: " + e);
        // Roll back the transaction, rejecting the message
        session.rollback();
    }
}
```