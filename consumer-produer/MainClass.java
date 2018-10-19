/**
 * @Author: chenhaisheng
 * @Date:   2018-10-19T15:29:52+08:00
 * @Email:  ecjtusbs@foxmail.com
 * @Last modified by:   chenhaisheng
 * @Last modified time: 2018-10-19T15:30:36+08:00
 * @Copyright: ecjtusbs@foxmail.com
 *
 生产者-消费者模型
 原始wait-nofity方法实现
 加深对wait-notify的理解
 */
 import java.util.LinkedList;
 import java.util.Queue;
 public class MainClass {

     public static void main(String[] args) throws Exception {

         //缓冲区
         Queue<Integer> queue = new LinkedList<>();
         //缓冲区最大大小
         int maxSize = 4;

         Producer p1 = new Producer(queue, maxSize, "P1");
         Producer p2 = new Producer(queue, maxSize, "P2");

         Consumer c1 = new Consumer(queue, maxSize, "C1");
         Consumer c2 = new Consumer(queue, maxSize, "C2");
         Consumer c3 = new Consumer(queue, maxSize, "C3");

         p1.start();
         p2.start();
         c1.start();
         c2.start();
         c3.start();

     }
 }
