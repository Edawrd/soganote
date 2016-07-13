package com.example.soganote;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private Button startEdit;
    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        noteDB = new NoteDB(this);
        dbWriter = noteDB.getWritableDatabase();
        addDB();
        startEdit = (Button) findViewById(R.id.start_edit);
        startEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addDB(){
        ContentValues values = new ContentValues();
        values.put("content","");
        values.put("time",getTime());
        dbWriter.insert("Note",null,values);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public String getTime(){

        String str = System.currentTimeMillis()+"";
        return str;
    }
}
