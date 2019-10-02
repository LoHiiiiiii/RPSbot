package rps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import rps.game.MyHashMap;

public class TestHashMap {

    @Test
    public void testKeysAndClear(){
        MyHashMap<Integer, Integer> hp = new MyHashMap<>();
        hp.put(1,0);
        assertEquals(1, (int)(hp.keys()[0]));
        assertEquals(1, hp.size());
        hp.clear();
        assertEquals(0, hp.size());
    }
}