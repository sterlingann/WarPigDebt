package com.sterling.game.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.sterling.game.adapter.GridAdapter;
import com.sterling.game.R;
import com.sterling.game.presenter.GamePresenterImplementation;
import com.sterling.game.view.GameView;

public class MainActivity extends AppCompatActivity implements GameView{

    private GridAdapter gridAdapter;
    private GridView gridview;
    private TextView scorePig1, scorePig2;
    private Button startButton, resetButton;
    private GamePresenterImplementation presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new GamePresenterImplementation(this);
        gridview = (GridView) findViewById(R.id.gridview);
        scorePig1 = (TextView) findViewById(R.id.score_pig1);
        scorePig2 = (TextView) findViewById(R.id.score_pig2);
        resetButton = (Button) findViewById(R.id.resetBtn);
        startButton = (Button) findViewById(R.id.startBtn);

        presenter.setupGame();
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presenter.startGame();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presenter.resetGame();
            }
        });
    }

    @Override
    public void movePig1(int fromRow, int fromCol, int toRow, int toCol) {
        gridAdapter.setPig1Moved(fromRow, fromCol, toRow, toCol);
    }

    @Override
    public void movePig2(int fromRow, int fromCol, int toRow, int toCol) {
        gridAdapter.setPig2Moved(fromRow, fromCol, toRow, toCol);
    }

    @Override
    public void updateScore(int pig, int score) {
        switch(pig){
            case 1:
                scorePig1.setText("  " + score + " ");
                break;
            case 2:
                scorePig2.setText(" " + score + " ");
                break;
            default:
                break;
        }
    }

    @Override
    public void setPlayGrids(String[][] map) {
        gridAdapter = new GridAdapter(this, map);
        gridview.setAdapter(gridAdapter);
        presenter.generateGoal();
    }

    @Override
    public void setReward(int row, int col) {
        gridAdapter.updateRewardPosition(row, col);
    }

    @Override
    public Context getMyContext() {
        return MainActivity.this;
    }
}