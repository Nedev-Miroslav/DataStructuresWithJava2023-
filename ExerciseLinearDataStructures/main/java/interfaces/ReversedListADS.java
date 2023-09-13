package interfaces;

public interface ReversedListADS<E> extends Iterable<E> {

    void add(E element);
    int size();
    int capacity();
    E get(int index);
    E removeAt(int index);
}
