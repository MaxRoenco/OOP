package CarServiceLab.QueueImplementation;

import CarServiceLab.Interfaces.Queue;

public class CircularQueue<T> implements Queue<T> {
    private Node<T> rear;
    private int size;
    private volatile boolean isComplete;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public CircularQueue() {
        rear = null;
        size = 0;
        isComplete = false;
    }

    @Override
    public synchronized void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            newNode.next = newNode;
            rear = newNode;
        } else {
            newNode.next = rear.next;
            rear.next = newNode;
            rear = newNode;
        }
        size++;
        notifyAll();
    }

    @Override
    public synchronized T dequeue() throws InterruptedException {
        while (isEmpty() && !isComplete) {
            wait();
        }

        if (isEmpty()) {
            return null;
        }

        T item = rear.next.data;
        if (rear.next == rear) {
            rear = null;
        } else {
            rear.next = rear.next.next;
        }
        size--;
        return item;
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