package com.neetpiq.android.webmarks.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.neetpiq.android.webmarks.DatabaseHelper;
import com.neetpiq.android.webmarks.R;
import com.neetpiq.android.webmarks.adapters.WebmarkCursorAdapter;
import com.neetpiq.android.webmarks.utils.ToastUtils;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_list);

        // Gets the database helper to access the database for the application
        DatabaseHelper database = new DatabaseHelper(this);

        // Setup cursor adapter
        WebmarkCursorAdapter webmarkAdapter = new WebmarkCursorAdapter(this, database.queryWebmarks());

        // Attach cursor adapter to the ListView
        mListView.setAdapter(webmarkAdapter);
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
            ToastUtils.showToast(getBaseContext(), "Settings item menu");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
