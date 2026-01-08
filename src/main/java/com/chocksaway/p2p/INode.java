package com.chocksaway.p2p;

import com.chocksaway.p2p.route.IRouter;

public interface INode {
    String getHostname();
    int getPort();
    String getName();
    IRouter getRouter();
}