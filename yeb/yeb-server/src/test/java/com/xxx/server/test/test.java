package com.xxx.server.test;

/**
 * @author Wu Zicong
 * @create 2021-05-16 21:29
 */
public class test {
    public static void main(String[] args) {
        System.out.println("1到100的总和为："+ test.sumAdd(1,100));
        System.out.println("1到100的总和为："+ test.sumAdd2(1,100));
        System.out.println("1到100的总和为："+ test.sumAdd3(1,100));
    }

    /**
     * 求n到m的总和
     * @param n
     * @param m
     * @return
     */
    public static Integer sumAdd(int n,int m){
        if(n > m){
            throw new RuntimeException("请输入规范的数");
        }
        int num = 0;
        for(int i = n;i <= m;i++){
            num += i;
        }
        return num;
    }
    /**
     * 求n到m的总和
     * @param n
     * @param m
     * @return
     */
    public static Integer sumAdd2(int n,int m){
        if(n > m){
            throw new RuntimeException("请输入规范的数");
        }
        int num = 0;
        while( n<= m){
            num += n;
            n++;
        }
        return num;
    }
    /**
     * 求n到m的总和
     * @param n
     * @param m
     * @return
     */
    public static Integer sumAdd3(int n,int m){
        if(n > m){
            throw new RuntimeException("请输入规范的数");
        }
        int num = 0;
        do{
           num+=n;
           n++;
        }
        while( n<= m);
        return num;
    }
}
