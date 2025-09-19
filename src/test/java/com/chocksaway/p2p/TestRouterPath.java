package com.chocksaway.p2p;

import com.chocksaway.p2p.route.BaseNode;
import com.chocksaway.p2p.route.Router;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRouterPath {
    @Test
    public void testPathAlreadyExists() {
        Router router = new Router("A");
        List<BaseNode> path1 = List.of(new BaseNode("a", "a", 1), new BaseNode("b", "b", 2), new BaseNode("c","c", 3));
        List<BaseNode> path2 = List.of(new BaseNode("a", "a", 1), new BaseNode("b", "b", 2));

        var paths = new ArrayList<List<BaseNode>>();
        paths.add(path1);
        paths.add(path2);

        List<BaseNode> path = List.of(new BaseNode("a", "a", 1), new BaseNode("b", "b", 2));
        assertTrue(router.pathExists(paths, path));
    }

    @Test
    public void testPathDoesNotExist() {
        Router router = new Router("A");
        List<BaseNode> path1 = List.of(new BaseNode("a", "a", 1), new BaseNode("b", "b", 2), new BaseNode("c","c", 3));
        List<BaseNode> path2 = List.of(new BaseNode("a", "a", 1), new BaseNode("b", "b", 2));

        var paths = new ArrayList<List<BaseNode>>();
        paths.add(path1);
        paths.add(path2);

        List<BaseNode> path = List.of(new BaseNode("a", "a", 1), new BaseNode("b", "b", 2), new BaseNode("d", "d", 4));
        assertFalse(router.pathExists(paths, path));
    }
}
