Extremely shocking performance
Single machine easily supports a million-level tcp long connection, completely open the industry C1000K troubles;
At the highest time, it can send and receive 5 million business messages per second, about 165M (version 1.6.9 data, want to verify, there are verification steps later, im 1.7.1 version of im can not be used for testing framework due to the addition of many business functions performance)
Extremely thoughtful built-in API
Built-in heartbeat detection
Built-in heartbeat
Various convenient binding APIs, such as binding groups for message grouping, binding users for sending messages to specified users, etc.
Various convenient sending APIs, such as sendToGroup, sendToUser, etc.
Blocking send and asynchronous send are just a matter of bSendXxx and sendXxx, which is extremely convenient
Client built-in disconnection automatic reconnection function
The monitoring data of the connection is done to the extreme: how many bytes of the service packet are sent, received, processed, and all monitored.
Built-in monitoring for IP: For example, how many connections are there in an IP, how many messages are sent in a certain period of time, etc., all of which are good for attack prevention.
