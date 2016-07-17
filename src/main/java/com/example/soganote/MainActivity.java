package com.example.soganote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ImageButton startEdit;
    private ListView listView;
    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;
    private MyAdapter myAdapter;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        noteDB = new NoteDB(this);
        dbWriter = noteDB.getWritableDatabase();
        startEdit = (ImageButton) findViewById(R.id.start_edit);
        listView = (ListView) findViewById(R.id.list);
        startEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("id",cursor.getInt(cursor.getColumnIndex("id")));
                intent.putExtra("content",cursor.getString(cursor.getColumnIndex("content")));
                intent.putExtra("photo_path",cursor.getString(cursor.getColumnIndex("photo_path")));
                intent.putExtra("video_path",cursor.getString(cursor.getColumnIndex("video_path")));
                startActivity(intent);


            }
        });



    }
    //获取全部数据并添加至ListView
    public void getDB(){
        cursor = dbWriter.query("Note",null,null,null,null,null,null);
        myAdapter = new MyAdapter(this,cursor);
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDB();
    }


}
