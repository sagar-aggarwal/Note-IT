package com.example.aggarwals.applicationdrawer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by AGGARWAL'S on 8/17/2015.
 */
public class ArchiveFragment extends Fragment {
    public static final int RESULT_CODE = 0;
    public static final int UPDATE_CODE = 1;
    public static final int COPY_CODE = 2;
    public static final String UNIQUEID = "Unique-1";
    public static final String OBJEECTIDENTIFY = "Identifiaction Of Object";
    private int update_position = 0;
    private GridView notesfragment_gridview = null;
    private ListView notesfragment_listview = null;
    private ImageView clipart = null;
    private ArrayList<NotesGridViewObject> objects = new ArrayList<NotesGridViewObject>();
    private NotesGridViewAdapter gridViewAdapter = null;
    private ArrayList<NotesListViewObject> objectArrayList = new ArrayList<NotesListViewObject>();
    private NotesListViewAdapter notesListViewAdapter = null;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private boolean custom_view_button = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.archive_fragment,container,false);
        notesfragment_gridview = (GridView)rootview.findViewById(R.id.main_grid_view);
        notesfragment_listview = (ListView)rootview.findViewById(R.id.main_list_view);
        clipart = (ImageView)rootview.findViewById(R.id.clipart);


        gridViewAdapter = new NotesGridViewAdapter(getActivity().getApplicationContext(),objects);
        notesfragment_gridview.setAdapter(gridViewAdapter);
        notesListViewAdapter = new NotesListViewAdapter(getActivity(),objectArrayList);
        notesfragment_listview.setAdapter(notesListViewAdapter);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem("Delete", R.drawable.ic_delete_black_24dp));
        navDrawerItems.add(new NavDrawerItem("UnArchive", R.drawable.ic_archive_black_24dp));
        navDrawerItems.add(new NavDrawerItem("Send", R.drawable.ic_send_black_24dp));
        navDrawerItems.add(new NavDrawerItem("Add Label",R.drawable.ic_add_black_24dp));
        adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(),navDrawerItems);
        File temp = new File(Environment.getExternalStorageDirectory() + "/Note IT");
        temp.mkdir();
        File orignal = new  File(temp.getAbsolutePath()+"/Databases");
        orignal.mkdir();

        if (custom_view_button == false)
            updateallnotesgrid();
        else
            updateallnoteslist();
        //GridView Onitem Selected

        notesfragment_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(),AddNote.class);
                intent.putExtra(UNIQUEID,5);
                update_position = i;
                if (custom_view_button == false)
                    intent.putExtra(OBJEECTIDENTIFY,objects.get(i).getIcon());
                else
                    intent.putExtra(OBJEECTIDENTIFY,objectArrayList.get(i).getIcon());
                startActivityForResult(intent,UPDATE_CODE);
            }
        });
        notesfragment_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(),AddNote.class);
                intent.putExtra(UNIQUEID,5);
                update_position = i;
                if (custom_view_button == false)
                    intent.putExtra(OBJEECTIDENTIFY,objects.get(i).getIcon());
                else
                    intent.putExtra(OBJEECTIDENTIFY,objectArrayList.get(i).getIcon());
                startActivityForResult(intent,UPDATE_CODE);
            }
        });

        notesfragment_gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder alertselectoption = new AlertDialog.Builder(getActivity());
                alertselectoption.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                try {
                                    NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity());
                                    boolean r = notesAddDBHelper.deletArchiveByPath(objects.get(position).getIcon());
                                    if (objects.get(position).getIcon().contains("Bitmap's"))
                                        Utils.deletebitmap(objects.get(position).getIcon());
                                    objects.remove(position);
                                    gridViewAdapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Item Removed", Toast.LENGTH_LONG).show();
                                } catch (SQLException e) {
                                }
                                break;
                            case 1: try {
                                NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity());
                                Cursor c = notesAddDBHelper.retrieveArchive(objects.get(position).getIcon());
                                long id = notesAddDBHelper.insertNotes(c.getString(1),
                                        c.getString(2),
                                        c.getString(3),
                                        c.getString(4),
                                        c.getString(5),
                                        c.getString(6),
                                        c.getString(7),
                                        c.getString(8),
                                        c.getInt(9),
                                        c.getInt(10));
                                boolean r = notesAddDBHelper.deletArchiveByPath(objects.get(position).getIcon());
                                objects.remove(position);
                                gridViewAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "UnArchived", Toast.LENGTH_LONG).show();
                            }catch (SQLException e){}
                                break;
                            case 3:  try {
                                final NotesAddDBHelper helper = new NotesAddDBHelper(getActivity());
                                final Cursor c = helper.retrieveArchive(objects.get(position).getIcon());
                                if (c != null){
                                    c.moveToFirst();
                                    if (c.getString(5) == null){
                                        LayoutInflater inflater = getActivity().getLayoutInflater();
                                        View addcustom = inflater.inflate(R.layout.add_list_edit_text,null);
                                        final EditText edit_add =(EditText)addcustom.findViewById(R.id.add_list_edit_text);
                                        final AlertDialog.Builder adddialog = new AlertDialog.Builder(getActivity());
                                        adddialog.setTitle("Add Item")
                                                .setView(addcustom)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        try {
                                                            helper.updateArchive(c.getString(3), c.getString(1),
                                                                    c.getString(2),
                                                                    c.getString(3),
                                                                    c.getString(4),
                                                                    edit_add.getText().toString(),
                                                                    c.getString(6),
                                                                    c.getString(7),
                                                                    c.getString(8),
                                                                    c.getInt(9),
                                                                    c.getInt(10));
                                                        }catch (SQLException e) {}
                                                    }
                                                }).show();

                                    }else
                                        Toast.makeText(getActivity(),"Label Already Present",Toast.LENGTH_LONG).show();
                                }
                            }catch (SQLException e){}
                                break;
                        }
                    }
                }).show();
                return true;
            }
        });

        notesfragment_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder alertselectoption = new AlertDialog.Builder(getActivity());
                alertselectoption.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                try {
                                    NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity());
                                    boolean r = notesAddDBHelper.deletArchiveByPath(objectArrayList.get(position).getIcon());
                                    if (objectArrayList.get(position).getIcon().contains("Bitmap's"))
                                        Utils.deletebitmap(objectArrayList.get(position).getIcon());
                                    objectArrayList.remove(position);
                                    notesListViewAdapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Item Removed", Toast.LENGTH_LONG).show();
                                } catch (SQLException e) {
                                }
                                break;
                            case 1: try {
                                NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity());
                                Cursor c = notesAddDBHelper.retrieveArchive(objectArrayList.get(position).getIcon());
                                long id = notesAddDBHelper.insertNotes(c.getString(1),
                                        c.getString(2),
                                        c.getString(3),
                                        c.getString(4),
                                        c.getString(5),
                                        c.getString(6),
                                        c.getString(7),
                                        c.getString(8),
                                        c.getInt(9),
                                        c.getInt(10));
                                boolean r = notesAddDBHelper.deletArchiveByPath(objectArrayList.get(position).getIcon());
                                objectArrayList.remove(position);
                                notesListViewAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "UnArchived", Toast.LENGTH_LONG).show();
                            }catch (SQLException e){}
                                break;
                            case 3:  try {
                                final NotesAddDBHelper helper = new NotesAddDBHelper(getActivity());
                                final Cursor c = helper.retrieveArchive(objectArrayList.get(position).getIcon());
                                if (c != null){
                                    c.moveToFirst();
                                    if (c.getString(5) == null){
                                        LayoutInflater inflater = getActivity().getLayoutInflater();
                                        View addcustom = inflater.inflate(R.layout.add_list_edit_text,null);
                                        final EditText edit_add =(EditText)addcustom.findViewById(R.id.add_list_edit_text);
                                        final AlertDialog.Builder adddialog = new AlertDialog.Builder(getActivity());
                                        adddialog.setTitle("Add Item")
                                                .setView(addcustom)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        try {
                                                            helper.updateArchive(c.getString(3),c.getString(1),
                                                                    c.getString(2),
                                                                    c.getString(3),
                                                                    c.getString(4),
                                                                    edit_add.getText().toString(),
                                                                    c.getString(6),
                                                                    c.getString(7),
                                                                    c.getString(8),
                                                                    c.getInt(9),
                                                                    c.getInt(10));
                                                        }catch (SQLException e) {}
                                                    }
                                                }).show();

                                    }else
                                        Toast.makeText(getActivity(),"Label Already Present",Toast.LENGTH_LONG).show();
                                }
                            }catch (SQLException e){}
                                break;

                        }
                    }
                }).show();
                return true;
            }
        });

        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case COPY_CODE:
            case RESULT_CODE: if (resultCode == AddNote.RESULT_OK){
                Cursor c = null;
                try {
                    NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity().getApplicationContext());
                    c = notesAddDBHelper.retrieveAllArchive();
                }catch (SQLException e){}
                if (c.moveToLast()) {
                    if (custom_view_button == false) {
                        objects.add(new NotesGridViewObject(c.getString(1), c.getString(3), c.getString(6), false, c.getString(4)));
                        gridViewAdapter.notifyDataSetChanged();
                    }else {
                        objectArrayList.add(new NotesListViewObject(c.getString(1), c.getString(3), c.getString(6), false, c.getString(4),c.getString(2)));
                        notesListViewAdapter.notifyDataSetChanged();
                    }
                }
            }break;
            case UPDATE_CODE: if (resultCode == AddNote.RESULT_OK){
                String Updated_path = data.getStringExtra(AddNote.UPDATED_PATH_INTENT);
                try {
                    NotesAddDBHelper helper = new NotesAddDBHelper(getActivity());
                    Cursor c = helper.retrieveArchive(Updated_path);
                    if (c.moveToFirst()) {
                        if (custom_view_button == false) {
                            objects.set(update_position, new NotesGridViewObject(c.getString(1), c.getString(3), c.getString(6), false, c.getString(4)));
                            gridViewAdapter.notifyDataSetChanged();
                        } else {
                            objectArrayList.set(update_position, new NotesListViewObject(c.getString(1), c.getString(3), c.getString(6), false, c.getString(4), c.getString(2)));
                            notesListViewAdapter.notifyDataSetChanged();
                        }
                    }
                }catch (SQLException e){}
            }break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.custom_view: ;custom_view_button  = (custom_view_button == false) ? true : false ;
                if (custom_view_button == false)
                    updateallnotesgrid();
                else
                    updateallnoteslist();
                break;
        }
        return  false;
    }

    public void updateallnotesgrid () {
        objectArrayList.clear();
        objects.clear();
        notesfragment_listview.setVisibility(View.GONE);
        notesfragment_gridview.setVisibility(View.VISIBLE);
        notesListViewAdapter.clearcachememory();
        gridViewAdapter.reserve_cache_memory();
        gridViewAdapter.notifyDataSetChanged();
        notesListViewAdapter.notifyDataSetChanged();
        Cursor c = null;
        try {
            NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity().getApplicationContext());
            c = notesAddDBHelper.retrieveAllArchive();
        }catch (SQLException e){}
        if (c.moveToFirst()){
            clipart.setVisibility(View.INVISIBLE);
            do {
                objects.add(new NotesGridViewObject(c.getString(1), c.getString(3), c.getString(6), false, c.getString(4)));
                gridViewAdapter.notifyDataSetChanged();
            }while (c.moveToNext());
        }
    }
    public void updateallnoteslist () {
        objects.clear();
        objectArrayList.clear();
        notesListViewAdapter.reserve_cache_memory();
        gridViewAdapter.clearcachememory();
        gridViewAdapter.notifyDataSetChanged();
        notesListViewAdapter.notifyDataSetChanged();
        notesfragment_gridview.setVisibility(View.GONE);
        notesfragment_listview.setVisibility(View.VISIBLE);
        Cursor c = null;
        try {
            NotesAddDBHelper notesAddDBHelper = new NotesAddDBHelper(getActivity().getApplicationContext());
            c = notesAddDBHelper.retrieveAllArchive();
        }catch (SQLException e){}
        if (c.moveToFirst()){
            clipart.setVisibility(View.INVISIBLE);
            do {
                objectArrayList.add(new NotesListViewObject(c.getString(1), c.getString(3), c.getString(6), false, c.getString(4),c.getString(2)));
                notesListViewAdapter.notifyDataSetChanged();
            }while (c.moveToNext());
        }
    }
}
