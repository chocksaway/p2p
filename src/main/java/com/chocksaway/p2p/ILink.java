package com.chocksaway.p2p;

import java.io.Serializable;

public interface ILink extends Serializable {
    INode from();
    INode to();
    void addToFromRouter();
    void sendMessage(Object message);
}