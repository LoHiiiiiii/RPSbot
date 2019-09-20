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
public interface SelectionMethod {
    int getScore(int currentScore, Move playerMove, Move opponentMove, Boolean chosenOne);
}
