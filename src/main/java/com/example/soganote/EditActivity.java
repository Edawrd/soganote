package com.example.soganote;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by Edward on 2016/7/13.
 */
public class EditActivity extends Activity implements View.OnClickListener{

    private ImageButton backBtn, photoBtn, videoBtn, commitBtn;
    private EditText editText;
    private ImageView editPhoto;
    private VideoView editVideo;
    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;
    private File photoFile,videoFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        iniView();
        setClick();
        if (getIntent().getStringExtra("content")!= null){
            editText.setText(getIntent().getStringExtra("content"));
            if (getIntent().getStringExtra("photo_path").equals("null")){
                editPhoto.setVisibility(View.GONE);
            }else {
                editPhoto.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("photo_path"));
                editPhoto.setImageBitmap(bitmap);
            }
            if (getIntent().getStringExtra("video_path").equals("null")){
                editVideo.setVisibility(View.GONE);
            }else {
                editVideo.setVisibility(View.VISIBLE);
                editVideo.setVideoURI(Uri.parse(getIntent().getStringExtra("video_path")));
                editVideo.start();
            }

        }

        if (savedInstanceState != null){
            String tempData = savedInstanceState.getString("key");
            editText.setText(tempData);
        }
    }

    public void iniView(){
        backBtn = (ImageButton) findViewById(R.id.back);
        photoBtn = (ImageButton) findViewById(R.id.addPhoto);
        videoBtn = (ImageButton) findViewById(R.id.addVideo);
        commitBtn = (ImageButton) findViewById(R.id.commit);
        editText = (EditText) findViewById(R.id.edit_text);
        editPhoto = (ImageView) findViewById(R.id.edit_photo);
        editVideo = (VideoView) findViewById(R.id.edit_video);
        noteDB = new NoteDB(this);
        dbWriter = noteDB.getWritableDatabase();

    }

    public void setClick(){
        backBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.addPhoto://启动照相
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"
                        +getTime()+".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent,1);
                break;
            case R.id.addVideo://启动录像
                intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"
                        +getTime()+".mp4");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
                startActivityForResult(intent,2);
                break;
            case R.id.commit:
                if ( addDB()){
                    finish();
                }
                break;
            default:
                break;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            editPhoto.setImageBitmap(bitmap);
        }else  if (requestCode == 2){
            editVideo.setVideoURI(Uri.fromFile(videoFile));
            editVideo.start();
        }
    }

    //简易草稿功能   暂未成功！！
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = editText.getText().toString();
        outState.putString("key",tempData);
    }

    //向table中添加数据
    public boolean addDB(){
        ContentValues values = new ContentValues();
        //判断有无内容
        if (!(editText.getText().toString()).equals("")|| videoFile != null || photoFile != null){

            values.put("content",editText.getText().toString());
            values.put("time",getTime());
            values.put("photo_path",photoFile+"");
            values.put("video_path",videoFile+"");
            if ((getIntent().getStringExtra("fromShowAty")).equals("reEdit")){
                dbWriter.update("Note",values,"id = "+getIntent().getIntExtra("id",0),null);
            }else{
                dbWriter.insert("Note",null,values);
            }
            return true;
        }
        Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
        return false;

    }

    //获取当前时间     未完成！！！
    @TargetApi(Build.VERSION_CODES.N)
    public String getTime(){
        /*long t = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t);*/
        String str = System.currentTimeMillis()+"";
        return str;
    }
}
