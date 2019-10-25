/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

/**
 *
 * @author vertt
 */
public class MyHashMap<K, V> {
    
    private final LinkedPair<K,V>[] pairs; 
    private int hashCapacity; 
    private int size = 0;
    
    public MyHashMap(){
        this(4);//Simplest used case is for the three moves
    }
    
    public MyHashMap(int hashCapacity){
        this.hashCapacity = hashCapacity;
        pairs = new LinkedPair[hashCapacity];
    }
    
    /**
     * @param key
     * @param value
     * @return Whether adding was succesful
     */
    public Boolean put(K key, V value){
        return set(key, value, false);
    }
    
    private Boolean set(K key, V value, Boolean replace){
        if (key == null)
            return false;
        int hashCode = getHashCode(key);
        LinkedPair<K,V> newPair = new LinkedPair(key, value);
        if (pairs[hashCode] == null){
            if (!replace){
                pairs[hashCode] = newPair;
                size++;
            }
            return !replace;
        } else {
            LinkedPair<K,V> current = pairs[hashCode];
            LinkedPair<K,V> previous = null;
            while(current != null){
                if (current.key.equals(key)){
                    if (replace){
                        if (previous != null){
                            newPair.next = current.next;
                            previous.next = newPair;
                        } else {
                            pairs[hashCode] = newPair;
                        }
                        return true;
                    }
                    return false;
                }
                previous = current;
                current = current.next;
            }
            if (!replace && previous != null){
                previous.next = newPair;
                size++;
                return true;
            }
        }
        return false;
    }
    
    public V get(K key){
        if (key == null || pairs == null)
            return null;
        int expectedHashCode = getHashCode(key);
        LinkedPair<K,V> current;
        if (pairs[expectedHashCode] != null){
            current = pairs[expectedHashCode];
            while(current != null){
                if (current.key.equals(key))
                    return current.value;
                current = current.next;
            }
        }
        
        /*//Checking whether the hashcode has changed
        for (int i = 0; i < pairs.length; ++i) {
            current = pairs[i];
            while(current != null){
                if (current.key.equals(key)){
                    return current.value;
                }
                current = current.next;
            }
        }*/
        return null;
    }
    
    public Boolean replace(K key, V value){
        return set(key, value, true);
    }
    
    public Object[] keys(){
        return keys((K[])new Object[size]);
    }
    
    public K[] keys(K[] array){
        int index = 0;
        LinkedPair current;
        for(LinkedPair p : pairs){
            current = p;
            while (current != null){
                array[index] = (K)current.key;
                index++;
                current = current.next;
            }
        }
        return array;
    }
    
    public int size(){
        return size;
    }
    
    public void clear(){
        size = 0;
        LinkedPair current, next;
        for(int i = 0; i < pairs.length; ++i){
            current = pairs[i];
            while (current != null){
                next = current.next;
                current.next = null;
                current = next;
            }
            pairs[i] = null;
        }
    }
    
    private int getHashCode(K key){
        if (key instanceof Move){
            return ((Move)key).getHierarchy();
        }
        return Math.abs(key.hashCode()) % hashCapacity;
    }
    
    private class LinkedPair<K,V>{
        public final K key;
        public V value;
        public LinkedPair<K,V> next = null;
        
        public LinkedPair(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
}
