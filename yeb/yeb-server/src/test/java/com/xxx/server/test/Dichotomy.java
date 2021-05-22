package com.xxx.server.test;


import javax.xml.soap.Node;

/**
 * 二分法
 * @author Wu Zicong
 * @create 2021-05-11 22:37
 */
public class Dichotomy  {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int need = 2;
        int head = 0; //首索引
        int end = arr.length - 1;
        while (head <= end) {
            int middle = (head + end) / 2;
            if (need == arr[middle]) {
                System.out.println(middle);
                break;
            } else if (need > arr[middle]) {
                head = middle;
            } else if (need < arr[middle]) {
                end = middle;
            }
        }
    }
}
