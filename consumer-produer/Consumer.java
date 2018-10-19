/**
 * @Author: chenhaisheng
 * @Date:   2018-10-19T15:31:25+08:00
 * @Email:  ecjtusbs@foxmail.com
 * @Last modified by:   chenhaisheng
 * @Last modified time: 2018-10-19T15:32:54+08:00
 * @Copyright: ecjtusbs@foxmail.com
 */
 import java.util.Queue;
 public class Consumer extends Thread {

     //面向接口编程
     Queue<Integer> queue;
     int maxSize;

     public Consumer(Queue<Integer> queue, int maxSize,String name) {
         this.queue = queue;
         this.maxSize = maxSize;
         this.setName(name);
     }

     @Override
     public void run() {

         while (true) {

             synchronized (queue) {

                 //这里用while是为了避免被错误唤醒
                 //例如，线程只有A和B都满足时才能继续执行，但线程在等待A和B时被错误唤醒，此时A和B还未满足，如果用if则无法再次判断，因此唤醒后还要继续进行判断
                 while (queue.isEmpty()) {
                     System.out.println(Thread.currentThread().getName()+"发现没有吃的,开始等待");
                     try {
                         //队列为空，此时需要释放当前进程占用的queue的锁，阻塞当前进程，让其它等待queue锁的进程能有机会获取queue锁
                         queue.wait();
                     } catch (InterruptedException e) {
                         System.out.println("等待食物时被中断");
                     }
                 }

                 System.out.println(Thread.currentThread().getName()+"开始吃东西");

                 //poll()从队列中取出一个元素，破坏性取出
                 int num=queue.poll();
                 System.out.println(Thread.currentThread().getName()+"吃完了, 剩余食物个数："+queue.size());

                 //消费完成，nofity()通知等待queue锁的线程,当前线程在退出queue的临界区方法后释放占有的queue锁
                 queue.notifyAll();
             }

             //notify()在退出临界区之后才会释放锁，当前线程等待一段时间，让其他线程有时间去占有queue锁
             try {
                 Thread.sleep(500);
             } catch (InterruptedException e) {
                 System.out.println("interrupted!");
             }
         }
     }
 }
