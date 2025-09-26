# Peer to Peer (P2P) Network Project

A peer-to-peer (P2P) messaging system implemented in Java using Maven for build. 

Nodes can send and receive serializable messages over sockets.
Ack messages are sent back to the sender after delivery of a message.

When the ack message is received, the routing (path) is updated.

For example, if a there are five nodes 1, 2, 3, 4, 5 - connected as:

    (1)--(2)--(4)

    (1)--(3)--(5)--(4)

Node 1 sends a message to node 4.  

The logs show the output from running the TestMostEfficientRoute::testSendAndReturnAckMessage unit test.

Because each node has it's own thread, the log output may not be sequential.  The best way to read the output is to search for "Received message for self".

When there is no favourite route, the message will be sent along both routes:

    12:57:26.746 [Thread-3] INFO com.chocksaway.p2p.Node -- [node4:8014] Received message for self: Hello, Node 4![BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]

    12:57:26.756 [Thread-3] INFO com.chocksaway.p2p.Node -- [node4:8014] Received message for self: Hello, Node 4![BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node3', hostname='127.0.0.1', port=8013}, BaseNode{name='node5', hostname='127.0.0.1', port=8015}]

The search for "Ack is for self:".  This is the acknowledgment message being received back at node 1.

    12:57:26.757 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Ack is for self: Ack from node4

    12:57:26.761 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Ack is for self: Ack from node4

The next time a message is sent from node1 to node4, the message will be sent "along" the most efficient route (shortest number of hops):
    
    (1)--(2)--(4)

With output:

    12:57:28.763 [Thread-3] INFO com.chocksaway.p2p.Node -- [node4:8014] Received message for self: Hello, Node 4![BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]

The complete log output is here:
```
12:57:26.605 [main] INFO com.chocksaway.p2p.Node -- Starting: node1
12:57:26.606 [main] INFO com.chocksaway.p2p.Node -- Starting: node2
12:57:26.607 [main] INFO com.chocksaway.p2p.Node -- Starting: node3
12:57:26.607 [main] INFO com.chocksaway.p2p.Node -- Starting: node4
12:57:26.607 [main] INFO com.chocksaway.p2p.Node -- Starting: node5
12:57:26.737 [Thread-1] INFO com.chocksaway.p2p.Node -- [node2:8012] Forwarding message to: node4 
12:57:26.746 [Thread-3] INFO com.chocksaway.p2p.Node -- [node4:8014] Received message for self: Hello, Node 4![BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]
12:57:26.746 [Thread-1] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node2:8012 to node4:8014: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]}
12:57:26.746 [main] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node1:8011 to node2:8012: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}]}
12:57:26.750 [main] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node1:8011 to node3:8013: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}]}
12:57:26.750 [Thread-2] INFO com.chocksaway.p2p.Node -- [node3:8013] Forwarding message to: node4     
12:57:26.752 [Thread-2] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node3:8013 to node5:8015: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node3', hostname='127.0.0.1', port=8013}]}
12:57:26.752 [Thread-4] INFO com.chocksaway.p2p.Node -- [node5:8015] Forwarding message to: node4
12:57:26.753 [Thread-3] WARN com.chocksaway.p2p.route.Router -- Node node4 not in path [BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]
12:57:26.753 [Thread-4] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node5:8015 to node4:8014: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node3', hostname='127.0.0.1', port=8013}, BaseNode{name='node5', hostname='127.0.0.1', port=8015}]}
12:57:26.756 [Thread-3] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node4:8014 to node2:8012: com.chocksaway.p2p.message.AckMessage@46c6dbd
12:57:26.756 [Thread-1] INFO com.chocksaway.p2p.Node -- [node2:8012] Received ack message: Ack from node4
12:57:26.756 [Thread-1] INFO com.chocksaway.p2p.Node -- [node2:8012] Forwarding ack to: BaseNode{name='node1', hostname='node1', port=8011}
12:57:26.756 [Thread-3] INFO com.chocksaway.p2p.Node -- [node4:8014] Received message for self: Hello, Node 4![BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node3', hostname='127.0.0.1', port=8013}, BaseNode{name='node5', hostname='127.0.0.1', port=8015}]
12:57:26.756 [Thread-3] WARN com.chocksaway.p2p.route.Router -- Node node4 not in path [BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node3', hostname='127.0.0.1', port=8013}, BaseNode{name='node5', hostname='127.0.0.1', port=8015}]
12:57:26.757 [Thread-1] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node2:8012 to node1:8011: com.chocksaway.p2p.message.AckMessage@4ccc5d51
12:57:26.757 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Received ack message: Ack from node4
12:57:26.757 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Ack is for self: Ack from node4
12:57:26.757 [Thread-3] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node4:8014 to node5:8015: com.chocksaway.p2p.message.AckMessage@41b18312
12:57:26.758 [Thread-4] INFO com.chocksaway.p2p.Node -- [node5:8015] Received ack message: Ack from node4
12:57:26.758 [Thread-4] INFO com.chocksaway.p2p.Node -- [node5:8015] Forwarding ack to: BaseNode{name='node1', hostname='node1', port=8011}
12:57:26.759 [Thread-4] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node5:8015 to node3:8013: com.chocksaway.p2p.message.AckMessage@63aeba89
12:57:26.759 [Thread-2] INFO com.chocksaway.p2p.Node -- [node3:8013] Received ack message: Ack from node4
12:57:26.759 [Thread-2] INFO com.chocksaway.p2p.Node -- [node3:8013] Forwarding ack to: BaseNode{name='node1', hostname='node1', port=8011}
12:57:26.760 [Thread-2] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node3:8013 to node1:8011: com.chocksaway.p2p.message.AckMessage@1e871bda
12:57:26.761 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Received ack message: Ack from node4
12:57:26.761 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Ack is for self: Ack from node4
12:57:28.759 [main] INFO com.chocksaway.p2p.TestMostEfficientRoute -- ---- Sending another message to verify consistent routing ----
12:57:28.761 [main] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node1:8011 to node2:8012: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}]}
12:57:28.761 [Thread-1] INFO com.chocksaway.p2p.Node -- [node2:8012] Forwarding message to: node4
12:57:28.763 [Thread-1] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.SimpleMessage sent from node2:8012 to node4:8014: SimpleMessage{destination='node4', message='Hello, Node 4!', path=[BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]}
12:57:28.763 [Thread-3] INFO com.chocksaway.p2p.Node -- [node4:8014] Received message for self: Hello, Node 4![BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]
12:57:28.764 [Thread-3] WARN com.chocksaway.p2p.route.Router -- Node node4 not in path [BaseNode{name='node1', hostname='node1', port=8011}, BaseNode{name='node2', hostname='127.0.0.1', port=8012}]
12:57:28.767 [Thread-3] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node4:8014 to node2:8012: com.chocksaway.p2p.message.AckMessage@3be6eb72
12:57:28.767 [Thread-1] INFO com.chocksaway.p2p.Node -- [node2:8012] Received ack message: Ack from node4
12:57:28.767 [Thread-1] INFO com.chocksaway.p2p.Node -- [node2:8012] Forwarding ack to: BaseNode{name='node1', hostname='node1', port=8011}
12:57:28.770 [Thread-1] INFO com.chocksaway.p2p.Link -- Message class com.chocksaway.p2p.message.AckMessage sent from node2:8012 to node1:8011: com.chocksaway.p2p.message.AckMessage@3e7a606c
12:57:28.771 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Received ack message: Ack from node4
12:57:28.771 [Thread-0] INFO com.chocksaway.p2p.Node -- [node1:8011] Ack is for self: Ack from node4
```

## Requirements

- Java 17+
- Maven 3.6+


