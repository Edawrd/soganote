package com.example.soganote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Edward on 2016/7/13.
 */
public class EditActivity extends Activity implements View.OnClickListener{

    private Button backBtn, photoBtn, videoBtn, commitBtn;
    private EditText editText;
    private ImageView editPhoto, editVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        backBtn = (Button) findViewById(R.id.back);
        photoBtn = (Button) findViewById(R.id.addPhoto);
        videoBtn = (Button) findViewById(R.id.addVideo);
        commitBtn = (Button) findViewById(R.id.commit);
        editText = (EditText) findViewById(R.id.editText);
        editPhoto = (ImageView) findViewById(R.id.editPhoto);
        editVideo = (ImageView) findViewById(R.id.editVideo);
        backBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:

                break;
            case R.id.addPhoto:

                break;
            case R.id.addVideo:

                break;
            case R.id.commit:

                break;
            default:
                break;
        }
    }
}
