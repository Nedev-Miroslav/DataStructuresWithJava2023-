package implementations;

import interfaces.List;


import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {

    private final static int INITIAL_SIZE = 4;
    private Object[] elements;
    private int size;
    private int capacity;

    public ArrayList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
        this.capacity = INITIAL_SIZE;
    }

    @Override
    public boolean add(E element) {
        if (this.size == this.capacity) {
            grow();

        }

        this.elements[this.size++] = element;


        return true;
    }


    @Override
    public boolean add(int index, E element) {

        if (!isValidIndex(index)) {
            return false;

        }

        shiftRight(index);

        this.elements[index] = element;
        this.size++;


        return true;
    }


    @Override
    public E get(int index) {
        ensureIndex(index);


        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        ensureIndex(index);

        Object current = this.elements[index];
        this.elements[index] = element;


        return (E) current;
    }

    @Override
    public E remove(int index) {
        ensureIndex(index);
        Object current = this.elements[index];

        shiftLeft(index);
        this.size--;

        shrinkIfNeeded();

        return (E) current;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) >= 0;

    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
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

    // Друга имплементация на grow метода
    // private Object[] grow() {
    // return Arrays.copyOf(this.elements, this.elements.length * 2);
    // }

    private void shiftRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.elements[i + 1] = this.elements[i];

        }

    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];

        }
    }

    private void shrinkIfNeeded() {
        if (this.size > this.capacity / 3) {
            return;
        }
        this.capacity /= 2;
        this.elements = Arrays.copyOf(this.elements, this.capacity);

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
}