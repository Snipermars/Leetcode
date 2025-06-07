package com.snipermars.tree_example;

public class BPlusTree {

    class Node {
        int[] keys;
        Node[] children;
        int m;
        boolean leaf;
    
        public Node() {
            this.keys = new int[M - 1];
            this.children = new Node[M];
            this.leaf = true;
            this.m = 0;
        }
    }

    private Node root;
    private static final int M = 4;

    public BPlusTree() {
        root = new Node();
    }

    // 查找方法
    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(Node node, int key) {
        int i = 0;
        while (i < node.m && key > node.keys[i]) {
            i++;
        }
        if (i < node.m && key == node.keys[i]) {
            return true;
        }
        if (node.leaf) {
            return false;
        }
        return search(node.children[i], key);
    }

    // 插入方法
    public void insert(int key) {
        Node r = root;
        if (r.m == M - 1) {
            Node s = new Node();
            root = s;
            s.leaf = false;
            s.children[0] = r;
            splitChild(s, 0);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(Node node, int key) {
        int i = node.m - 1;
        if (node.leaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.m++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.children[i].m == M - 1) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(Node parent, int index) {
        Node child = parent.children[index];
        Node newNode = new Node();
        newNode.leaf = child.leaf;
        for (int j = 0; j < M / 2 - 1; j++) {
            newNode.keys[j] = child.keys[j + M / 2];
        }
        if (!child.leaf) {
            for (int j = 0; j < M / 2; j++) {
                newNode.children[j] = child.children[j + M / 2];
            }
        }
        child.m = M / 2 - 1;
        for (int j = parent.m; j >= index + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[index + 1] = newNode;
        for (int j = parent.m - 1; j >= index; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[index] = child.keys[M / 2 - 1];
        parent.m++;
    }

    // 删除方法
    public void delete(int key) {
        delete(root, key);
        if (root.m == 0 && ! root.leaf) {
            root = root.children[0];
        }
    }

    private void delete(Node node, int key) {
        int i = 0;
        while (i < node.m && key > node.keys[i]) {
            i++;
        }
        if (i < node.m && key == node.keys[i]) {
            if (node.leaf) {
                removeFromLeaf(node, i);
            } else {
                removeFromNonLeaf(node, i);
            }
        } else {
            if (node.leaf) {
                return;
            }
            boolean flag = (i == node.m);
            if (node.children[i].m < M / 2) {
                fill(node, i);
            }
            if (flag && i > node.m) {
                delete(node.children[i - 1], key);
            } else {
                delete(node.children[i], key);
            }
        }
    }

    private void removeFromLeaf(Node node, int idx) {
        for (int i = idx + 1; i < node.m; ++i) {
            node.keys[i - 1] = node.keys[i];
        }
        node.m--;
    }

    private void removeFromNonLeaf(Node node, int idx) {
        int key = node.keys[idx];
        if (node.children[idx].m >= M / 2) {
            int pred = getPred(node, idx);
            node.keys[idx] = pred;
            delete(node.children[idx], pred);
        } else if (node.children[idx + 1].m >= M / 2) {
            int succ = getSucc(node, idx);
            node.keys[idx] = succ;
            delete(node.children[idx + 1], succ);
        } else {
            merge(node, idx);
            delete(node.children[idx], key);
        }
    }

    private int getPred(Node node, int idx) {
        Node curr = node.children[idx];
        while (!curr.leaf) {
            curr = curr.children[curr.m];
        }
        return curr.keys[curr.m - 1];
    }

    private int getSucc(Node node, int idx) {
        Node curr = node.children[idx + 1];
        while (!curr.leaf) {
            curr = curr.children[0];
        }
        return curr.keys[0];
    }

    private void fill(Node node, int idx) {
        if (idx != 0 && node.children[idx - 1].m >= M / 2) {
            borrowFromPrev(node, idx);
        } else if (idx != node.m && node.children[idx + 1].m >= M / 2) {
            borrowFromNext(node, idx);
        } else {
            if (idx != node.m) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }

    private void borrowFromPrev(Node node, int idx) {
        Node child = node.children[idx];
        Node sibling = node.children[idx - 1];
        for (int i = child.m - 1; i >= 0; --i) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.leaf) {
            for (int i = child.m; i >= 0; --i) {
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = node.keys[idx - 1];
        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.m];
        }
        node.keys[idx - 1] = sibling.keys[sibling.m - 1];
        child.m++;
        sibling.m--;
    }

    private void borrowFromNext(Node node, int idx) {
        Node child = node.children[idx];
        Node sibling = node.children[idx + 1];
        child.keys[child.m] = node.keys[idx];
        if (!child.leaf) {
            child.children[child.m + 1] = sibling.children[0];
        }
        node.keys[idx] = sibling.keys[0];
        for (int i = 1; i < sibling.m; ++i) {
            sibling.keys[i - 1] = sibling.keys[i];
        } 
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.m; ++i) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }
        child.m++;
        sibling.m--;
    }

    private void merge(Node node, int idx) {
        Node child = node.children[idx];
        Node sibling = node.children[idx + 1];
        child.keys[M / 2 - 1] = node.keys[idx];
        for (int i = 0; i < sibling.m; ++i) {
            child.keys[i + M / 2] = sibling.keys[i];
        }
        if (!child.leaf) {
            for (int i = 0; i <= sibling.m; ++i) {
                child.children[i + M / 2] = sibling.children[i];
            }
        }
        for (int i = idx + 1; i < node.m; ++i) {
            node.keys[i - 1] = node.keys[i];
        }
        for (int i = idx + 2; i <= node.m; ++i) {
            node.children[i - 1] = node.children[i];
        }
        child.m += sibling.m + 1;
        node.m--;
    }
}