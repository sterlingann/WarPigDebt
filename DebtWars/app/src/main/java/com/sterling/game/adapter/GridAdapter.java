package com.sterling.game.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sterling.game.R;
import com.sterling.game.pathFinder.GridState;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private int rowPosition, columnPosition, count;
    private String[][] gridContent;

    public GridAdapter(Context c, String[][] content) {
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.gridContent = content;
        mContext = c;
        count = 49;
        rowPosition = 0;
        columnPosition = 0;
    }

    public int getCount() {
        return count;
    }

    public int getRowCount(){
        return gridContent.length;
    }

    public int getColumnCount(){
        return gridContent[0].length;
    }

    public Object getItem(int rowNum, int columnNum) {
        return gridContent[rowNum][columnNum];
    }

    public Object getItem(int position) {
        columnPosition = position % (gridContent[0].length);
        rowPosition = (position - columnPosition)/gridContent[0].length;
        return gridContent[rowPosition][columnPosition];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ImageView gridImage;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.layout_grid_view, null);
            gridImage = (ImageView) rowView.findViewById(R.id.image);
        } else{
            gridImage = (ImageView) convertView.findViewById(R.id.image);
        }
        columnPosition = position % (gridContent[0].length);
        rowPosition = (position - columnPosition)/gridContent[0].length;
        if(gridContent[rowPosition][columnPosition].equals(GridState.Pig1Current))
           gridImage.setImageResource(mThumbIds[2]);
        else if(gridContent[rowPosition][columnPosition].equals(GridState.Pig2Current))
            gridImage.setImageResource(mThumbIds[0]);
        else if(gridContent[rowPosition][columnPosition].equals(GridState.Goal))
            gridImage.setImageResource(mThumbIds[3]);
        else if(gridContent[rowPosition][columnPosition].equals(GridState.Pig1Visited))
            gridImage.setImageResource(mThumbIds[4]);
        else if(gridContent[rowPosition][columnPosition].equals(GridState.Pig2Visited))
            gridImage.setImageResource(mThumbIds[5]);
        else
            gridImage.setImageResource(mThumbIds[6]);

        return rowView;
    }

    public void updateRewardPosition(int row, int col){
        gridContent[row][col] = GridState.Goal;
        notifyDataSetChanged();
    }

    public void setPig1Moved(int startRow, int startColumn, int endRow, int endColumn){
        gridContent[startRow][startColumn] = GridState.Pig1Visited;
        gridContent[endRow][endColumn] = GridState.Pig1Current;
        notifyDataSetChanged();
    }

    public void setPig2Moved(int startRow, int startColumn, int endRow, int endColumn){
        gridContent[startRow][startColumn] = GridState.Pig2Visited;
        gridContent[endRow][endColumn] = GridState.Pig2Current;
        notifyDataSetChanged();
    }
    private Integer[] mThumbIds = {
            R.drawable.bluepig,
            R.drawable.images,
            R.drawable.greenpig,
            R.drawable.outofdebt,
            R.drawable.greenvisited,
            R.drawable.bluevisited,
            R.drawable.greycreditcard
    };

    };

