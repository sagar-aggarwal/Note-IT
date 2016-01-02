package com.example.aggarwals.applicationdrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by AGGARWAL'S on 8/2/2015.
 */
public class GridColorView extends Activity {

    private GridView gview;

    private Integer[] imageids = {R.drawable.simple_note_layout,
            R.drawable.list_note_layout,
            R.drawable.picture_note_layout,
            R.drawable.voice_note_layout,
            R.drawable.purple_layout,
            R.drawable.red_layou,
            R.drawable.grey_layout,
            R.drawable.yellow_layout};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_color_layout);

        gview = (GridView)findViewById(R.id.gridview_color);
        gview.setAdapter(new GridImageAdapter(this,imageids));
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddNote.integer = i;
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
