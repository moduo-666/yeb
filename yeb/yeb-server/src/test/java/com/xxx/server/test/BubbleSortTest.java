package com.xxx.server.test;

import java.util.*;

/**
 * 冒泡排序
 * @author Wu Zicong
 * @create 2021-05-11 22:27
 */
public class BubbleSortTest {

    public static void main(String[] args) {
        Collection set = new TreeSet();
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("1");
        list.add("b");
        list.add("c");
        Collections.sort(list);
        System.out.println(Arrays.asList(list));

        int[] arr = new int[]{42,15,65,13,17};
        for(int i = 0; i< arr.length - 1;i++){
            for(int j = 0;j< arr.length- 1 - i;j++){
                    if(arr[j]>arr[j+1]){
                        int temp = arr[j+1];
                        arr[j+1] = arr[j];
                        arr[j] = temp;
                    }
            }
        }
        System.out.println(Arrays.toString(arr));

    }
}
