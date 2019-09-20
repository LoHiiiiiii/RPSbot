package rps.game;

/**
 *
 * @author vertt
 */
public interface RPSPlayer {
    Move getMove();
    void recordResult(Move myMove, Move opponentMove);
    RPSPlayer clone();
}
