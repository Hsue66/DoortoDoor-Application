package com.hsue.sue.level;


public class Location {

    private int building_index;
    private String location;
    private String[][] locations = new String[][]
            {{"101,91,59","102,108,59","103,124,58" ,"104,143,59" ,"105,167,59" ,"106,189,59","108,175,40","109,150,40" ,"110,128,40" ,"111,110,40"
             ,"401,89,54", "402,104,54","403,119,54","404,134,54","405,150,54","406,164,54","407,177,54", "408,188,54","409,179,37","410,160,37","411,139,37","412,119,37","413,105,37" },
             {"102,35,128","103,35,156" ,"104,35,188" ,"105,35,214" ,"106,35,235","108,58,249","109,58,216","110-1,58,193" ,"110,58,178","111,58,157","112,58,137","113,58,125","114,58,104",
              "402,35,115","403,35,133" ,"404,35,153" ,"405,35,178" ,"406,35,199","407,35,220","408,35,238","409,55,237","410,55,205","411,55,177","412,55,144","413,55,120","414,55,98"},
             {},
             {},
             {},
             {},
             {"101,80,122","103,131,165","104,185,165","105,93,186","106,93,150", "107,93,130","301,80,140","303,133,180","304,163,180","305,200,140","306,164,101","307,136,101","309,81,117"}};
    private int X, Y;

    public Location(int index, String loc)
    {
        building_index = index;
        location = loc;
        searchL();
    }

    /** search Location in building **/
    private void searchL()
    {
        for(int i = 0 ; i < locations[building_index].length; i++)
        {
            String[] info = locations[building_index][i].split(",");
            if (info[0].equals(location)) {
                X = Integer.parseInt(info[1]);
                Y = Integer.parseInt(info[2]);
            }
        }
        if(X == 0 && Y == 0)        // no such place
        {
            X = -1;
            Y = -1;
        }
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
