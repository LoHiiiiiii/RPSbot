package rps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import rps.game.MyList;

public class TestList {

    @Test
    public void testGetAndCapacity() {
        MyList<Integer> list = new MyList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        assertEquals(1, (int)list.get(0));
        assertEquals(2, (int)list.get(1));
        assertEquals(2, (int)list.get(2));
        assertEquals(3, (int)list.get(3));
    }

    @Test()
    public void testCountAndClear() {
        MyList<Integer> list = new MyList<>();
        assertEquals(0, list.count());
        list.add(1);
        list.add(2);
        assertEquals(2, list.count());
        list.clear();
        assertEquals(0, list.count());
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testNegativeIndex() {
        MyList<Integer> list = new MyList<>();
        list.add(0);
        list.get(-1);
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testOverIndex() {
        MyList<Integer> list = new MyList<>();
        list.add(0);
        list.get(1);
    }
}