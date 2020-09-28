import java.util.ArrayList;

 
public class MyStack<T> implements StackInterface<T> {

    private T arr[];
    private int size;
    private int index ;

    /**
	 * Provide two constructors
	 * 1. takes in
         * an int as the size of the stack
	 * 2. default constructor - uses default as the size of the stack
	 */
    
    @SuppressWarnings("unchecked")
	public MyStack(int size) {
        this.size = size;
        arr =   (T[]) new Object[size];
        index = 0;
    }
    
    public MyStack( ) {
        this(20);
    }
    
    @Override
    public boolean isEmpty() {
        return index == 0;
    }

    @Override
    public boolean isFull() {
        return index == size;
    }

    @Override
    public T pop() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("Emply Stack!");
        }
        return arr[--index];
    }

    @Override
    public T top() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("Emply Stack!");
        }
        return arr[index-1];
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public boolean push(T e) throws StackOverflowException {
        if (isFull()) {
            throw new StackOverflowException("Stack is full");
        }

        arr[index] = e;
        index++;
        return true;
    }

    @Override
    public String toString(String delimiter) {
         StringBuilder s = new StringBuilder();
        for (int i = 0; i <index; i++) {
            s.append(arr[i].toString());
            if(i!=index-1)
            s.append(delimiter);
        }
        return s.toString();
    }
    @Override
    public String toString() {
         StringBuilder s = new StringBuilder();
        for (int i = 0; i <index; i++) {
            s.append(arr[i].toString());
            //if(i!=index-1)
            //s.append(delimiter);
        }
        return s.toString();
    }
    

    @SuppressWarnings("unchecked")
	@Override
    public void fill(ArrayList<T> list) {
        arr =   (T[]) new Object[size];
        index = 0;
        try{
        for(T t:list) {
            push(t);
        }
        }catch(StackOverflowException ex) {
            System.out.println(ex);
        }
    }

}
