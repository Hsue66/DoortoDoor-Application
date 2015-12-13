package com.hsue.sue.level;

/**
 * Created by sue on 2015-07-30.
 */
public class DoorLoc {

    int S_X,S_Y,F_X,F_Y,start_idx, finish_idx;

    private String[][] Doorloc = new String[][]
            {{"204,50","81,89","47,266"}
            ,{"204,50","81,89","47,266"}
            ,{}
            ,{}
            ,{}
            ,{}
            ,{"231,131"}};

    DoorLoc(int start_loc, int finish_loc)
    {
        decide(start_loc,finish_loc);
        if(start_idx != -1)
        {
            searchS(start_loc, start_idx);
            searchF(finish_loc, finish_idx);
        }
    }

    private void decide(int start, int finish)
    {
        if((( start==1 || start == 0 )&& finish == 6) ||(( finish == 1 || finish == 0 )&& start == 6) )
        {
            start_idx = 0;
            finish_idx = 0;
        }
        else
        {
            start_idx= -1;
            finish_idx= -1;
        }
    }

    private void searchS(int building_idx, int idx)
    {
        String[] info = Doorloc[building_idx][idx].split(",");
        S_X = Integer.parseInt(info[0]);
        S_Y = Integer.parseInt(info[1]);
    }
    private void searchF(int building_idx, int idx)
    {
        String[] info = Doorloc[building_idx][idx].split(",");
        F_X = Integer.parseInt(info[0]);
        F_Y = Integer.parseInt(info[1]);
    }

    public int getS_X() {
        return S_X;
    }

    public int getS_Y() {
        return S_Y;
    }

    public int getF_X() {
        return F_X;
    }

    public int getF_Y() {
        return F_Y;
    }
}
