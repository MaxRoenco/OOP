package CarServiceLab.Interfaces;

public interface Queue<T> {
    void enqueue(T item);
    T dequeue() throws InterruptedException;
    boolean isEmpty();
    int size();
    void setComplete();
    boolean isComplete();
}