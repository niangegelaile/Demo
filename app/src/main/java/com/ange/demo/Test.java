package com.ange.demo;

public class Test {

    public static void main(String[] args) {
//        test3();
        System.out.println("result = " + fun("Smart"));
    }
//包装类属于引用数据类型，所以它们之间的区别就是基本数据类型和引用数据类型的区别。反应到内存中，基本数据类型的值是存放在栈里面，而包装类的栈存放的是值得地址，值存放在堆内存中。
//初始值不同，eg： int的初始值为 0 、 boolean的初始值为false 而包装类型的初始值为null

    //在java中，Integer，Short，Byte，Character，Long有缓存机制。浮点型没有该机制，大小范围除Character为0-127外其余均为-128–127.
    public static void test() {
        Integer a1 = 1;
        Integer a2 = 1;
        System.out.println("a1==a2?" + (a1 == a2));//true
    }

    public static void test1() {
        Integer a1 = 128;
        Integer a2 = 128;
        System.out.println("a1==a2?" + (a1 == a2));//false
    }

    public static void test2() {
        Integer a1 = 1;
        Integer a2 = 1;

        Integer b1 = 200;
        Integer b2 = 200;

        Integer c1 = Integer.valueOf(1);
//        Integer c2 = new Integer(1);      官方不推荐这种建对象的方法喔
        Integer c2 = Integer.valueOf(1);

        Integer d1 = Integer.valueOf(200);
        Integer d2 = Integer.valueOf(200);


        System.out.println("a1==a2?" + (a1 == a2));//true
        System.out.println("b1==b2?" + (b1 == b2));//false
        System.out.println("c1==c2?" + (c1 == c2));//true
        System.out.println("d1==d2?" + (d1 == d2));//false

    }


    public static void test3(){
        int a = 2;
        int rusult = a++ + 4<<2; //先算 a++ 得2 再+4 得 6 左移两位 得24
        System.out.println("rusult:" + rusult);//false
    }


    public static String fun(String s) {
      return s.substring(1) + s.charAt(0);
    }



}
