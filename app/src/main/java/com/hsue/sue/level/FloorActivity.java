package com.hsue.sue.level;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class FloorActivity extends FragmentActivity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private TextView title;
    private int startF,finishF;
    private int b1,b2, startX, startY, finishX, finishY,midX,midY,S;
    SpecialLoc mid1, mid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);

        int inout = (int)getIntent().getExtras().get("inout");
        b1 = (int)getIntent().getExtras().get("b1");
        b2 = (int)getIntent().getExtras().get("b2");

        String start = (String)getIntent().getExtras().get("floorS");
        String finish = (String)getIntent().getExtras().get("floorF");

        startX = (int)getIntent().getExtras().get("startX");
        startY = (int)getIntent().getExtras().get("startY");
        finishX = (int)getIntent().getExtras().get("finishX");
        finishY = (int)getIntent().getExtras().get("finishY");

        if(inout == 0)
        {
            S = (int)getIntent().getExtras().get("s");
            midX = (int)getIntent().getExtras().get("midX");
            midY = (int)getIntent().getExtras().get("midY");
            decideSameFloor(start,finish);
            if(S == 0)
            {
                if(startF == 1)
                {
                    Intent i = new Intent(FloorActivity.this, IndoorActivity.class);
                    i.putExtra("inout", 1);
                    i.putExtra("floor", startF);
                    i.putExtra("startX", startX);  
                    i.putExtra("startY", startY);
                    i.putExtra("finishX", midX);
                    i.putExtra("finishY", midY);
                    i.putExtra("b1", b1);
                    i.putExtra("b2", b2);
                    startActivityForResult(i, 0);
                }
                else
                {
                    makeView(S,start);
                }
            }
            else
            {
                if(finishF == 1)
                {
                    Intent i = new Intent(FloorActivity.this, IndoorActivity.class);
                    i.putExtra("inout", 1);
                    i.putExtra("floor", finishF);
                    i.putExtra("startX", midX);
                    i.putExtra("startY", midY);
                    i.putExtra("finishX", finishX);
                    i.putExtra("finishY", finishY);
                    i.putExtra("b1", b2);
                    i.putExtra("b2", b1);
                    startActivityForResult(i, 0);
                }
                else
                {
                    makeView(S,finish);
                }
            }
        }
        else {
            decideSameFloor(start, finish);

            // not same floor
            if (startF != finishF) {

                makeView(start, finish);

                mid2 = new SpecialLoc(b2, finishF);
            } else {
                Intent i = new Intent(FloorActivity.this, IndoorActivity.class);
                i.putExtra("inout", 1);
                i.putExtra("floor", startF);
                i.putExtra("startX", startX);
                i.putExtra("startY", startY);
                i.putExtra("finishX", finishX);
                i.putExtra("finishY", finishY);
                i.putExtra("b1", b1);
                i.putExtra("b2", b2);
                startActivityForResult(i, 0);
            }
        }
    }


    private void decideSameFloor(String s, String f)
    {
        String[] s_arr = s.split(" ");
        String[] f_arr = f.split(" ");

        startF = Character.getNumericValue(s_arr[1].charAt(0));
        finishF =Character.getNumericValue(f_arr[1].charAt(0));
    }


    private void makeView(int S, String building)
    {
        title = (TextView) findViewById(R.id.title);
        title.setText(building);

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple);

        if(S == 0) {
            adapter.add(building + " -> " + startF + "Elevator");
            adapter.add("1Elevator" + " -> " + "Door");
        }
        else{
            adapter.add("Door"+ " -> " +"1Elevator");
            adapter.add(finishF + "Elevator" + " -> " + building);
        }
        if(S == 0) {
            list = (ListView) findViewById(R.id.list_view);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView listview = (ListView) parent;
                    //String item = (String) listview.getItemAtPosition(position);
                    // Toast.makeText(FloorActivity.this, item, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(FloorActivity.this, IndoorActivity.class);
                    i.putExtra("inout", 1);
                    i.putExtra("b1", b1);
                    i.putExtra("b2", b2);
                    if (position == 0) {
                        mid1 = new SpecialLoc(b1, startF);
                        i.putExtra("floor", startF);
                        i.putExtra("startX", startX);
                        i.putExtra("startY", startY);
                        i.putExtra("finishX", mid1.getX());
                        i.putExtra("finishY", mid1.getY());
                    } else if (position == 1) {
                        mid2 = new SpecialLoc(b1, 1);
                        i.putExtra("floor", 1);
                        i.putExtra("startX", mid2.getX());
                        i.putExtra("startY", mid2.getY());
                        i.putExtra("finishX", midX);
                        i.putExtra("finishY", midY);
                    }

                    startActivityForResult(i, 0);
                }
            });
        }
        else
        {
            list = (ListView) findViewById(R.id.list_view);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listview = (ListView) parent;
                //String item = (String) listview.getItemAtPosition(position);
                // Toast.makeText(FloorActivity.this, item, Toast.LENGTH_LONG).show();
                Intent i = new Intent(FloorActivity.this, IndoorActivity.class);
                i.putExtra("inout", 1);
                i.putExtra("b1", b2);
                i.putExtra("b2", b1);
                if (position == 0) {
                    mid1 = new SpecialLoc(b2, 1);
                    i.putExtra("floor", 1);
                    i.putExtra("startX", midX);
                    i.putExtra("startY", midY);
                    i.putExtra("finishX", mid1.getX());
                    i.putExtra("finishY", mid1.getY());
                } else if (position == 1) {
                    mid2 = new SpecialLoc(b2, finishF);
                    i.putExtra("floor", finishF);
                    i.putExtra("startX", mid2.getX());
                    i.putExtra("startY", mid2.getY());
                    i.putExtra("finishX", finishX);
                    i.putExtra("finishY", finishY);
                }
                startActivityForResult(i, 0);
                }
            });
        }
    }
    private void makeView(String start, String finish)
    {
        title = (TextView) findViewById(R.id.title);
        title.setText(start);

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple);

        adapter.add(start+" -> "+startF+"Elevator");
        adapter.add(finishF+"Elevator"+" -> "+finish);
        list = (ListView) findViewById(R.id.list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listview = (ListView) parent;
                //String item = (String) listview.getItemAtPosition(position);
               // Toast.makeText(FloorActivity.this, item, Toast.LENGTH_LONG).show();
                Intent i = new Intent(FloorActivity.this, IndoorActivity.class);
                i.putExtra("inout", 1);
                i.putExtra("b1", b1);
                i.putExtra("b2", b2);
                if(position == 0) {
                    mid1 = new SpecialLoc(b1, startF);
                    i.putExtra("floor",startF);
                    i.putExtra("startX", startX);
                    i.putExtra("startY", startY);
                    i.putExtra("finishX", mid1.getX());
                    i.putExtra("finishY", mid1.getY());
                }
                else if(position == 1) {
                    i.putExtra("floor",finishF);
                    i.putExtra("startX", mid2.getX());
                    i.putExtra("startY", mid2.getY());
                    i.putExtra("finishX", finishX);
                    i.putExtra("finishY", finishY);
                }

               startActivityForResult(i, 0);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_floor, menu);
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
