package com.snipermars.tree_example;

import java.util.Arrays;

public class SkipList {
    static final int MAX_LEVEL = 16;
    static final double P = 0.5;
    static final Node sentinel = new Node(0, Integer.MAX_VALUE); // 哨兵节点，用于简化代码

    static class Node {
        Node[] forward; // forward[i] 表示当前节点在第 i 层的下一个节点
        int key;
        int value;

        Node(int level, int key){
            forward = new Node[level];
            this.key = key;
            this.value = 1;
        }
    }

    Node header;
    int listLevel;

    public SkipList(){
        header = new Node(MAX_LEVEL, -1);
        Arrays.fill(header.forward, sentinel);
        listLevel = 1;
    }
    
    public boolean search(int target) {
        Node cur = header;
        for(int i = listLevel - 1; i >= 0; --i){
            while(cur.forward[i].key < target){
                cur = cur.forward[i];
            }
        }

        return cur.forward[0].key == target;
    }
    
    public void add(int num) {
        Node[] update = getPredecessors(num); // 返回当前值的所有前驱节点
        Node cur = update[0].forward[0];

        if(cur.key == num){
            ++cur.value;
        } else {
            int level = randomLevel(); // 随机生成一个层数
            // 如果随机生成的层数大于当前的层数，那么需要更新哨兵节点的指针
            if(level > listLevel){
                for(int i = listLevel; i < level; ++i) {
                    update[i] = header;
                }

                listLevel = level;
            }
            cur = new Node(level, num);
            for(int i = 0; i < level; i++){
                cur.forward[i] = update[i].forward[i];
                update[i].forward[i] = cur;
            }
        }
    }

    public boolean erase(int num){
        Node[] update = getPredecessors(num);
        Node cur = update[0].forward[0];
        if(cur.key != num){
            return false;
        } else {
            if(cur.value > 1){
                --cur.value;
            } else {
                for(int i = 0; i < listLevel; ++i){
                    if(update[i].forward[i] != cur){
                        break;
                    }
                    update[i].forward[i] = cur.forward[i];
                }
                while(listLevel > 1 && header.forward[listLevel - 1] == sentinel){
                    --listLevel;
                }
            }
            return true;
        }
    }

    private Node[] getPredecessors(int target){
        Node[] update = new Node[MAX_LEVEL];
        Node cur = header;
        // 从最高层开始查找
        // 查找的过程中，update[i] 表示当前节点在第 i 层的下一个节点
        // 查找的过程中，cur 表示当前节点在第 i 层的下一个节点
        for(int i = listLevel - 1; i >= 0; --i){
            while(cur.forward[i].key < target){ // 如果当前节点的下一个节点的 key 小于 target，那么当前节点就是 target 的前驱节点
                cur = cur.forward[i];
            }
            update[i] = cur;
        }

        return update; // 返回的是 target 的前驱节点
    }

    private int randomLevel() {
        int level = 1;
        while(Math.random() < P && level < Math.min(MAX_LEVEL, listLevel + 1)) {
            ++level;
        }

        return level;
    }
}
