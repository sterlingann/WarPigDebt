package com.sterling.game.view;

import com.sterling.game.utils.BaseView;

public interface GameView extends BaseView {

    void setPlayGrids(String[][] map);

    void setReward(int row, int col);

    void movePig1(int startRow, int startCol, int endRow, int endCol);

    void movePig2(int startRow, int startCol, int endRow, int endCol);

    void updateScore(int player, int score);

}
