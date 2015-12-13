package com.hsue.sue.level;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;


public class InMapActivity extends FragmentActivity {

    private int[][] MAP;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmap);

        int inout = (int)getIntent().getExtras().get("inout");
        int b1 = (int)getIntent().getExtras().get("b1");
        int b2 = (int)getIntent().getExtras().get("b2");
        int floor = (int)getIntent().getExtras().get("floor");
        int startX = (int)getIntent().getExtras().get("startX");
        int startY = (int)getIntent().getExtras().get("startY");
        int finishX = (int)getIntent().getExtras().get("finishX");
        int finishY = (int)getIntent().getExtras().get("finishY");

        MapView mView = new MapView(this,inout,b1,floor);
        mView.makeMap();
        MAP = mView.getOriginal_map();

        Algo algo = new Algo(startX, startY, finishX, finishY, MAP);

        algo.searchPath();

        mView.paintMap(algo.getPath());
        setContentView(mView);
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
