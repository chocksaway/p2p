package com.chocksaway.p2p.message;

import com.chocksaway.p2p.route.BaseNode;

import java.io.Serializable;

public record RouterMessage(BaseNode baseNode) implements Serializable {
    public BaseNode getBaseNode() {
        return this.baseNode;
    }

}
