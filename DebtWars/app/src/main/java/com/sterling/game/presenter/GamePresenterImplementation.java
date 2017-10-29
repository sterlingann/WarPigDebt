package com.sterling.game.presenter;

import android.os.AsyncTask;

import com.sterling.game.pathFinder.Grid2d;
import com.sterling.game.pathFinder.GridState;
import com.sterling.game.view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePresenterImplementation implements GamePresenter {

    private final GameView view;
    private int rewardRow, rewardCol;
    private int scorePig1, scorePig2;
    private boolean gameEnd, isKeepPlaying;
    private List<Grid2d.MapNode> pig1Node = new ArrayList<Grid2d.MapNode>();
    private List<Grid2d.MapNode> pig2Node = new ArrayList<Grid2d.MapNode>();

    private String[][] startMap = {{GridState.Pig1Current, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
            {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
            {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
            {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
            {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
            {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
            {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Pig2Current}};

    private double[][] map = {{-1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, -1}};

    private Grid2d map2d = new Grid2d(map);

    public GamePresenterImplementation(GameView view) {
        this.view = view;
    }

    @Override
    public void setupGame() {
        view.setPlayGrids(startMap);
    }

    @Override
    public void resetGame() {
        pig1Node.clear();
        pig2Node.clear();
        scorePig1 = 0;
        scorePig2 = 0;
        view.updateScore(1, scorePig1);
        view.updateScore(2, scorePig2);
        restartGame();
    }

    @Override
    public void generateGoal() {
        Random r = new Random();
        int rewardPosition = r.nextInt(48 - 1) + 1;
        rewardCol = rewardPosition % startMap[0].length;
        rewardRow = (rewardPosition - rewardCol) / startMap[0].length;
        view.setReward(rewardRow, rewardCol);
    }

    @Override
    public void startGame() {
        findPathForPig1();
    }

    @Override
    public void keepPlaying() {
        if (!gameEnd) {
            isKeepPlaying = true;
            startGame();
        } else {
            resetGame();
        }
    }

    @Override
    public void findPathForPig1() {

        new PathFinder1().execute();
    }

    @Override
    public void findPathForPig2() {

        new PathFinder2().execute();

    }

    private void updateVisitedNode(int row, int col) {
        map[row][col] = -1;
    }

    @Override
    public void restartGame() {
        String[][] resetMap = {{GridState.Pig1Current, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
                {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
                {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
                {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
                {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
                {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty},
                {GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Empty, GridState.Pig2Current}};

        double[][] newMap = {{-1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, -1}};

        isKeepPlaying = false;
        map = newMap;
        startMap = resetMap;
        pig1Node.clear();
        pig2Node.clear();
        gameEnd = false;
        map2d = new Grid2d(map);
        view.setPlayGrids(startMap);
    }

    private class PathFinder1 extends AsyncTask<String, Void, int[]> {
        @Override
        protected int[] doInBackground(String... urls) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (isKeepPlaying) {
                int fromRow = pig1Node.get(1).getRow();
                int fromCol = pig1Node.get(1).getCol();
                pig1Node = map2d.findPath(fromRow, fromCol, rewardRow, rewardCol);
                return new int[]{fromRow, fromCol};
            } else {
                pig1Node = map2d.findPath(0, 0, rewardRow, rewardCol);
                return new int[]{0, 0};
            }
        }

        @Override
        protected void onPostExecute(int[] result) {
            if (isKeepPlaying) {
                updateVisitedNode(pig1Node.get(1).getRow(), pig1Node.get(1).getCol());
                view.movePig1(result[0], result[1], pig1Node.get(1).getRow(), pig1Node.get(1).getCol());
                if (pig1Node.get(1).getRow() == rewardRow && pig1Node.get(1).getCol() == rewardCol) {
                    gameEnd = true;
                    scorePig1++;
                    view.updateScore(1, scorePig1);
                }
                if (!gameEnd) {
                    findPathForPig2();
                } else {
                    restartGame();
                }
            } else {
                updateVisitedNode(pig1Node.get(1).getRow(), pig1Node.get(1).getCol());
                view.movePig1(0, 0, pig1Node.get(1).getRow(), pig1Node.get(1).getCol());
                if (pig1Node.get(1).getRow() == rewardRow && pig1Node.get(1).getCol() == rewardCol) {
                    gameEnd = true;
                    scorePig1++;
                    view.updateScore(1, scorePig1);
                }
                if (!gameEnd) {
                    findPathForPig2();
                } else
                    restartGame();
            }
        }
    }

    private class PathFinder2 extends AsyncTask<String, Void, int[]> {
        @Override
        protected int[] doInBackground(String... urls) {
            int[] position = {0, 0};
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (isKeepPlaying) {
                position[0] = pig2Node.get(1).getRow();
                position[1] = pig2Node.get(1).getCol();
                pig2Node = map2d.findPath(position[0], position[1], rewardRow, rewardCol);
                return position;
            } else {
                pig2Node = map2d.findPath(6, 6, rewardRow, rewardCol);
                return position;
            }

        }

        @Override
        protected void onPostExecute(int[] result) {
            if (isKeepPlaying) {
                updateVisitedNode(pig2Node.get(1).getRow(), pig2Node.get(1).getCol());
                view.movePig2(result[0], result[1], pig2Node.get(1).getRow(), pig2Node.get(1).getCol());
                if (pig2Node.get(1).getRow() == rewardRow && pig2Node.get(1).getCol() == rewardCol) {
                    gameEnd = true;
                    scorePig2++;
                    view.updateScore(2, scorePig2);
                }
                if (!gameEnd)
                    keepPlaying();
                else
                    restartGame();
            } else {
                updateVisitedNode(pig2Node.get(1).getRow(), pig2Node.get(1).getCol());
                view.movePig2(6, 6, pig2Node.get(1).getRow(), pig2Node.get(1).getCol());
                if (pig2Node.get(1).getRow() == rewardRow && pig2Node.get(1).getCol() == rewardCol) {
                    gameEnd = true;
                    scorePig2++;
                    view.updateScore(2, scorePig2);
                }
                if (!gameEnd)
                    keepPlaying();
                else
                    restartGame();
            }
        }
    }
}
