import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    @Test
    public void testCreate(){
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(5);
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }
}