package com.example.soganote;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button startEdit;
    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        addDB();
        startEdit = (Button) findViewById(R.id.start_edit);

    }

    public void addDB(){
        ContentValues values = new ContentValues();
        values.put("content","");
        values.put("time",getTime());
        dbWriter.insert("Note",null,values);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }
}
