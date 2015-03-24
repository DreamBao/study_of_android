package com.example.administrator.teststackview;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.StackView;


public class MainActivity extends Activity {

    private StackView mStackView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] imagesId = new int[] {R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4};


        mStackView = (StackView) findViewById(R.id.sv_image);

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
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return iv;
            }
        };
        mStackView.setAdapter(adapter);
    }


    public void next(View v) {
        mStackView.showNext();
    }

    public void prev(View v) {
        mStackView.showPrevious();
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
