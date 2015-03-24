package com.example.administrator.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity implements View.OnClickListener{

    private AdapterViewFlipper flipper;
    private Button btNext;
    private Button btPrev;
    private Button btAuto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] imagesId = new int[] {R.drawable.w, R.drawable.s1, R.drawable.s3, R.drawable.s4, R.drawable.s5};

        flipper = (AdapterViewFlipper) findViewById(R.id.flipper);
        btAuto = (Button) findViewById(R.id.bt_auto);
        btNext = (Button) findViewById(R.id.bt_next);
        btPrev = (Button) findViewById(R.id.bt_prev);

        btPrev.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btAuto.setOnClickListener(this);


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
                //创建一个ImageView
                ImageView iv = new ImageView(MainActivity.this);
                iv.setImageResource(imagesId[position]);
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                iv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


                return iv;
            }
        };

        flipper.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_auto:
                flipper.startFlipping();
                break;

            case R.id.bt_next:
                flipper.showNext();
                flipper.stopFlipping();
                break;

            case R.id.bt_prev:
                //显示上一个组件
                flipper.showPrevious();
                //停止自动播放
                flipper.stopFlipping();
                break;
        }
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
