package com.example.administrator.testflipper;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class MainActivity extends Activity {

    private AdapterViewFlipper flipper;

    private float oldX;
    private float newX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] imagesId = new int[] {R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5};

        flipper = (AdapterViewFlipper) findViewById(R.id.flipper);


        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imagesId.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView iv = new ImageView(MainActivity.this);
                iv.setImageResource(imagesId[position]);
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                iv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
             /*   iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("你选中了这张图片");
                    }
                });*/
                return iv;
            }
        };

        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    oldX = event.getX();
                    Log.d("debug", "按下时X:" + oldX);
                }

                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d("debug", "mouse is moving!");
                }

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    newX = event.getX();
                    Log.d("debug", "松开时X:" + newX);

                    if(newX - oldX > 100) {
                        prev(v);
                    }else if(oldX - newX > 100) {
                        next(v);
                    }
                }


                return true;
            }
        });

        flipper.setAdapter(adapter);
    }

    public void prev(View v) {
        //展现前一张图片
        flipper.showPrevious();
        //终止自动播放
        flipper.stopFlipping();
    }

    public void next(View v) {
        flipper.showNext();
        flipper.stopFlipping();
    }

    public void auto(View v) {
        flipper.startFlipping();
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
