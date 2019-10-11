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
public class Pair<K,V>{
    public final K key;
    public V value;
        
    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
}
