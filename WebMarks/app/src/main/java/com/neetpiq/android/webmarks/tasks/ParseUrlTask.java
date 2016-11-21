package com.neetpiq.android.webmarks.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.neetpiq.android.webmarks.utils.UrlUtils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by edoardo on 29/08/2015.
 */
public class ParseUrlTask extends AsyncTask<String, Void, String> {

    private final static String TAG = "ParseUrlTask";

    public interface ResponseCallback {

        void processFinish(Object output);

    }

    ProgressDialog dialog;
    Context context;

    // Call back interface
    public ResponseCallback responseDelegate = null;

    public ParseUrlTask(Context ctx ,ResponseCallback callback) {
        // Assigning call back interface through constructor
        responseDelegate = callback;
        context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

        JSONObject jsonObj = new JSONObject();
        String url = strings[0];

        try {

            if(!UrlUtils.isValidUrlAndHostNotNull(url)) {
                Log.d(TAG, "Invalid Url ...");
                return "Invalid Url";
            }

            Log.d(TAG, "Connecting to [" + url + "]");
            Document doc  = Jsoup.connect(url).userAgent("Mozilla").get();
            Log.d(TAG, "Connected to [" + url + "]");

            jsonObj.put("url", url);

            // Get document (HTML page) title
            String title = doc.title();
            Log.d(TAG, "Title [" + title + "]");
            jsonObj.put("title", title);

            jsonObj.put("description", "n/a");

            JSONObject jsonMetadata = new JSONObject();

            // Get meta info
            Elements metaElems = doc.select("meta");

            for (Element metaElem : metaElems) {

                String key = "";
                String value = "";

                for (Attribute attrib : metaElem.attributes()) {

                    //*
                    if (attrib.getKey().equals("name") || attrib.getKey().equals("property") || attrib.getKey().equals("http-equiv")) {
                        key = attrib.getValue();
                    }

                    if (attrib.getKey().equals("itemprop")) {
                        key = "itemprop:" + attrib.getValue();
                    }

                    if (attrib.getKey().equals("content")) {
                        value = attrib.getValue();
                    }

                    if (attrib.getKey().equals("charset")) {
                        key = attrib.getKey();
                        value = attrib.getValue();
                    }
                    //*/

                    key = key.replace('.', ':');

                }

                Log.d(TAG, key + ": " + value);

                jsonMetadata.put(key, value);

                if(key == "description") {
                    jsonObj.put("description", value);
                }
            }

            jsonObj.putOpt("metadata", jsonMetadata);


        }
        catch(Throwable t) {
            t.printStackTrace();
            return t.getMessage();
        }

        return jsonObj.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        responseDelegate.processFinish(result);
        dialog.dismiss();
    }
}
