/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

import java.util.Random;

/**
 *
 * @author vertt
 */
public class Selector implements RPSPlayer {
    
    private enum Meta {
        P0a,
        P0b,
        P1a,
        P1b,
        P2a,
        P2b
    }

    private class MetaFilter implements RPSPlayer {
    
        private Meta myMeta;
        private RPSPlayer myPlayer;
    
        public MetaFilter(Meta myMeta, RPSPlayer myPlayer){
            this.myMeta = myMeta;
            this.myPlayer = myPlayer;
        }
        /**
        * Rotates the move according to the specified meta. B type metas get an additional rotation because they would pick what the opponent should pick, so it needs to be rotated to beat that.
        * @return The move the player would choose rotated appropriately by the filter's meta
        */
        @Override
        public Move getMove(){
            Move myMove = myPlayer.getMove();
            switch(myMeta){
                case P1a: case P0b: return RPSLogic.rotateMove(myMove, 1);
                case P2a: case P1b: return RPSLogic.rotateMove(myMove, 2);
                default: return myMove;
            }
        }
        
        /**
        * Records the result, but mirrored for type b metas, so they instead think what the opponent should play.
        * @param myMove The move the player chose. Flipped with opponentMove for b type metas
        * @param myMove The move the opponent chose. Flipped with myMove for b type metas
        */
        @Override
        public void recordResult(Move myMove, Move opponentMove){
            switch(myMeta){
                case P0a: case P1a: case P2a: myPlayer.recordResult(myMove, opponentMove);
                default: myPlayer.recordResult(opponentMove, myMove);
            }
        }
        
        @Override
        public RPSPlayer clone(){
            return new MetaFilter(myMeta, myPlayer.clone());
        }
    }
    
    RPSPlayer[] playerParams; //None of these are referred in the actual sets, to simplify cloning and allowing same list to initiate multiple selectors
    
    SelectionMethod myMethod;
    MyHashMap<RPSPlayer, Integer> scores;
    MyHashMap<RPSPlayer, Move> lastChosenMoves;
    RPSPlayer lastChosenPlayer;
    Random random;
    
    /**
     * Intializes a selector with the 6 meta variants of each player and one random player
     * @param myMethod what evaluation method this selector uses
     * @param random a random seed for the random player
     * @param players an arbitary amount of RPS players that the selector will choose from, can be other selectors
     */
    public Selector(SelectionMethod myMethod, Random random, RPSPlayer... players ){
        this.myMethod = myMethod;
        playerParams = players;
        scores = new MyHashMap<>();
        lastChosenMoves = new MyHashMap<>();
        
        Meta[] metaValues = Meta.values();
        for (RPSPlayer p : players){
            for (Meta m : metaValues){
                scores.put(new MetaFilter(m, p.clone()), 0);
                lastChosenMoves.put(new MetaFilter(m, p.clone()), Move.ROCK); //Previous move shouldn't be checked with this value
            }
        }
        RPSPlayer randomPlayer = new RandomPlayer(random);
        scores.put(randomPlayer, 0);
        lastChosenMoves.put(randomPlayer, Move.ROCK);
    }
    
    /**
     * Goes through each player in the map and records what they would play. Also keeps a reference to the chosen player.
     * @return The move the first player with the highest score played
     */
    @Override
    public Move getMove(){
        int hiscore = Integer.MIN_VALUE;
        Move move = Move.ROCK;
        Move chosenMove = Move.ROCK;
        for(RPSPlayer p : (RPSPlayer[])scores.keys()){
            move = p.getMove();
            lastChosenMoves.replace(p, move);
            if (scores.get(p) > hiscore){
                hiscore = scores.get(p);
                chosenMove = move;
                lastChosenPlayer = p;
            }
        }
        return chosenMove;
    }
    
    /**
     * Records results for each player in the map and updates the score via the method
     * @param myMove what the Selector played, discarded for what each player in the map played
     * @param opponentMove
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        for(RPSPlayer p : (RPSPlayer[])scores.keys()){
            myMove = lastChosenMoves.get(p);
            p.recordResult(myMove, opponentMove);
            scores.replace(p, myMethod.getScore(scores.get(p), myMove, opponentMove, p == lastChosenPlayer));
        }
    }
    
    @Override
    public RPSPlayer clone(){
        return new Selector(myMethod, random, playerParams);
    }
    
}
