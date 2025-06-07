package com.snipermars.sync;

import java.util.*;
import java.util.concurrent.*;

public class HeartBeatMechanism {
    // 主节点维护的节点状态表
    private final Map<String, Long> nodeLastHeartbeat = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    // 心跳超时时间（毫秒）
    private static final long HEARTBEAT_TIMEOUT = 3000;
    // 心跳间隔时间（毫秒）
    private static final long HEARTBEAT_INTERVAL = 1000;
    
    public HeartBeatMechanism() {
        // 启动心跳检测线程
        scheduler.scheduleAtFixedRate(this::checkHeartbeats, 0, 1, TimeUnit.SECONDS);
    }
    
    // 节点发送心跳
    public void sendHeartbeat(String nodeId) {
        nodeLastHeartbeat.put(nodeId, System.currentTimeMillis());
    }

    /**
     * 检查所有节点的心跳状态
     */
    private void checkHeartbeats() {
        long currentTime = System.currentTimeMillis();
        nodeLastHeartbeat.forEach((nodeId, lastTime) -> {
            if (currentTime - lastTime > HEARTBEAT_TIMEOUT) {
                System.err.println("!!! 节点 [" + nodeId + "] 心跳超时，最后活跃: " + new Date(lastTime));
                // 这里可以添加节点失效的处理逻辑
            }
        });
    }
    
    /**
     * 模拟从节点
     */
    static class SlaveNode implements Runnable {
        private final String nodeId;
        private final HeartBeatMechanism master;
        
        public SlaveNode(String nodeId, HeartBeatMechanism master) {
            this.nodeId = nodeId;
            this.master = master;
        }
        
        @Override
        public void run() {
            // 定期发送心跳
            while (true) {
                try {
                    master.sendHeartbeat(nodeId);
                    Thread.sleep(HEARTBEAT_INTERVAL);
                } catch (InterruptedException e) {
                    System.out.println("[" + nodeId + "] 心跳发送被中断");
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        HeartBeatMechanism master = new HeartBeatMechanism();
        
        // 启动3个从节点
        new Thread(new SlaveNode("node-1", master)).start();
        new Thread(new SlaveNode("node-2", master)).start();
        new Thread(new SlaveNode("node-3", master)).start();
        
        // 模拟node-2在5秒后宕机
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("\n模拟节点[node-2]宕机\n");
                // 这里实际上应该停止node-2的心跳线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}