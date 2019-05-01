package com.ange.demo.thread;

public class ThreadTest {
   static Thread t1,t2=null;
    public static void main(String[] args){

        System.out.print("hello");

         t1=new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<100){
                    i++;
                    System.out.println("t1:"+i);
                    if(i==10){
                        //这里join 在线程1中执行，使线程2先执行完再执行线程1
                        t2.start();
                        try {
                            t2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

         t2=new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<10){
                    i++;

                    System.out.println("t2:"+i);
                }

            }
        });
        t1.start();
    }
}
