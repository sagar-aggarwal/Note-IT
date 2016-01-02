package com.example.aggarwals.applicationdrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by AGGARWAL'S on 8/5/2015.
 */
public class ListItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListItemObject> objectItemList ;

    public ListItemAdapter(Context context,ArrayList<ListItemObject> objects ){
        mContext = context;
        objectItemList = objects;
    }
    @Override
    public int getCount() {
        return objectItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return objectItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        protected TextView editText;
        protected ImageView doneimage;
    }

    @Override
    public View getView( int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater )mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout,null);
            viewHolder.editText = (TextView)convertView.findViewById(R.id.edit_text);
            viewHolder.doneimage = (ImageView)convertView.findViewById(R.id.done_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.editText.setText(objectItemList.get(i).geteditText());
        viewHolder.editText.setId(i);
        if (objectItemList.get(i).getselected()) {
            viewHolder.doneimage.setVisibility(View.VISIBLE);
            viewHolder.editText.setPaintFlags(viewHolder.editText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            viewHolder.doneimage.setVisibility(View.INVISIBLE);
            viewHolder.editText.setPaintFlags(viewHolder.editText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        }
        viewHolder.doneimage.setId(i);
        return  convertView;
    }
    
}
