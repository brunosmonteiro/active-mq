In Jakarta Messaging (JMS), acknowledgment modes define how a session acknowledges the receipt of messages. There are 
three primary acknowledgment modes:

- `AUTO_ACKNOWLEDGE`: With this mode, the session automatically acknowledges the receipt of a message when it has
successfully returned from a call to receive, or when the MessageListener it has called to process the message returns 
successfully. If a message isn't processed successfully, it won't be acknowledged, and it may be redelivered later. 
This is the simplest acknowledgment mode to use but may result in duplicate messages if processing fails after the 
message is acknowledged.

- `CLIENT_ACKNOWLEDGE`: In this mode, the client acknowledges messages explicitly by calling the acknowledge method on 
the message. This allows the client to have finer control over acknowledgment and can be used to acknowledge one or more 
received messages at once. If the client fails to acknowledge a message, it can be redelivered.

- `DUPS_OK_ACKNOWLEDGE`: This mode is similar to `AUTO_ACKNOWLEDGE`, but it allows the session to lazily acknowledge the 
receipt of messages. This can improve performance by reducing the number of acknowledgment signals sent, especially over 
a network. The trade-off is that it may lead to duplicate messages if a failure occurs, hence the name "DUPS_OK" 
(duplicates are okay).

To choose the specific mode, we change how we create our consumer session:
```
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
```