package com.neetpiq.android.webmarks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neetpiq.android.webmarks.DatabaseHelper;
import com.neetpiq.android.webmarks.R;
import com.neetpiq.android.webmarks.models.Webmark;
import com.neetpiq.android.webmarks.tasks.ParseUrlTask;
import com.neetpiq.android.webmarks.utils.ToastUtils;

public class NewWebmarkActivity extends ActionBarActivity {

    private TextView textView;

    View.OnClickListener mCreateProjectOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            // Get the values to save the webmark

            /*
            TextView projectNameView = (TextView)CreateProjectActivity.this.findViewById(R.id.create_project_name);
            String projectName = projectNameView.getText().toString();

            int rowCounterAmount = mRowCountersAmountView.getValue();
            int rowsAmount = mRowsAmountView.getValue();
            //*/

            // Gets the database helper to access the database for the application
            DatabaseHelper database = new DatabaseHelper(NewWebmarkActivity.this);

            // Create a webmark objetc
            Webmark item = new Webmark("");

            //Insert the webmak in the database
            database.insertWebMark(item);

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_webmark);

        textView = (TextView) findViewById(R.id.webmark_content);

        // Sets the onClick method for the button that creates the project
        Button saveButton = (Button) this.findViewById(R.id.save_webmark);
        saveButton.setOnClickListener(mCreateProjectOnClickListener);

        //get the received intent
        Intent receivedIntent = getIntent();

        //get the action
        String receivedAction = receivedIntent.getAction();

        //find out what we are dealing with
        String receivedType = receivedIntent.getType();

        //make sure it's an action and type we can handle
        if (receivedAction.equals(Intent.ACTION_SEND)) {
            //content is being shared
            if (receivedType.startsWith("text/")) {
                //handle sent text
                //get the received text
                String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);

                //check we have a string
                if (receivedText != null) {

                    ParseUrlTask.ResponseCallback callback = new ParseUrlTask.ResponseCallback() {
                        @Override
                        public void processFinish(Object output) {
                            textView.setText((String) output);
                        }
                    };

                    ParseUrlTask task = new ParseUrlTask(this, callback);

                    task.execute(receivedText);

                } else {
                    ToastUtils.showToast(this, "No text sent!");
                    finish();
                }
            }
        } else {
            ToastUtils.showToast(this, "No send action!");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_webmark, menu);
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
