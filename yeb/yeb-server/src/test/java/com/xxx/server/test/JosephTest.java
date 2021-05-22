package com.xxx.server.test;

/**
 * 约瑟夫环问题
 * @author Wu Zicong
 * @create 2021-05-12 19:36
 */
public class JosephTest {

    /**
     *
     * @param n  有n只猴子
     * @param m  第m只踢出
     */
    public static void count(int n,int m){
        //头一个节点不存数据
        Node head = new Node();
        Node cur = head;  //这里的cur相当于指针，此时它指向第一个节点head
        //把值放入猴子的编号从1开始连成一个链表
        for(int i = 1;i <= n ; i++){
            //创建一个新节点，把编号放进去
            Node node = new Node(i);
            //连接下一个节点后，指针指向下一个节点
            cur.next = node;
            cur = node;
        }
        //最后一个节点的下一个节点是head的下一个节点，此时形成一个环形链表（head没用了）
        cur.next = head.next;
       //新的指针：p
        Node p = head.next;
        while(p.next!=p){
            //这一步是让p指针指向，m的前一个节点
            for(int i = 1;i<m-1;i++){
                p = p.next;
            }
            //此时这里的p.next就是我们想要踢出的第m个了
            System.out.print(p.next.data + "->");
            p.next = p.next.next; //此时直接把p的下一个指向p的下一个的下一个，即跳过p.next（实现踢出）,
            //再把p指针指向它的下一个节点。
            /*（思考，这里的m已经被踢出，那么m的下一位替代了m的位置，
                此时我们需要从m的下一位重新开始循环，因此,把指针给它的下一位。
            */
            p = p.next;
        }
        System.out.println("大王是:"+p.data);
    }

    public static void main(String[] args) {
        count(5,3);
    }
}
class Node{
    int data;
    Node next;
    public Node(){};
    public Node (int data){
        this.data = data;
    }
}
