package com.example.soganote;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by Edward on 2016/7/16.
 */
public class ShowActivity extends Activity implements View.OnClickListener {

    private ImageButton backBtn, editBtn, deleteBtn;
    private TextView showText, showTime;
    private VideoView showVideo;
    private ImageView showPhoto;
    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        noteDB = new NoteDB(this);
        dbWriter = noteDB.getWritableDatabase();
        ini();

    }

    public void ini() {
        backBtn = (ImageButton) findViewById(R.id.show_back);
        editBtn = (ImageButton) findViewById(R.id.show_edit);
        deleteBtn = (ImageButton) findViewById(R.id.show_delete);
        showText = (TextView) findViewById(R.id.show_text);
        showTime = (TextView) findViewById(R.id.show_time);
        showVideo = (VideoView) findViewById(R.id.show_video);
        showPhoto = (ImageView) findViewById(R.id.show_photo);

        if (getIntent().getStringExtra("photo_path").equals("null")){
            showPhoto.setVisibility(View.GONE);
        }else {
            showPhoto.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra("video_path").equals("null")){
            showVideo.setVisibility(View.GONE);
        }else {
            showVideo.setVisibility(View.VISIBLE);
        }
        showText.setText(getIntent().getStringExtra("content"));
        showTime.setText(getIntent().getStringExtra("time"));
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("photo_path"));
        showPhoto.setImageBitmap(bitmap);
        showVideo.setVideoURI(Uri.parse(getIntent().getStringExtra("video_path")));
        showVideo.start();

        backBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_back:
                finish();
                break;
            case R.id.show_edit:
                reEdit();
                finish();
                break;
            case R.id.show_delete:
                dbDelte();
                finish();
                break;
            default:
                break;
        }
    }

    public void reEdit(){
        Intent intent = new Intent(ShowActivity.this,EditActivity.class);
        intent.putExtra("id",getIntent().getIntExtra("id",0));
        intent.putExtra("content",getIntent().getStringExtra("content"));
        intent.putExtra("photo_path",getIntent().getStringExtra("photo_path"));
        intent.putExtra("video_path",getIntent().getStringExtra("video_path"));
        intent.putExtra("fromShowAty","reEdit");
        startActivity(intent);
    }

    public void dbDelte(){
        dbWriter.delete("Note","id="+getIntent().getIntExtra("id",0),null);
    }
}



