package implementations;


import interfaces.ReversedListADS;

import java.util.Iterator;


public class ReversedList<E> implements ReversedListADS<E> {

    private final static int INITIAL_SIZE = 2;
    private Object[] elements;
    private int size;
    private int capacity;

    public ReversedList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
        this.capacity = INITIAL_SIZE;
    }

    @Override
    public void add(E element) {
        if (this.size == this.capacity) {
            grow();
        }

        this.elements[this.size++] = element;

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public E get(int index) {

        int realIndex = this.size - index - 1;
        ensureIndex(index);

        return this.getAt(realIndex);
    }

    @Override
    public E removeAt(int index) {

        int realIndex = this.size - index - 1;
        ensureIndex(index);
        E elementToReturn = this.getAt(realIndex);
        for (int i = index; i < this.size - 1; i++) {
            elements[i] = elements[i + 1];

        }

        this.elements[this.size - 1] = null;
        this.size--;


        return elementToReturn;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            private int index = size - 1;

            @Override
            public boolean hasNext() {
                return this.index >= 0;
            }

            @Override
            public E next() {
                return (E) elements[index--];
            }
        };
    }

    private void grow() {
        this.capacity *= 2;
        Object[] grownArr = new Object[this.capacity];

        for (int i = 0; i < this.elements.length; i++) {
            grownArr[i] = this.elements[i];
        }


        this.elements = grownArr;

    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < this.size;
    }

    private void ensureIndex(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot use index %d on Array with %d elements", index, this.size));
        }
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }
}
