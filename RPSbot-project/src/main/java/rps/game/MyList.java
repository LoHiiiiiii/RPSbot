package rps.game;

import java.util.Arrays;

public class MyList<T> {
    
    private int size = 0;
    private static final int STARTING_CAPACITY = 4; // chosen because a power of 2 and fits the amount of moves in RPS.
    private Object elements[] = new Object[STARTING_CAPACITY];

    public void add(T element) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size] = element;
        size++;
    }
    
    private void increaseCapacity() {
        elements = Arrays.copyOf(elements, elements.length * 2);
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size:" + size );
        }
        return (T) elements[index];
    }
    
    public int count(){
        return size;
    }
    
    public void clear(){
        size = 0;
        elements =  new Object[STARTING_CAPACITY];
    }
}