package com.example.aggarwals.applicationdrawer;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AGGARWAL'S on 8/14/2015.
 */
public class NotesListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NotesListViewObject> notesListViewObjects;
    private LruCache<String,Bitmap> mMemoryCache;

    public NotesListViewAdapter(Context context,ArrayList<NotesListViewObject> objects){
        mContext = context;
        notesListViewObjects = objects;
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
        if (mMemoryCache !=null)
        mMemoryCache.evictAll();
    }

    @Override
    public int getCount() {
        return notesListViewObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return notesListViewObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertview == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.custom_list_view_layout,null);
            viewHolder.maincontent = (ImageView) convertview.findViewById(R.id.notes_list_image_view);
            viewHolder.alaramview = (ImageView)convertview.findViewById(R.id.list_alarm_view);
            viewHolder.title = (TextView)convertview.findViewById(R.id.text_title_list);
            viewHolder.Date = (TextView)convertview.findViewById(R.id.list_date);
            viewHolder.content = (TextView)convertview.findViewById(R.id.notes_list_text_content);
            viewHolder.verticalbar = (LinearLayout)convertview.findViewById(R.id.verticalbar_list);
            convertview.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertview.getTag();
        }
        //Load Images....

        String key = notesListViewObjects.get(i).getIcon();
        if (key !=null) {
            Bitmap bitmap = getBitmapFromMemCache(key);
            if (bitmap != null)
                viewHolder.maincontent.setImageBitmap(bitmap);
            else {
                new BitmapWorkerTask(viewHolder.maincontent).execute(key);
            }
        }else
        viewHolder.maincontent.setVisibility(View.GONE);
        viewHolder.maincontent.setId(i);
        viewHolder.title.setText(notesListViewObjects.get(i).getTitle());
        viewHolder.title.setBackground(new ColorDrawable(Color.parseColor(notesListViewObjects.get(i).getColor())));
        viewHolder.title.setId(i);
        viewHolder.Date.setText(notesListViewObjects.get(i).getDate());
        viewHolder.Date.setId(i);
        viewHolder.alaramview.setId(i);
        viewHolder.content.setText(notesListViewObjects.get(i).getContent());
        viewHolder.content.setId(i);
        viewHolder.verticalbar.setBackground(new ColorDrawable(Color.parseColor(notesListViewObjects.get(i).getColor())));
        viewHolder.verticalbar.setId(i);
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation slide = new TranslateAnimation(-200,0,-200,0);
        slide.setInterpolator(new DecelerateInterpolator(5.0f));
        slide.setDuration(300);
        Animation fade = new AlphaAnimation(0,1.0f);
        fade.setInterpolator(new DecelerateInterpolator(5.0f));
        fade.setDuration(300);
        set.addAnimation(slide);
        set.addAnimation(fade);
        convertview.startAnimation(set);
        return convertview;
    }

    class ViewHolder {
        ImageView maincontent;
        TextView title;
        TextView content;
        ImageView alaramview;
        TextView Date;
        LinearLayout verticalbar;
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
            Bitmap bitmap = Utils.decodeSampledBitmapFromResource(key,100,136);
            addBitmapToMemoryCache(key,bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }


}
