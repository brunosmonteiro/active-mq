## Dead Letter Queue (DLQ)
The DLQ serves as a special queue that stores messages that could not be processed or delivered to their intended 
destination. They prevent endless redelivery attempts of "poison" messages that could not be processed, provide insights
into problematic messages for debugging purposes, and allow for the possibility of manual intervention or reprocessing by
appending additional information to the message before sending it to the queue.

### Persistence and Storage
The fate of a message in the DLQ depends on its persistence status. Persistent messages in ActiveMQ are typically stored
using KahaDB by default (or any configured storage for regular messages), allowing them to survive broker restarts. 
Non-persistent messages exist only in memory and are lost upon broker restarts or failures.

### Configuration and Customization
ActiveMQ's DLQ configuration is highly versatile, allowing for alternatives such as JDBC-based storage and configurable 
properties such as the maximum number of redelivery attempts and specific policies to define which DLQ to use. This can
be done programmatically or in the activemq.xml configuration file.

Example in the activemq.xml file
```
<redeliveryPolicyMap>
    <redeliveryPolicyEntries>
        <!-- Example of setting maximum redeliveries for a specific queue -->
        <redeliveryPolicy queue="my.queue" maximumRedeliveries="5" />
    </redeliveryPolicyEntries>
</redeliveryPolicyMap>
```





