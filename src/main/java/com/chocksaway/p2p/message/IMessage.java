package com.chocksaway.p2p.message;

import com.chocksaway.p2p.Link;
import com.chocksaway.p2p.route.BaseNode;

public interface IMessage {
    default Link buildLink(BaseNode from, BaseNode to) {
        var toNode = from.build();
        var fromNode = to.build();

        return new Link(fromNode, toNode);
    }
}
