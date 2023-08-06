Batching is a mechanism where the producer of messages holds onto several messages before forwarding them to the broker.
By sending a single request that includes multiple messages to the broker, the client reduces network bandwidth at the 
cost of a bit of latency. For those clients that support it, a single batch can be compressed into a single message.

## Batching and Transactions
Batching and transactions are closely related but distinct concepts. While transactions provide a way to group a set of 
related operations together, ensuring that they either all succeed or all fail, batching is about sending or receiving 
multiple messages at once to improve efficiency.

You can implement batching without transactions by sending or receiving multiple messages in a single operation. However,
without transactions, there's no guarantee that the entire batch will be processed atomically. If a failure occurs partway
through processing a batch, there's no built-in mechanism to roll back the already processed messages.

In the case of ActiveMQ, the built-in functionality for grouping messages together and treating them as a single unit is 
provided through transactions. By creating a transactional session and sending multiple messages within that transaction, 
you ensure that they are treated as a single unit from the perspective of both the producer and the broker. If you commit 
the transaction, all the messages are sent; if you roll back the transaction, none of them are.

There is no built-in support for ActiveMQ for batching without transactions.