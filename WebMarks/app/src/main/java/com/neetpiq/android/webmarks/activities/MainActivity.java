package com.neetpiq.android.webmarks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neetpiq.android.webmarks.DatabaseHelper;
import com.neetpiq.android.webmarks.DatabaseHelper.WebmarkCursor;
import com.neetpiq.android.webmarks.R;
import com.neetpiq.android.webmarks.adapters.WebmarkCursorAdapter;
import com.neetpiq.android.webmarks.models.Webmark;
import com.neetpiq.android.webmarks.utils.ToastUtils;


public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private WebmarkCursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_list);
        TextView emptyText = (TextView) findViewById(android.R.id.empty);
        mListView.setEmptyView(emptyText);

        // Gets the database helper to access the database for the application
        DatabaseHelper database = DatabaseHelper.getInstance(MainActivity.this);

        mCursor = database.queryWebmarks();

        // Setup cursor adapter
        WebmarkCursorAdapter webmarkAdapter = new WebmarkCursorAdapter(this, mCursor);

        // Attach cursor adapter to the ListView
        mListView.setAdapter(webmarkAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebmarkCursor cursor = (WebmarkCursor)parent.getItemAtPosition(position);
                Webmark item = cursor.getWebmark();

//                String message = "Item on position: " + position;
                if(item != null) {
//                    message = item.getTitle();
                    startActivity(ViewWebmarkActivity.getStartIntent(MainActivity.this, item));
                }

//                ToastUtils.showToast(MainActivity.this, message, ToastUtils.Duration.LONG);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mCursor.requery();
        ((WebmarkCursorAdapter)mListView.getAdapter()).notifyDataSetChanged();
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
            ToastUtils.showToast(getBaseContext(), "Settings View");
            return true;
        } else if (id == R.id.action_refresh) {
            Toast.makeText(MainActivity.this, "Refreshing View", Toast.LENGTH_SHORT).show();
            onRefreshData();
            return true;
        } else if(id== R.id.action_about) {
            ToastUtils.showToast(getBaseContext(), "About View");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onRefreshData() {
        mCursor.requery();
        ((WebmarkCursorAdapter)mListView.getAdapter()).notifyDataSetChanged();
    }
}
