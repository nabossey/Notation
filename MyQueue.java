import java.util.*;
 

public class MyQueue<T> implements QueueInterface<T> {

    protected T q[];
    protected int front, rear, capacity, len;

    
    @SuppressWarnings("unchecked")
	public MyQueue(int n) {
        capacity = n;
        len = 0;
        q = (T[]) new Object[capacity];
        front = -1;
        rear = -1;
    }

    //default constructor - uses a default as the size of the queue
    public MyQueue() {
        this(20);
    }

    @Override
    public boolean isEmpty() {
        return front == -1;
    }

    @Override
    public boolean isFull() {
        return front == 0 && rear == capacity - 1;
    }

    @Override
    public T dequeue() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException("Underflow Exception");
        } else {
            len--;
            T ele = q[front];
            if (front == rear) {
                front = -1;
                rear = -1;
            } else {
                front++;
            }
            return ele;
        }
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public boolean enqueue(T e) throws QueueOverflowException {

        if (rear == -1) {
            front = 0;
            rear = 0;
            q[rear] = e;

        } else if (rear + 1 >= capacity) {
            throw new QueueOverflowException("Overflow Exception");
        } else if (rear + 1 < capacity) {
            q[++rear] = e;
        }
        len++;
        return true;
    }

    @Override
    public String toString(String delimiter) {
        StringBuilder s = new StringBuilder();
        for (int i = front; i <= rear; i++) {
            s.append(q[i].toString());
            if(i<rear)
            s.append(delimiter);
        }
        return s.toString();
    }
    
    @Override
    public String toString( ) {
        StringBuilder s = new StringBuilder();
        for (int i = front; i <= rear; i++) {
            s.append(q[i].toString());
             
        }
        return s.toString();
    }
    

    @SuppressWarnings("unchecked")
	@Override
    public void fill(ArrayList<T> list) {
        len = 0;
        q = (T[]) new Object[capacity];
        front = -1;
        rear = -1;
        try {
            for (T t : list) {

                enqueue(t);

            }
        } catch (QueueOverflowException ex) {
            System.out.println(ex.toString());
        }
    }

}
