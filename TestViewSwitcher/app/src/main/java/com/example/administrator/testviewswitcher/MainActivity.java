package com.example.administrator.testviewswitcher;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewSwitcher仿Launcher实现方法
 * 创建了ViewSwitcher以后，实现仿照Launcher步骤应该是这样的：
 * 1、确定每个屏幕包含多少个程序icon，定义一个整型确定当前所在页面;
 * 2、ViewSwitcher其实每个页面也是View，而每个View中的内容填充就是 setFactory()方法;
 * 3、在每个页面中icon呈表格存在，即是用GridView填充返回;
 * 4、然后就自然想到GridView也是继承于AdapterView，那么用adapter来填充其中内容即可了
 */
public class MainActivity extends Activity {

    //定义一个常量，用于显示每屏显示的应用程序数
    public static final int NUMBER_PER_SCREEN = 12;
    //记录当前正在显示第几屏的程序
    private int screenNo = -1;
    //保存程序所占的总屏数
    private int screenCount;
    //代表应用程序的内部
    public static class DataItem {
        public String title;
        public Drawable icon;
    }

    //保存系统程序个数的list集合
    private List<DataItem> items = new ArrayList<DataItem>();

    //填充界面
    private LayoutInflater minflater;

    private ViewSwitcher vsLauncher;
    private Button btPrev;
    private Button btNext;

    //定义滑屏时的左右偏移量
    float oldX = 0;
    float nowX = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建40个程序填充进入list当中
        for(int i = 0; i < 40; i++) {
            DataItem item = new DataItem();
            item.title = i + "";
            item.icon = getResources().getDrawable(R.drawable.ic_launcher);
            items.add(item);
        }

        minflater = LayoutInflater.from(MainActivity.this);

        vsLauncher = (ViewSwitcher) findViewById(R.id.vs_launcher);
        //计算程序占屏幕总数
        screenCount = items.size() % NUMBER_PER_SCREEN == 0 ? items.size() / NUMBER_PER_SCREEN : items.size() / NUMBER_PER_SCREEN + 1;

        vsLauncher.setFactory(new ViewSwitcher.ViewFactory() {
            //实际上就是返回了一个GridView组件
            @Override
            public View makeView() {
                GridView view = new GridView(MainActivity.this);
                view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                view.setHorizontalSpacing(10);
                view.setVerticalSpacing(10);
                view.setNumColumns(3);
                view.setGravity(Gravity.CENTER);
                return view;
            }
        });

        vsLauncher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("debug", "进入了ontouch方法");
                Log.d("debug", "ontouch:" + event.getX() + "");
                //按下时记录当前的位置
                float  positionX = event.getX();

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    oldX =  positionX;
                    Log.d("debug", "按下:" + oldX  + "");
                }

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    nowX = positionX;
                    Log.d("debug", "离开:" + nowX + "");


                    if((oldX - nowX) > 100) {
                        Log.d("debug", "oldX - nowX >100");
                        if(screenNo < screenCount - 1) {
                            Log.d("debug", "屏幕向右滑");
                            screenNo++;

                            vsLauncher.setInAnimation(MainActivity.this, R.anim.slide_in_right);
                            vsLauncher.setOutAnimation(MainActivity.this, R.anim.slide_out_left);
                            //控制下一屏要显示的内容
                            ((GridView) vsLauncher.getNextView()).setAdapter(adapter);

                            vsLauncher.showNext();
                        }
                    }else if((nowX - oldX) > 100) {
                        Log.d("debug", nowX + "-" + oldX + "=" + (nowX - oldX));
                        Log.d("debug", "进入左滑");
                        if(screenNo > 0) {
                            screenNo--;
                            vsLauncher.setInAnimation(MainActivity.this, android.R.anim.slide_in_left);
                            vsLauncher.setOutAnimation(MainActivity.this, android.R.anim.slide_out_right);
                            //控制下一屏要显示的内容
                            ((GridView) vsLauncher.getNextView()).setAdapter(adapter);

                            vsLauncher.showPrevious();
                        }
                    }
                }
                return true;
            }
        });

        //页面加载时先显示第一屏
        next(null);

    }




    public void prev(View v) {
        if(screenNo > 0) {
            screenNo--;

            vsLauncher.setInAnimation(this, android.R.anim.slide_in_left);
            vsLauncher.setOutAnimation(this, android.R.anim.slide_out_right);
            //控制下一屏要显示的内容
            ((GridView)vsLauncher.getNextView()).setAdapter(adapter);

            vsLauncher.showPrevious();
        }
    }

    public void next(View v) {
        if(screenNo < screenCount - 1) {
            screenNo++;

            vsLauncher.setInAnimation(this, R.anim.slide_in_right);
            vsLauncher.setOutAnimation(this, R.anim.slide_out_left);
            //控制下一屏要显示的内容
            ((GridView)vsLauncher.getNextView()).setAdapter(adapter);

            vsLauncher.showNext();
        }
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if(screenNo == screenCount - 1 && items.size() / NUMBER_PER_SCREEN != 0) {
                return items.size() % NUMBER_PER_SCREEN;
            }
            return NUMBER_PER_SCREEN;
        }

        @Override
        public DataItem getItem(int position) {
            return items.get(screenNo * NUMBER_PER_SCREEN + position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = minflater.inflate(R.layout.labelicon, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
            icon.setImageDrawable(getItem(position).icon);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(getItem(position).title);
            return convertView;
        }
    };


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
