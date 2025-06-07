package com.snipermars.tree_example;


// 红黑树关键点
// 红黑树的每个节点都有一个颜色（红色或黑色），并遵循以下规则：

// 每个节点要么是红色，要么是黑色
// 根节点是黑色
// 所有叶子节点（NIL 节点，空节点）是黑色
// 如果一个节点是红色的，则它的子节点必须是黑色的
// 对每个节点，从该节点到其所有后代叶节点的简单路径上，均包含相同数目的黑色节点
public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int key;
        Node left, right;
        boolean color;

        Node(int key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private Node root;

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void insert(int key) {
        root = insert(root, key);
        root.color = BLACK;
    }

//  插入操作遵循标准的二叉搜索树插入逻辑，但插入后需要通过旋转和颜色转换来恢复红黑树的性质：

//  递归插入新节点，新节点默认为红色
//  自底向上调整树结构：
//  如果右子节点是红色而左子节点是黑色，进行左旋转
//  如果左子节点是红色且左子节点的左子节点也是红色，进行右旋转
//  如果左右子节点均为红色，进行颜色转换
    private Node insert(Node h, int key) {
        if (h == null) return new Node(key, RED);

        if (key < h.key) h.left = insert(h.left, key);
        else if (key > h.key) h.right = insert(h.right, key);
        else h.key = key;

        
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    public void delete(int key) {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (root != null) root.color = BLACK;
    }

    private Node delete(Node h, int key) {
        if (key < h.key) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key == h.key && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key == h.key) {
                Node x = min(h.right);
                h.key = x.key;
                h.right = deleteMin(h.right);
            } else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    // balance the tree after deletion
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // balance the tree after deletion
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public boolean search(int key) {
        return search(root, key) != null;
    }

    private Node search(Node x, int key) {
        if (x == null) return null;
        if (key == x.key) return x;
        if (key < x.key) return search(x.left, key);
        else return search(x.right, key);
    }
}