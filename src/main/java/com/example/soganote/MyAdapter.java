package com.example.soganote;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Edward on 2016/7/14.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;
    private LinearLayout linearLayout;



    public MyAdapter(Context context,Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }
    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {//匹配视图

        LayoutInflater inflater = LayoutInflater.from(context);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.cell,null);
        TextView contentTv = (TextView) linearLayout.findViewById(R.id.list_text);
        TextView timeTv = (TextView) linearLayout.findViewById(R.id.list_time);
        ImageView photoIm = (ImageView) linearLayout.findViewById(R.id.list_photo);
        ImageView videoIm = (ImageView) linearLayout.findViewById(R.id.list_video);
        //获取数据
        cursor.moveToPosition(i);
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String time = cursor.getString(cursor.getColumnIndex("time"));
        String videoUri = cursor.getString(cursor.getColumnIndex("video_path"));
        String photoUri = cursor.getString(cursor.getColumnIndex("photo_path"));
        contentTv.setText(content);
        timeTv.setText(time);
        //缩略图
        photoIm.setImageBitmap(getImageThumbnail(photoUri,200,200));
        videoIm.setImageBitmap(getVideoThumbnail(videoUri,200,200, MediaStore.Images.Thumbnails.MICRO_KIND));

        return linearLayout;
    }

    //获取相片缩略图
    public Bitmap getImageThumbnail(String uri, int width, int height){
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri,options);//不知功能
        options.inJustDecodeBounds = false;
        int beWidth = options.outWidth/width;
        int beHeight = options.outHeight/height;
        int be = 1;
        if (beWidth<beHeight){
            be = beWidth;
        }else{
            be = beHeight;
        }
        if (be<=0){
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(uri,options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    //获取视频缩略图
    public Bitmap getVideoThumbnail(String uri, int width, int height, int kind){
        Bitmap bitmap;
        bitmap = ThumbnailUtils.createVideoThumbnail(uri,kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return  bitmap;
    }
}
