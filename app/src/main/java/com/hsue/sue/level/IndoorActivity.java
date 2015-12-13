package com.hsue.sue.level;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class IndoorActivity extends FragmentActivity {

    private int[][] MAP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        int inout = (int)getIntent().getExtras().get("inout");
        int b1 = (int)getIntent().getExtras().get("b1");
        int b2 = (int)getIntent().getExtras().get("b2");
        int floor = (int)getIntent().getExtras().get("floor");
        int startX = (int)getIntent().getExtras().get("startX");
        int startY = (int)getIntent().getExtras().get("startY");
        int finishX = (int)getIntent().getExtras().get("finishX");
        int finishY = (int)getIntent().getExtras().get("finishY");
        //RelativeLayout relate = (RelativeLayout)findViewById(R.id.inmap);

        MapView mView = new MapView(this,inout,b1,floor);
        mView.makeMap();
        MAP = mView.getOriginal_map();

        //    Algo algo = new Algo(start[0], start[1], finish[0], finish[1], MAP);
        Algo algo = new Algo(startX, startY, finishX, finishY, MAP);
        //  algo.showEverything();
        algo.searchPath();

        mView.paintMap(algo.getPath());
        //relate.addView(mView,0);
        //setContentView(relate);
        setContentView(mView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_indoor, menu);
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
