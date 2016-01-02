package com.example.aggarwals.applicationdrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by AGGARWAL'S on 8/13/2015.
 */
public class NotesGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NotesGridViewObject> notesGridViewObjects;
    private LruCache<String,Bitmap> mMemoryCache;

    public NotesGridViewAdapter(Context context,ArrayList<NotesGridViewObject> objects){
        mContext = context;
        notesGridViewObjects = objects;
    }

    public void reserve_cache_memory(){
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public void clearcachememory(){
        mMemoryCache.evictAll();
    }

    @Override
    public int getCount() {
        return notesGridViewObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return notesGridViewObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        Log.d("Convert View :", i + "");
        if (convertview == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.custom_grid_view_layout,null);
            viewHolder.maincontent = (ImageView) convertview.findViewById(R.id.mian_content_imag_view);
            viewHolder.alaramview = (ImageView)convertview.findViewById(R.id.grid_alarm_view);
            viewHolder.title = (TextView)convertview.findViewById(R.id.text_title);
            viewHolder.Date = (TextView)convertview.findViewById(R.id.grid_date);
            convertview.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertview.getTag();
        }
        //Load Images....

        String key = notesGridViewObjects.get(i).getIcon();
        Bitmap bitmap = getBitmapFromMemCache(key);
        if (bitmap != null)
            viewHolder.maincontent.setImageBitmap(bitmap);
        else {
            new BitmapWorkerTask(viewHolder.maincontent).execute(key);
        }

        viewHolder.maincontent.setId(i);
        viewHolder.title.setText(notesGridViewObjects.get(i).getTitle());
        viewHolder.title.setBackground(new ColorDrawable(Color.parseColor(notesGridViewObjects.get(i).getColor())));
        viewHolder.title.setId(i);
        viewHolder.Date.setText(notesGridViewObjects.get(i).getDate());
        viewHolder.Date.setId(i);
        viewHolder.alaramview.setId(i);

        
        return convertview;
    }

    class ViewHolder {
        ImageView maincontent;
        TextView title;
        ImageView alaramview;
        TextView Date;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    class BitmapWorkerTask extends AsyncTask<String,Void,Bitmap> {

        private ImageView imageView;

        public  BitmapWorkerTask(ImageView imageView){
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String key = strings[0];
            Bitmap bitmap = Utils.decodeSampledBitmapFromResource(key,210,135);
            addBitmapToMemoryCache(key,bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
