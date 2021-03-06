package com.neetpiq.android.webmarks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.neetpiq.android.webmarks.models.Webmark;

import java.util.Date;

/**
 * This class helps open, create, and upgrade the database file containing the url list.
 * <p/>
 * Created by edoardo on 23/08/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    private static DatabaseHelper mInstance = null;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;

    // The name of the database file on the file system
    public static final String DATABASE_NAME = "webmarks.db";

    // Webmarks table name
    private static final String TABLE_WEBMARKS = "tbl_webmarks";

    // Webmarks Table Columns names
    private static final String COL_ID = "_id";
    private static final String COL_DATE_CREATED = "date_created";
    private static final String COL_URL = "url";
    private static final String COL_TITLE = "title";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_METADATA = "metadata";

    /**
     * Get the singleton instance
     * @param ctx
     * @return
     */
    public static DatabaseHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activity's
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Table
        db.execSQL("create table if not exists tbl_webmarks ("
                + "_id                 INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "date_created        INTEGER,"
                + "url                 TEXT,"
                + "title	           TEXT,"
                + "description         TEXT,"
                + "metadata            TEXT"
                + ")");

        // not sure this is useful
        db.execSQL("CREATE INDEX idx_webmarks_date_created ON tbl_webmarks(date_created)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // implement schema changes and data massage here when upgrading
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEBMARKS);
        onCreate(db);
    }

    /**
     * Inserting a new item in webmarks table checking if the url
     * already existed in database. If existed will update the old one else
     * creates a new row
     */
    public void insertWebMark(Webmark item) {

        ContentValues values = new ContentValues();
        values.put(COL_DATE_CREATED, item.getInsertDate().getTime());
        values.put(COL_URL, item.getUrl());
        values.put(COL_TITLE, item.getTitle());
        values.put(COL_DESCRIPTION, item.getDescription());
        values.put(COL_METADATA, item.getMetadata());

        SQLiteDatabase db = getWritableDatabase();

        // Check if row already existed in database
        if (!isSiteExists(db, item.getUrl())) {
            db.insert(TABLE_WEBMARKS, null, values);
        } else {
            // site already existed update the row
            db.update(TABLE_WEBMARKS,
                    values,
                    COL_URL + " = ?",
                    new String[]{String.valueOf(item.getUrl())});
        }
    }

    public WebmarkCursor queryWebmarks() {
        // equivalent to "select * from webmarks order by date_created desc"
        Cursor wrapped = getReadableDatabase().query(TABLE_WEBMARKS,
                null, null, null, null, null, COL_DATE_CREATED + " desc");
        return new WebmarkCursor(wrapped);
    }

    public WebmarkCursor queryWebmark(long id) {
        Cursor wrapped = getReadableDatabase().query(TABLE_WEBMARKS,
                null, // all columns
                COL_ID + " = ?", // look for a werbmark ID
                new String[]{String.valueOf(id)}, // with this value
                null, // group by
                null, // order by
                null, // having
                "1"); // limit 1 row
        return new WebmarkCursor(wrapped);
    }

    /**
     * Checking whether a webmark is already existing (check is done by matching url
     */
    public boolean isSiteExists(SQLiteDatabase db, String url) {

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_WEBMARKS
                + " WHERE url = '" + url + "'", new String[]{});

        return (cursor.getCount() > 0);
    }

    /**
     * A convenience class to wrap a cursor that returns rows from the "webmarks" table.
     * The {@link getWebmark()} method will give you a Webmark instance representing the current row.
     */
    public class WebmarkCursor extends CursorWrapper {

        public WebmarkCursor(Cursor c) {
            super(c);
        }

        /**
         * Returns a Run object configured for the current row, or null if the current row is invalid.
         */
        public Webmark getWebmark() {
            if (isBeforeFirst() || isAfterLast())
                return null;
            Webmark webmark = new Webmark();
            webmark.setId(getLong(getColumnIndex(COL_ID)));
            webmark.setInsertDate(new Date(getLong(getColumnIndex(COL_DATE_CREATED))));
            webmark.setUrl(getString(getColumnIndex(COL_URL)));
            webmark.setTitle(getString(getColumnIndex(COL_TITLE)));
            webmark.setDescription(getString(getColumnIndex(COL_DESCRIPTION)));
            webmark.setMetadata(getString(getColumnIndex(COL_METADATA)));

            return webmark;
        }
    }

}
