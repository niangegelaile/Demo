package com.ange.demo.thread;

public class ThreadTestWaitAndSleep {
    //https://www.cnblogs.com/hongten/p/hongten_java_sleep_wait.html wait and sleep的区别
    static Thread t1, t2 = null;

    public static void main(String[] args) {

        System.out.print("hello");

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                synchronized (ThreadTestWaitAndSleep.class) {
                    while (i < 30) {
                        i++;
                        System.out.println("t1:" + i);
                        if (i == 10) {
                            //这里join 在线程1中执行，使线程2先执行完再执行线程1
                            t2.start();
                            System.out.println("t1:t2开始执行，由于锁被t1持有，t2在阻塞");
                        }
                        if (i == 20) {
                            try {
                                //调用wait()方法，线程会放弃对象锁，进入等待此对象的等待锁定池
                                System.out.println("t1:进入等待状态");
                                ThreadTestWaitAndSleep.class.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }

            }
        });

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                synchronized (ThreadTestWaitAndSleep.class) {
                    while (i < 30) {
                        i++;
                        System.out.println("t2www:" + i);
                        if (i == 20) {
                            System.out.println("t2www:在t2通知解除阻塞，t1继续执行");
                            ThreadTestWaitAndSleep.class.notify();
                        }
                    }

                }
            }
        });
        t1.start();
    }
}
