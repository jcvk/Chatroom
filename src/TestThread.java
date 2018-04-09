
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Jvck on 2018/2/22.
 */
class InterruptTest extends Thread{
    private BlockingQueue<Long> blockingQueue;

    public InterruptTest(BlockingQueue<Long> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        long i = Thread.currentThread().getId();
        while(true){
            try {
                blockingQueue.put(i);
            } catch (InterruptedException e) {
                System.out.println(blockingQueue.size());
                e.printStackTrace();
            }
        }
    }

    public void cancel(){
        interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Long> blockingQueue = new ArrayBlockingQueue(10000);
        InterruptTest interruptTest = new InterruptTest(blockingQueue);


        interruptTest.start();
        Thread.sleep(5);
        interruptTest.cancel();


        Iterator<Long> iterator = blockingQueue.iterator();
        int i = 0;
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            i++;
        }
        System.out.println(i);
    }
}

