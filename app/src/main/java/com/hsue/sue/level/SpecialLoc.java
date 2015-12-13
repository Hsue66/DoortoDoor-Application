package com.hsue.sue.level;

/**
 * Created by sue on 2015-07-30.
 */
public class SpecialLoc {

    int X,Y;

    private String[][] Specialloc = new String[][]
            {{"1,38,72","4,38,67"},
                    {"1,38,72","4,38,67"},
                    {},
                    {},
                    {},
                    {},
                    {"1,86,151","3,86,167"}};

    SpecialLoc(int loc, int level)
    {
        searchSL(loc, level);
    }

    private void searchSL(int building_idx, int level)
    {
        for(int i=0; i < Specialloc[building_idx].length; i++)
        {
            String[] info = Specialloc[building_idx][i].split(",");
            if(Integer.parseInt(info[0]) == level)
            {
                X = Integer.parseInt(info[1]);
                Y = Integer.parseInt(info[2]);
            }
        }
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
