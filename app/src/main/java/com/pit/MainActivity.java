package com.pit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pit.UI.CustomLayouts.PitLayout.PitLayout;

public class MainActivity extends AppCompatActivity {

    ///////////////////////////////
    // UI Elements
    ///////////////////////////////

    PitLayout pitLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {

        getViews();
        setViews();
    }

    private void getViews() {

        pitLayout = (PitLayout) findViewById(R.id.clPit);
    }

    private void setViews() {

        pitLayout.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_point) {

            pitLayout.addPoint();
        }

        return super.onOptionsItemSelected(item);
    }
}
