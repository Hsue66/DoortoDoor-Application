package com.hsue.sue.level;

/**
 * Created by sue on 2015-07-23.
 */
public class Building {

   private String building, loc;        // separate input
   private int building_index;          // building number

   private int start , finish;
   private int startX, startY, finishX, finishY;
   private String[][] doors = new String[][]
                    {{"149,389","55,404" },
                     {"149,389","55,404", "39,509" },
                     {"142,511","87,511" , "39,512" },
                     {"137,292","131,341" },
                     {"201,306"},
                     {"138,182"},
                     {"182,75"}};
    private int PlaceX, PlaceY;

   /** building **/
   public Building(String b)
   {
       // separate building and location
       String[] building_arr = b.split(" ");
       building = building_arr[0];
       if(building_arr.length > 1)
           loc = building_arr[1];

       searchB();                               // search building

       /////////////////나중에지울것/////////////////////
       if( building_arr.length == 1)
       {
           System.out.println(building_arr.length);
           PlaceX = 0;
           PlaceY = 0;
       }
       /////////////////////////////////////
       else if(building_index != -1) {
           searchL(building_index);              // search location
       }
   }

    /** building to building **/
    public Building(int s, int f)
    {
        start = s;
        finish = f;
        checkRoute();
    }

    /** search Building **/
    public void searchB()
    {
        if(building.equals("전자관"))
            building_index = 0;
        else if(building.equals("기계관"))
            building_index = 1;
        else if(building.equals("과학관"))
            building_index = 2;
        else if(building.equals("학생회관"))
            building_index = 3;
        else if(building.equals("항공우주센터"))
            building_index = 4;
        else if(building.equals("본관"))
            building_index = 5;
        else if(building.equals("도서관"))
            building_index = 6;
        else                                    // wrong input
            building_index = -1;
    }

    /** search Location **/
    public void searchL(int index)
    {
        Location place = new Location(index,loc);
        PlaceX = place.getX();
        PlaceY = place.getY();
    }

    /** check route building to building **/
    public void checkRoute()
    {
        // 1:1 route
        if(doors[start].length == 1)
        {
            String[] door = doors[start][0].split(",");
            startX = Integer.parseInt(door[0]);
            startY = Integer.parseInt(door[1]);
        }
        if(doors[finish].length == 1)
        {
            String[] door = doors[finish][0].split(",");
            finishX = Integer.parseInt(door[0]);
            finishY = Integer.parseInt(door[1]);
        }

        // 1:many route
    //////////////////////////////////////코드정리할것/////////////////////////
        if(startX==0 && startY == 0 && finishX != 0 &&finishY != 0)
        {
            int [] temp = new int[doors[start].length];
            for(int i = 0; i<doors[start].length; i++)
            {
                String[] door = doors[start][i].split(",");
                startX = Integer.parseInt(door[0]);
                startY = Integer.parseInt(door[1]);

                temp[i] = Math.abs(finishX - startX)+Math.abs(finishY - startY);
            }

            int best = temp[0];
            int best_idx = 0;
            for(int i =0 ; i< temp.length; i++)
            {
                if(best > temp[i]) {
                    best = temp[i];
                    best_idx = i;
                }
            }

            String[] door = doors[start][best_idx].split(",");
            startX = Integer.parseInt(door[0]);
            startY = Integer.parseInt(door[1]);
        }

        if(startX!=0 && startY != 0 && finishX == 0 &&finishY == 0)
        {
            int [] temp = new int[doors[finish].length];
            for(int i = 0; i<doors[finish].length; i++)
            {
                String[] door = doors[finish][i].split(",");
                finishX = Integer.parseInt(door[0]);
                finishY = Integer.parseInt(door[1]);

                temp[i] = Math.abs(finishX - startX)+Math.abs(finishY - startY);
            }

            int best = temp[0];
            int best_idx = 0;
            for(int i =0 ; i< temp.length; i++)
            {
                if(best > temp[i]) {
                    best = temp[i];
                    best_idx = i;
                }
            }

            String[] door = doors[finish][best_idx].split(",");
            finishX = Integer.parseInt(door[0]);
            finishY = Integer.parseInt(door[1]);
        }
    }

    /** get method **/

    public int[] getStart()
    {
        return new int[]{startX,startY};
    }

    public int[] getFinish()
    {
        return new int[]{finishX,finishY};
    }

    public int getBuilding_index() {
        return building_index;
    }

    public int getPlaceX() {
        return PlaceX;
    }

    public int getPlaceY() {
        return PlaceY;
    }
}
