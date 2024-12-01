package CarServiceLab.QueueImplementation;

import CarServiceLab.Interfaces.Queue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueAdapter<T> implements Queue<T> {
    private final LinkedBlockingQueue<T> queue;
    private volatile boolean isComplete = false;

    public LinkedBlockingQueueAdapter() {
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void enqueue(T item) {
        queue.offer(item);
    }

    @Override
    public T dequeue() throws InterruptedException {
        while (!isComplete || !queue.isEmpty()) {
            T item = queue.poll();
            if (item != null) {
                return item;
            }
            Thread.sleep(100);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void setComplete() {
        isComplete = true;
    }

    @Override
    public boolean isComplete() {
        return isComplete && isEmpty();
    }
}