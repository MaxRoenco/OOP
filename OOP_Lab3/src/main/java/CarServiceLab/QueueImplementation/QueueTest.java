package CarServiceLab.QueueImplementation;

import CarServiceLab.Interfaces.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QueueTest {
    @Test
    void testArrayQueue() throws InterruptedException {
        System.out.println("\nTesting ArrayQueue Implementation:");
        Queue<Integer> queue = new ArrayQueue<Integer>();

        System.out.println("Testing initial state...");
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        System.out.println("Initial state verified - queue is empty");

        System.out.println("\nTesting enqueue operations...");
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Enqueued values: 1, 2, 3");

        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());
        System.out.println("Queue size verified: " + queue.size());

        System.out.println("\nTesting dequeue operations...");
        int value1 = queue.dequeue();
        System.out.println("Dequeued: " + value1);
        assertEquals(1, value1);

        int value2 = queue.dequeue();
        System.out.println("Dequeued: " + value2);
        assertEquals(2, value2);
        assertEquals(1, queue.size());
        System.out.println("Current queue size: " + queue.size());

        System.out.println("\nTesting additional enqueue...");
        queue.enqueue(4);
        System.out.println("Enqueued: 4");
        assertEquals(2, queue.size());

        System.out.println("\nTesting final dequeue operations...");
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertTrue(queue.isEmpty());
        System.out.println("Queue is empty after all operations");
    }

    @Test
    void testCircularQueue() throws InterruptedException {
        System.out.println("\nTesting CircularQueue Implementation:");
        Queue<String> queue = new CircularQueue<String>();

        System.out.println("Testing initial state...");
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        System.out.println("Initial state verified - queue is empty");

        System.out.println("\nTesting enqueue operations...");
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        System.out.println("Enqueued values: A, B, C");

        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());
        System.out.println("Queue size verified: " + queue.size());

        System.out.println("\nTesting dequeue operations...");
        String value1 = queue.dequeue();
        System.out.println("Dequeued: " + value1);
        assertEquals("A", value1);

        String value2 = queue.dequeue();
        System.out.println("Dequeued: " + value2);
        assertEquals("B", value2);
        assertEquals(1, queue.size());
        System.out.println("Current queue size: " + queue.size());

        System.out.println("\nTesting additional enqueue...");
        queue.enqueue("D");
        System.out.println("Enqueued: D");
        assertEquals(2, queue.size());

        System.out.println("\nTesting final dequeue operations...");
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
        assertTrue(queue.isEmpty());
        System.out.println("Queue is empty after all operations");
    }
}