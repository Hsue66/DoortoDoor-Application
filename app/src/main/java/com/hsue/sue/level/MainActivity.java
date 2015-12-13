package com.hsue.sue.level;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends FragmentActivity
{
    private RelativeLayout MainLayout;      // Layout
    private Button GoButton;                // Go button to start search
    private EditText ed1;                   // Start input
    private EditText ed2;                   // Finish input

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText)findViewById(R.id.ed1);
        ed2 = (EditText)findViewById(R.id.ed2);

        /** Prevent Keyboard Enter**/
        ed1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });
        ed2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        /** Hide keyboard by touch background **/
        MainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
            }
        });


        /** Go Button changes View Main to Test **/
        GoButton = (Button)findViewById(R.id.GoButton);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String txt1 = ed1.getText().toString();
                String txt2 = ed2.getText().toString();

                Building start = new Building(txt1);
                Building finish = new Building(txt2);

                // wrong input //
                if(start.getBuilding_index()== -1 || finish.getBuilding_index() == -1 ||
                   start.getPlaceX() == -1 || finish.getPlaceX() == -1  )
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong Input.", Toast.LENGTH_LONG );
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    if(start.getBuilding_index() == -1 || start.getPlaceX() == -1)
                        ed1.setText("");
                    if(finish.getBuilding_index() == -1 || finish.getPlaceX() == -1)
                        ed2.setText("");
                }

                // correct input //
                else
                {
                    Intent i;

                    // same building or electronic - mechanic building //
                    if((start.getBuilding_index() == finish.getBuilding_index()) ||
                      (start.getBuilding_index() == 0 && finish.getBuilding_index() == 1) ||
                      (start.getBuilding_index() == 1 && finish.getBuilding_index() == 0))
                    {
                       // i = new Intent(MainActivity.this, InMapActivity.class);
                        i = new Intent(MainActivity.this, FloorActivity.class);
                        i.putExtra("floorS",txt1);
                        i.putExtra("floorF",txt2);
                        i.putExtra("startX",start.getPlaceX());
                        i.putExtra("startY",start.getPlaceY());
                        i.putExtra("finishX",finish.getPlaceX());
                        i.putExtra("finishY",finish.getPlaceY());
                        i.putExtra("inout",1);
                    }
                    // different building //
                    else
                    {
                        i = new Intent(MainActivity.this, OutMapActivity.class);
                        Building b = new Building(start.getBuilding_index(),finish.getBuilding_index());
                        int[] start_loc = b.getStart();
                        int[]  finish_loc = b.getFinish();
                        i.putExtra("inout",0);
                        i.putExtra("msg1", start_loc);
                        i.putExtra("msg2", finish_loc);
                        ////////////////////////////////
                        i.putExtra("floorS",txt1);
                        i.putExtra("floorF",txt2);
                        i.putExtra("startX",start.getPlaceX());
                        i.putExtra("startY",start.getPlaceY());
                        i.putExtra("finishX",finish.getPlaceX());
                        i.putExtra("finishY",finish.getPlaceY());
                        ////////////////////////////////
                    }
                    i.putExtra("b1",start.getBuilding_index());
                    i.putExtra("b2",finish.getBuilding_index());
                    startActivityForResult(i, 0);
                }
            }
        });
    }
}
