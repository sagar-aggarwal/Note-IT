package com.example.aggarwals.applicationdrawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**9
 * Created by AGGARWAL'S on 8/2/2015.
 */
public class GridImageAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] imageIds;

    public GridImageAdapter(Context context,Integer[] image){
        mContext = context;
        imageIds = image;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(130,130));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackground(mContext.getResources().getDrawable(android.R.drawable.alert_light_frame));
        }else imageView = (ImageView)convertView;

        imageView.setImageResource(imageIds[i]);
        return  imageView;
    }
}
