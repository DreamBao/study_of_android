package com.example.administrator.computegrade;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    private int sum = 0;
    private EditText etAllTestGrade;
    private EditText etAllGrade;
    private EditText etGrade;

    private TextView tvShow;
    private TextView tvShowAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAllGrade = (EditText) findViewById(R.id.allgrade);
        etAllTestGrade = (EditText) findViewById(R.id.alltestgrade);
        etGrade = (EditText) findViewById(R.id.grade);

        tvShow = (TextView) findViewById(R.id.tv_show);
        tvShowAdd = (TextView) findViewById(R.id.tv_showadd);

    }

    public void compute(View v) {
        double grade = (((Integer.parseInt(String.valueOf(etAllTestGrade.getText())) / 4) * 0.7) + (((Integer.parseInt(String.valueOf(etAllGrade.getText()))) / 3 * 0.3))) * 0.45;
        tvShow.setText(grade + "");
    }
    public void add(View v) {
        sum = sum + Integer.parseInt(etGrade.getText().toString());
        etGrade.setText("");
        tvShowAdd.setText(sum + "");
    }

    public void clear(View v) {
        sum = 0;
        tvShowAdd.setText(sum + "");
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
