package com.neetpiq.android.webmarks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neetpiq.android.webmarks.DatabaseHelper;
import com.neetpiq.android.webmarks.R;
import com.neetpiq.android.webmarks.models.Webmark;
import com.neetpiq.android.webmarks.tasks.ParseUrlTask;
import com.neetpiq.android.webmarks.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class NewWebmarkActivity extends ActionBarActivity {

    public static final String TAG = "NewWebmarkActivity";

    private TextView textView;
    private Button saveButton;

    View.OnClickListener mOnSaveClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            // Gets the database helper to access the dbHelper for the application
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(NewWebmarkActivity.this);

            try {
                // Create a webmark object
                Webmark item = new Webmark();

                JSONObject jsonObject = new JSONObject((String)textView.getText());

                item.setUrl(jsonObject.getString("url"));
                item.setTitle(jsonObject.getString("title"));
                item.setDescription(jsonObject.getString("description"));
                item.setMetadata(jsonObject.getString("metadata"));

                //Insert the webmak in the database
                dbHelper.insertWebMark(item);

//                dbHelper.close();

                ToastUtils.showToast(NewWebmarkActivity.this, "Saved to Webmarks");

            } catch (Throwable ex) {
                Log.e(TAG, "Error saving webmark", ex);
                ToastUtils.showToast(NewWebmarkActivity.this, "Unable to save");
            }

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_webmark);

        textView = (TextView) findViewById(R.id.webmark_content);

        // Sets the onClick method for the button that creates the project
        saveButton = (Button) this.findViewById(R.id.save_webmark);
        saveButton.setOnClickListener(mOnSaveClickListener);

        ParseUrlTask.ResponseCallback callback = new ParseUrlTask.ResponseCallback() {
            @Override
            public void processFinish(Object output) {

                try {
                    JSONObject jsonObject = new JSONObject((String)output);
                    int indentSpaces = 2;
                    textView.setText(jsonObject.toString(indentSpaces));
                } catch (JSONException ex) {
                    String msg = "Error formatting json object";
                    textView.setText(msg);
                    Log.e(TAG, msg, ex);
                }

            }
        };

        //get the received intent
        Intent receivedIntent = getIntent();

        //get the action
        String receivedAction = receivedIntent.getAction();
        Log.d(TAG, "Received action: [" + receivedAction + "]");

        //find out what we are dealing with
        String receivedType = receivedIntent.getType();
        Log.d(TAG, "Received type: [" + receivedType + "]");

        //make sure it's an action and type we can handle
        if (receivedAction.equals(Intent.ACTION_SEND)) {
            //content is being shared
            if (receivedType.startsWith("text/")) {
                //handle sent text
                //get the received text
                String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                Log.d(TAG, "Received text: [" + receivedText + "]");

                //check we have a string
                if (receivedText != null) {

                    textView.setText(receivedText);
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

}
