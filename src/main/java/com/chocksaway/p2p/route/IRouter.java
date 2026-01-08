package com.chocksaway.p2p.route;

import com.chocksaway.p2p.ILink;

import java.util.List;
import java.util.Map;

public interface IRouter {
    void addLink(ILink link);
    int getLinks();
    Map<String, List<List<BaseNode>>> getPaths();
}