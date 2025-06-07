package com.snipermars.tree_example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    @Test
    public void testInsertAndInorder() {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);
        tree.inorder(); // 预期输出: 10 20 25 30 40 50
    }

    @Test
    public void testDelete() {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);
        tree.delete(30);
        tree.inorder(); // 预期输出: 10 20 25 40 50
    }

    @Test
    public void testSearch() {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.search(20); // 预期输出: Found
        tree.search(100); // 预期输出: Not found
    }
}