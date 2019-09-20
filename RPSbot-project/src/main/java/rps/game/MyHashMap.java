/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

import java.util.HashMap;

/**
 *
 * @author vertt
 */
public class MyHashMap<K, V> {
    //For now just a wrapper for Java's hashmap
    
    HashMap<K, V> hashMap;
    
    public MyHashMap(){
        hashMap = new HashMap<>();
    }
    
    public void put(K key, V value){
        hashMap.put(key, value);
    }
    
    public V get(K key){
        return hashMap.get(key);
    }
    
    public void replace(K key, V value){
        hashMap.replace(key, value);
    }
    
    public Object[] keys(){
        return hashMap.keySet().toArray();
    }
}
