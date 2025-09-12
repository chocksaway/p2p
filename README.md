# P2P Node Project

A peer-to-peer (P2P) messaging system in Java using Maven. Nodes can send and receive serializable messages over sockets.
Ack messages are sent back to the sender after delivery of a message.

## Features

- Node server that listens for incoming connections
- Message routing and forwarding
- Reactive message stream using Project Reactor
- Serializable message objects

## Requirements

- Java 17+
- Maven 3.6+

Unit tests

A network is constructed with nodes, and links.
Messages are sent between nodes, and the receipt (ack) of messages is verified.


