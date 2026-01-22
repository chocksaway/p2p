package com.chocksaway.p2p.message;

public interface IResponse {
    IMessage getOriginalMessage();
    void send(IMessage iMessage);
}
