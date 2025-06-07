package com.snipermars.tree_example;

public class AVLTree {

    class Node{
        int key, height;
        Node left, right;

        Node(int d){
            this.key = d;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node){
        if(node == null){
            return 0;
        }else{
            return node.height;
        }
    }

    private void updateHeight(Node node){
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private int getBalance(Node node){
        if(node == null){
            return 0;
        }else{
            return height(node.left) - height(node.right);
        }
    }

    private Node leftrotate(Node node){
        Node x = node.right;
        Node B = x.left;

        x.left = node;
        node.right = B;

        updateHeight(node);
        updateHeight(x);

        return x;
    }

    private Node rightrotate(Node node){
        Node x = node.left;
        Node B = x.right;

        x.right = node;
        node.left = B;

        updateHeight(node);
        updateHeight(x);

        return x;
    }

    public void insert(int key){
        root = insert(root, key);
    }

    private Node insert(Node node, int key){
        if(node == null){
            return new Node(key);
        }

        if(key < node.key){
            node.left = insert(node.left, key);
        }else if(key > node.key){
            node.right = insert(node.right, key);
        }else{
            return node;
        }

        updateHeight(node);

        int balance = getBalance(node);

        // Four situation need to be considered
        if(balance > 1 && key < node.left.key){ // LL
            return rightrotate(node);
        }else if(balance < -1 && key > node.right.key){ // RR
            return leftrotate(node);
        }else if(balance > 1 && key > node.left.key){ // LR
            node.left = leftrotate(node.left);
            return rightrotate(node);
        }else if(balance < -1 && key < node.right.key){ // RL
            node.right = rightrotate(node.right);
            return leftrotate(node);
        }
        return node;
    }

    public void inorder(){
        inorder(root);
    }

    private void inorder(Node node){
        if(node != null){
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    private Node minValueNode(Node node){
        Node current = node;
        while(current.left != null){
            current = current.left;
        }
        return current;
    }

    private Node delete(Node node, int key){
        if(node == null){
            return node;
        }

        if(key < node.key){
            node.left = delete(node.left, key);
        }else if(key > node.key){
            node.right = delete(node.right, key);
        }else{ // node.key == key
            if(node.left == null || node.right == null){ // one child or no child
                Node temp = node.left != null ? node.left : node.right;
                if(temp == null){ // no child
                    temp = node;
                    node = null;
                }else{ // one child
                    node = temp;
                }
            }else{ // two children
                Node temp = minValueNode(node.right); // find the smallest in the right subtree
                node.key = temp.key; // replace the node with the smallest in the right subtree
                node.right = delete(node.right, temp.key); // delete the smallest in the right subtree
            }
        }
        if(node == null){ // if the tree has only one node
            return node;
        }
        updateHeight(node); // update the height of the node
        int balance = getBalance(node); // get the balance factor of the node

        // Four situation need to be considered
        if(balance > 1 && getBalance(node.left) >= 0){ // LL
            return rightrotate(node);
        }else if(balance < -1 && getBalance(node.right) <= 0){ // RR
            return leftrotate(node);
        }else if(balance > 1 && getBalance(node.left) < 0){ // LR
            node.left = leftrotate(node.left);
            return rightrotate(node);
        }else if(balance < -1 && getBalance(node.right) > 0){ // RL
            node.right = rightrotate(node.right);
            return leftrotate(node);
        }
        return node;
    }

    public void delete(int key){
        root = delete(root, key);
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);
        System.out.println("中序遍历:");
        tree.inorder(); // 输出: 10 20 25 30 40 50
        
        // 删除测试
        tree.delete(30);
        System.out.println("\n删除30后的中序遍历:");
        tree.inorder(); // 输出: 10 20 25 40 50
    }

}
