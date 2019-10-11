package rps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import rps.game.MyHashMap;

public class TestHashMap {

    @Test
    public void testMap(){
        MyHashMap<Integer, Integer> hp = new MyHashMap<>(1);
        assertEquals(true, hp.put(1,0));
        assertEquals(false, hp.put(1,0));
        assertEquals(null, hp.get(0));
        assertEquals(null, hp.get(null));
        assertEquals(1, hp.size());
        assertEquals(false, hp.put(null, null));
        assertEquals(1, (int)(hp.keys()[0]));
        assertEquals(1, (int)(hp.keys(new Integer[hp.size()])[0]));
        assertEquals(1, (int)(hp.keys()[0]));
        assertEquals(0, (int)hp.get(1));
        assertEquals(true, hp.replace(1,1));
        assertEquals(false, hp.replace(5,1));
        assertEquals(true, hp.put(3,2));
        assertEquals(true, hp.replace(3,3));
        assertEquals(1, (int)hp.get(1));
        assertEquals(3, (int)hp.get(3));
        hp.clear();
        assertEquals(0, hp.size());
    }
}