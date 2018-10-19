/**
 * @Author: chenhaisheng
 * @Date:   2018-10-19T15:28:37+08:00
 * @Email:  ecjtusbs@foxmail.com
 * @Last modified by:   chenhaisheng
 * @Last modified time: 2018-10-19T15:33:45+08:00
 * @Copyright: ecjtusbs@foxmail.com
 */
 import java.util.Queue;

 public class Producer extends Thread {

     //面向接口编程:
     Queue<Integer> queue;
     int maxSize;

     public Producer(Queue<Integer> queue, int maxSize, String name) {
         this.queue = queue;
         this.maxSize = maxSize;
         this.setName(name);
     }

     @Override
     public void run() {

         while (true) {

             //首先获取queue的锁
             synchronized (queue) {

                 //条件判断，不满则可以生产,记住用while判断而不是if
                 while (queue.size() >= maxSize) {
                     try {
                         //释放占有的queue锁，进入queue的等待队列中
                         queue.wait();
                     } catch (InterruptedException e) {
                         System.out.println("Interrupted");
                     }
                 }

                 System.out.println(Thread.currentThread().getName() + "开始生产");
                 int tmp = (int) Math.random() * 400;
                 //添加元素
                 queue.offer(tmp);
                 System.out.println(Thread.currentThread().getName() + "将食物添加到缓冲区=====当前食物个数：" + queue.size());

                 //通知等待queue的线程进入等待队列，退出临界区后才会真正释放占有的queue锁
                 queue.notifyAll();
             }

             //退出临界区，当前线程释放queue的锁，让当前线程休眠一段时间，使其他线程有机会进入临界区
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 System.out.println("Interrupded");
             }
         } // end of while
     }
 }
