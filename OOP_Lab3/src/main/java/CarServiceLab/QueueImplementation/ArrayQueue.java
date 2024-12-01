package CarServiceLab.QueueImplementation;

import CarServiceLab.Interfaces.Queue;

public class ArrayQueue<T> implements Queue<T> {
    private Object[] array;
    private int front;
    private int rear;
    private int size;
    private volatile boolean isComplete;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayQueue() {
        array = new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
        isComplete = false;
    }

    @Override
    public synchronized void enqueue(T item) {
        if (size == array.length) {
            resize();
        }
        rear = (rear + 1) % array.length;
        array[rear] = item;
        size++;
        notifyAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized T dequeue() throws InterruptedException {
        while (isEmpty() && !isComplete) {
            wait();
        }

        if (isEmpty()) {
            return null;
        }

        T item = (T) array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return item;
    }

    private void resize() {
        Object[] newArray = new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(front + i) % array.length];
        }
        array = newArray;
        front = 0;
        rear = size - 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void setComplete() {
        isComplete = true;
        synchronized(this) {
            notifyAll();
        }
    }

    @Override
    public boolean isComplete() {
        return isComplete && isEmpty();
    }
}