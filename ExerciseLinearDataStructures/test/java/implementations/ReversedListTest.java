package implementations;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversedListTest {

    @Test
    public void test(){
        ReversedList<Integer> reversedList = new ReversedList<>();
        reversedList.add(2);
        reversedList.add(3);
        reversedList.add(4);
        reversedList.add(5);
        reversedList.add(6);
        reversedList.add(7);
        reversedList.add(8);
        reversedList.add(9);



        int del = reversedList.removeAt(0);
        int current = reversedList.get(3);

        for (Integer integer : reversedList) {
            System.out.println(integer);
        }

        int size = reversedList.size();
        int capacity = reversedList.capacity();

    }

    @Test
    public void removeAtShouldRemoveCorrectElement() {
        ReversedList<Integer> list = new ReversedList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.removeAt(0);
        Assert.assertEquals(2, (int) list.get(0));
    }
}