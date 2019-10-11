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
    
    private final Pair<K,V>[] pairs; 
    private int hashCapacity = 4; //Simplest used case is for the three moves
    private int size = 0;
    
    public MyHashMap(){
        pairs = new Pair[hashCapacity];
    }
    
    public MyHashMap(int hashCapacity){
        this.hashCapacity = hashCapacity;
        pairs = new Pair[hashCapacity];
    }
    
    /**
     *
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
        Pair<K,V> newPair = new Pair(key, value);
        if (pairs[hashCode] == null){
            if (!replace){
                pairs[hashCode] = newPair;
                size++;
            }
            return !replace;
        }
        else {
            Pair<K,V> current = pairs[hashCode];
            Pair<K,V> previous = null;
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
        if (key == null)
            return null;
        int expectedHashCode = getHashCode(key);
        Pair<K,V> current;
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
        Pair current;
        for(Pair p : pairs){
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
        Pair current, next;
        for(int i = 0; i < pairs.length; ++i){
            current = pairs[i];
            next = null;
            while (current != null){
                next = current.next;
                current.next = null;
                current = next;
            }
            pairs[i] = null;
        }
    }
    
    private int getHashCode(K key){
        return Math.abs(key.hashCode()) % hashCapacity;
    }
    
    private class Pair<K,V>{
        public final K key;
        public V value;
        public Pair<K,V> next = null;
        
        public Pair(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
}
