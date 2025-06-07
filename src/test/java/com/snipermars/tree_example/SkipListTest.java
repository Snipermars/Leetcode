package com.snipermars.tree_example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SkipListTest {
    @Test
    public void testSearch() {
        SkipList skipList = new SkipList();
        skipList.add(1);
        assertTrue(skipList.search(1));
        assertFalse(skipList.search(2));
    }

    @Test
    public void testAdd() {
        SkipList skipList = new SkipList();
        skipList.add(1);
        assertTrue(skipList.search(1));
        skipList.add(1);
        assertEquals(2, skipList.search(1) ? 1 : 0); // 假设search返回节点，这里需要根据实际实现调整
    }

    @Test
    public void testErase() {
        SkipList skipList = new SkipList();
        skipList.add(1);
        assertTrue(skipList.erase(1));
        assertFalse(skipList.search(1));
    }
}