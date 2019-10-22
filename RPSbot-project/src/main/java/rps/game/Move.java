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

public enum Move{
    ROCK(0),
    PAPER(1),
    SCISSORS(2);
    
    private final Integer hierarchy;

    private Move(final Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getHierarchy() {
        return hierarchy;
    }
}