package com.sterling.game.presenter;

public interface GamePresenter {

    void setupGame();

    void resetGame();

    void restartGame();

    void generateGoal();

    void startGame();

    void keepPlaying();

    void findPathForPig1();

    void findPathForPig2();
}
