package implementations;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testAdd(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();
            deque.push(4);
            deque.pop();
            deque.push(5);
            deque.push(6);
            deque.push(7);

    }

}