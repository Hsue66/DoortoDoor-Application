package com.hsue.sue.level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.Stack;


public class MapView extends View
{
    private Bitmap bit, newbit;             // bitmap variable
    int [][] original_map ;                 // original map
    int [][][] rgbs;                        // rgb value for making map


    public MapView(Context context,int inout,int b1, int floor) {
        super(context);
        // TODO Auto-generated constructor stub

        selectMap(inout, b1,floor);
        init();
    }

    /** select map **/
    public void selectMap(int inout, int b1, int floor)
    {
        if(inout == 0)  // outside
        {
    bit = BitmapFactory.decodeResource(getResources(), R.drawable.ma);
}
else            // inside
        {
        if (b1 == 0 || b1 == 1 )   // electronic & mechanics building
        {
        if(floor == 1)
        bit = BitmapFactory.decodeResource(getResources(), R.drawable.ee);
        else if(floor == 4)
        bit = BitmapFactory.decodeResource(getResources(), R.drawable.ee4);
        }
        else if (b1 == 6 )   // library building
        {
        if(floor == 1)
        bit = BitmapFactory.decodeResource(getResources(), R.drawable.lb);
        else if(floor == 3)
        bit = BitmapFactory.decodeResource(getResources(), R.drawable.lb3);
        }
        }
        }

/** map initialization **/
public void init()
    {
        original_map = new int[bit.getHeight()][bit.getWidth()];

        for (int i = 0; i < bit.getHeight(); i++) {
            for (int j = 0; j < bit.getWidth(); j++) {
                original_map[i][j] = bit.getPixel(j, i);
            }
        }
    }

    /** make bitmap to map **/
    public void makeMap()
    {
        // get rgb values from pixels
        rgbs = new int [bit.getHeight()][bit.getWidth()][3];
        for(int i=0; i<bit.getHeight(); i++)
        {
            for(int j=0; j<bit.getWidth();j++)
            {
                rgbs[i][j][0]= (original_map[i][j]&0xff0000)/0x10000;
                rgbs[i][j][1]= (original_map[i][j]&0xff00)/0x100;
                rgbs[i][j][2]= original_map[i][j]&0xff;
            }
        }

        // Change pixel array to map
        for(int i=0; i<bit.getHeight(); i++)
        {
            for(int j=0; j<bit.getWidth();j++)
            {
                if(rgbs[i][j][0] >= 240 && rgbs[i][j][1] >= 240 && rgbs[i][j][2] >= 240)
                    original_map[i][j] = 0;
                else
                    original_map[i][j] = 1;
            }
        }
    }

    /** draw route in map **/
    public void paintMap(Stack<Node> path)
    {
        System.out.println(path.size());
        for(int i = 0 ; i< path.size(); i++)
        {
            rgbs[path.elementAt(i).getX()][path.elementAt(i).getY()][0]= 255;
            rgbs[path.elementAt(i).getX()][path.elementAt(i).getY()][1]= 0;
            rgbs[path.elementAt(i).getX()][path.elementAt(i).getY()][2]= 0;
        }

        int [] newPixels = new int [bit.getHeight()*bit.getWidth()];

        int num=0;

        for(int i=0; i<bit.getHeight(); i++)
        {
            for(int j=0; j<bit.getWidth();j++)
            {
                newPixels[num]=0xFF*0x1000000+rgbs[i][j][0]*0x10000+rgbs[i][j][1]*0x100+rgbs[i][j][2];
                num++;
            }
        }

        newbit=Bitmap.createBitmap(newPixels,0,bit.getWidth(),bit.getWidth(),bit.getHeight(), Bitmap.Config.ARGB_8888);
    }

    /** automatically paint **/
    protected void onDraw(Canvas canvas)
    {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Bitmap resize = Bitmap.createScaledBitmap(newbit, width, height, true);

        canvas.drawBitmap(resize, 0, 0, null);
    }

    public int[][] getOriginal_map() {
        return original_map;
    }
}
