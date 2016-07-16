package com.example.soganote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Edward on 2016/7/13.
 */
public class NoteDB extends SQLiteOpenHelper{

    Context context;

    public static final String CREATE_TABLE = "create table Note("
            +"id integer primary key autoincrement,"
            +"content text,"
            +"time text,"
            +"photo_path text,"
            +"video_path text)";

    public NoteDB(Context context) {
        super(context, "Note", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
