package com.snipermars.sync;

public class MultiThreadPrinter {

    // 编写一个程序，开启三个线程，这三个线程的 ID 分别是 A、B 和 C，每个线程把自己的 ID 在屏幕上打印 10 遍，要求输出结果必须按 ABC 的顺序显示，如 ABCABCABC... 依次递推，用synchronized实现即可
    private static final Object lock = new Object();
    private static int currentThread = 1;
    private static final int TOTAL_RUNS = 10;

    public static void main(String[] args){
        Thread threadA = new Thread(new Printer("A", 1));
        Thread threadB = new Thread(new Printer("B", 2));
        Thread threadC = new Thread(new Printer("C", 3));

        threadA.start();
        threadB.start();
        threadC.start();

        try{
            threadA.join();
            threadB.join();
            threadC.join();
        } catch(InterruptedException ie){
            Thread.currentThread().interrupt();
            return;
        }
    }
    

    static class Printer implements Runnable{
        private final String id;
        private final int printId;

        public Printer(String id, int printId){
            this.id = id;
            this.printId = printId;
        }

        @Override
        public void run(){
            for(int i = 0; i < TOTAL_RUNS;){
                synchronized(lock) {
                    while(currentThread != printId){
                        try{
                            lock.wait();
                        } catch(InterruptedException ie){
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.print(id);
                    i++;
                }
                currentThread = (currentThread % 3) + 1;
                lock.notifyAll();
            }
        }
    }

    // 用concurrent的相关锁实现
    
}
