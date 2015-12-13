package com.hsue.sue.level;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class OutMapActivity extends FragmentActivity {

    private int[][] MAP;
    private int b1,b2,startX, startY, finishX, finishY;
    private int startPinX, startPinY, finishPinX,finishPinY;
    String startF,finishF;
    DoorLoc door;
    Intent i;
    ImageButton startB,finishB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        RelativeLayout relate = (RelativeLayout)findViewById(R.id.outmap);

        int[] start = (int [])getIntent().getExtras().get("msg1");
        int[] finish =  (int []) getIntent().getExtras().get("msg2");

        int inout = (int)getIntent().getExtras().get("inout");
        b1 = (int)getIntent().getExtras().get("b1");
        b2 = (int)getIntent().getExtras().get("b2");

        startF = (String)getIntent().getExtras().get("floorS");
        finishF = (String)getIntent().getExtras().get("floorF");

        startX = (int)getIntent().getExtras().get("startX");
        startY = (int)getIntent().getExtras().get("startY");
        finishX = (int)getIntent().getExtras().get("finishX");
        finishY = (int)getIntent().getExtras().get("finishY");


        MapView mView = new MapView(this,inout,b1,0);
        mView.makeMap();
        MAP = mView.getOriginal_map();

        Algo algo = new Algo(start[0], start[1], finish[0], finish[1], MAP);
        algo.searchPath();

        mView.paintMap(algo.getPath());
//        setContentView(mView);

        door = new DoorLoc(b1,b2);

        startB = (ImageButton)findViewById(R.id.start);
        startB.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeView();
                i.putExtra("s",0);
                i.putExtra("midX",door.getS_X());
                i.putExtra("midY",door.getS_Y());
                startActivityForResult(i, 0);
            }
        });
        finishB = (ImageButton)findViewById(R.id.finish);
        finishB.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeView();
                i.putExtra("s",1);
                i.putExtra("midX",door.getF_X());
                i.putExtra("midY",door.getF_Y());
                startActivityForResult(i, 0);
            }
        });

        setPinButton();

        relate.addView(mView,0);

        setContentView(relate);
    }

    private void setPinButton()
    {
        setPinLocation(b1);
        RelativeLayout.LayoutParams startBParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        startBParams.topMargin = startPinX;
        startBParams.leftMargin = startPinY;
        startB.setLayoutParams(startBParams);

        setPinLocation(b2);
        RelativeLayout.LayoutParams finishBParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        finishBParams.topMargin = finishPinX;
        finishBParams.leftMargin = finishPinY;
        finishB.setLayoutParams(finishBParams);

    }

    private void setPinLocation(int b)
    {
        if(b == b1) {
            if (b == 0){
                startPinX = 100; startPinY = 800;}
            else if(b == 1){
                startPinX = 40; startPinY = 1000;}
            else if(b == 6){
                startPinX = 410; startPinY = 50;}
        }
        else if(b == b2) {
            if (b == 0){
                finishPinX = 100; finishPinY = 800;}
            else if(b == 1){
                finishPinX = 40; finishPinY = 1000;}
            else if(b == 6){
                finishPinX = 410; finishPinY = 50;}
        }
    }

    public void ChangeView() {
        i = new Intent(OutMapActivity.this, FloorActivity.class);
        i.putExtra("inout",0);
        i.putExtra("floorS",startF);
        i.putExtra("floorF",finishF);
        i.putExtra("startX",startX);
        i.putExtra("startY",startY);
        i.putExtra("finishX",finishX);
        i.putExtra("finishY",finishY);
        i.putExtra("b1",b1);
        i.putExtra("b2",b2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
