package com.example.aggarwals.applicationdrawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AGGARWAL'S on 7/28/2015.
 */
public class AddNote extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_INTENT = 1;
    private static final int IMAGE_VIEW_INTENT = 2;
    private static final int CHOOSE_COLOR = 3;
    public static final String PHOTO_PATH_VIEWER_ACTIVITY = "Actionbar Color";
    public static final String UPDATED_PATH_INTENT = "updatedpathintent";
    private static int CHOICE_NOTE =0 ;
    private int UPDATE_NEW = 0;
    private int PATH_NUMBER_INTENT = 0;
    public static int integer =0;
    private int mYear = -1;
    private int mMonth = -1 ;
    private int mDay = -1;
    private int mHour = -1;
    private int mMinute = -1;
    private long repeat = -1;
    private String TempPath = null;
    private String PhotoPathGallery = null;
    private String PhotoPathCamera = null ;
    private String CurrentPhotoPath = null;
    private LinearLayout lmain_layout,listview_layout;
    private EditText etitle  , elabel,enote ;
    private TextView tdate;
    private Button breminder,bdate,btime,brepeat;
    private View headerview;
    private TableLayout tableLayout;
    private ImageButton imageButton,headerviewadd;
    private ListView listViewmain;
    private Date date;
    private Time time;
    private int imageset = 0;
    private Menu menuactivity = null;
    private MenuItem menuItem_picture = null;
    private Integer[] imageids = {R.drawable.simple_note_layout,
            R.drawable.list_note_layout,
            R.drawable.picture_note_layout,
            R.drawable.voice_note_layout,
            R.drawable.purple_layout,
            R.drawable.red_layou,
            R.drawable.grey_layout,
            R.drawable.yellow_layout};
    private String[] actionbarcolor = {"#c6ca495c","#ff027d45","#ff21727d","#ff95603c","#ffbe4bff","#b9e81f1f","#ff787878","#ccd1be2f"};
    private ArrayList<ListItemObject> listItemObjects = new ArrayList<ListItemObject>();
    private ListItemAdapter listItemAdapter;
    private Calendar csetalarmtime;
    private Calendar currenttime;


    private  DatePickerDialog.OnDateSetListener mListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
            mYear = i;
            mMonth = i1;
            mDay = i2;
            bdate.setText(mYear+"/"+(mMonth+1) + "/"+mDay);
            Toast.makeText(AddNote.this,"Date :"+mYear+"/"+(mMonth+1) + "/"+mDay,Toast.LENGTH_LONG).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener mtimelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
            mHour = i;
            mMinute = i1;
            btime.setText(+mHour+":"+mMinute);
            Toast.makeText(AddNote.this,"Time :-"+mHour+":"+mMinute,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);


        integer = 0;
        //Directory's

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Initialize Variables ...
        lmain_layout = (LinearLayout)findViewById(R.id.lmain_layout);
        listview_layout = (LinearLayout)findViewById(R.id.listview_layout);
        etitle = (EditText)findViewById(R.id.edit_title);
        elabel = (EditText)findViewById(R.id.edit_label);
        enote = (EditText)findViewById(R.id.edit_note_text);
        tableLayout = (TableLayout)findViewById(R.id.reminder_drawer);
        breminder = (Button)findViewById(R.id.button_reminder);
        bdate = (Button)findViewById(R.id.button_date);
        btime = (Button)findViewById(R.id.button_time);
        brepeat = (Button)findViewById(R.id.button_repeat);
        imageButton = (ImageButton)findViewById(R.id.image_button);
        listViewmain = (ListView)findViewById(R.id.list_note_main);

        headerview = getLayoutInflater().inflate(R.layout.headerviewlayout,null);
        headerviewadd = (ImageButton)headerview.findViewById(R.id.header_add);

        //Force Menu
        ForceMenuKeyField();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        int Uniquecode = getIntent().getIntExtra(Notes.UNIQUEID,0);
        if ( Uniquecode == 5){
            UPDATE_NEW = 1;
            updatedatabase(getIntent().getStringExtra(Notes.OBJEECTIDENTIFY));
        }else if (Uniquecode == 6){
            updatedatabase(getIntent().getStringExtra(Notes.OBJEECTIDENTIFY));
            UPDATE_NEW = 0;
        }

        CHOICE_NOTE = getIntent().getIntExtra(Notes.CHOICE_PASS,0);
        switch (CHOICE_NOTE){
            case 1 : simplenoteview();
                        break;
            case 2 : listnoteview();
                break;
            case 3 : cameranoteview();
                break;
            case 4 : voicenoteview();
                break;
        }

        breminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableLayout.getVisibility() == View.GONE) {
                    tableLayout.animate()
                            .translationY(3).alpha(1.0f).setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    tableLayout.setVisibility(View.VISIBLE);
                                }
                            });
                } else {
                    tableLayout.animate()
                            .translationY(0).alpha(0.0f).setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    tableLayout.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder date_choice = new AlertDialog.Builder(AddNote.this);
                date_choice.setTitle("Select a Date");
                final String name[] = {"Today","Tomorrow","Next Week","Select A Date..."};
                ArrayAdapter<String> date_choice_adapter = new ArrayAdapter<String>(AddNote.this,android.R.layout.select_dialog_item,name);
                date_choice.setAdapter(date_choice_adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i!=3)
                        bdate.setText(name[i]);
                        currenttime = Calendar.getInstance();
                        switch (i){
                            case 0: mYear = currenttime.get(Calendar.YEAR);
                                    mMonth = currenttime.get(Calendar.MONTH);
                                    mDay = currenttime.get(Calendar.DAY_OF_MONTH);
                                Toast.makeText(AddNote.this,"Date :"+mYear+"/"+mMonth + "/"+mDay,Toast.LENGTH_LONG).show();
                                    break;
                            case 1: currenttime.add(Calendar.DAY_OF_YEAR,1);
                                    mYear = currenttime.get(Calendar.YEAR);
                                    mMonth = currenttime.get(Calendar.MONTH);
                                    mDay = currenttime.get(Calendar.DAY_OF_MONTH);
                                Toast.makeText(AddNote.this,"Date :"+mYear+"/"+mMonth + "/"+mDay,Toast.LENGTH_LONG).show();
                                    break;
                            case 2: currenttime.add(Calendar.WEEK_OF_MONTH,1);
                                    mYear = currenttime.get(Calendar.YEAR);
                                    mMonth = currenttime.get(Calendar.MONTH);
                                    mDay = currenttime.get(Calendar.DAY_OF_MONTH);
                                Toast.makeText(AddNote.this,"Date :"+mYear+"/"+mMonth + "/"+mDay,Toast.LENGTH_LONG).show();
                                    break;
                            case 3:DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                                        mListener,
                                        currenttime.get(Calendar.YEAR),
                                        currenttime.get(Calendar.MONTH),
                                        currenttime.get(Calendar.DAY_OF_MONTH)
                                );
                                datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
                                break;

                        }

                    }
                }).show();
            }
        });

        btime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder date_choice = new AlertDialog.Builder(AddNote.this);
                date_choice.setTitle("Select a Time");
                final String name[] = {"Morning (9:00AM)","Afternoon (1:00PM)","Evening (5:00PM)","Night (8:00PM)","Select A Time..."};
                ArrayAdapter<String> date_choice_adapter = new ArrayAdapter<String>(AddNote.this,android.R.layout.select_dialog_item,name);
                date_choice.setAdapter(date_choice_adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currenttime = Calendar.getInstance();
                        if (i != 4)
                            btime.setText(name[i]);
                       switch (i){
                           case 0: mHour = 9;
                                   mMinute=0;
                               Toast.makeText(AddNote.this,"Time :-"+mHour+":"+mMinute,Toast.LENGTH_LONG).show();
                                   break;
                           case 1: mHour = 13;
                                   mMinute=0;
                                   break;
                           case 2: mHour = 17;
                                   mMinute=0;
                                   break;
                           case 3: mHour = 22;
                                   mMinute=0;
                               Toast.makeText(AddNote.this,"Time :-"+mHour+":"+mMinute,Toast.LENGTH_LONG).show();
                                    break;
                           case  4 :
                               TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                                   mtimelistener,
                                       currenttime.get(Calendar.HOUR),
                                       currenttime.get(Calendar.MINUTE),
                                       false
                               );
                               timePickerDialog.show(getFragmentManager(),"Time Picker Dialog");
                       }
                    }
                }).show();

            }
        });

        brepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder date_choice = new AlertDialog.Builder(AddNote.this);
                date_choice.setTitle("Select a Time");
                final String name[] = {"None","Daily","Weekly","Monthly"};
                ArrayAdapter<String> date_choice_adapter = new ArrayAdapter<String>(AddNote.this,android.R.layout.select_dialog_item,name);
                date_choice.setAdapter(date_choice_adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        brepeat.setText(name[i]);

                        switch (i){
                            case 0: repeat = -1;
                                    break;
                            case 1: repeat = AlarmManager.INTERVAL_DAY;
                                    break;
                            case 2: repeat = AlarmManager.INTERVAL_DAY*7;
                                    break;
                            case 3 : repeat = 30L*1440L*60000L;
                                    break;
                        }
                        Toast.makeText(AddNote.this,repeat+"",Toast.LENGTH_LONG).show();

                    }
                }).show();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout l = (LinearLayout)findViewById(R.id.image_layout);
                AlertDialog.Builder imageshow = new AlertDialog.Builder(AddNote.this);
                imageshow.setTitle(etitle.getText().toString())
                        .setView(inflater.inflate(R.layout.image_viewer,null))
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            takepictureoption();
                            }
                        })
                        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageButton.animate().alpha(0.0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                        imageButton.setImageDrawable(null);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        CurrentPhotoPath = null;
                                        imageButton.setVisibility(View.GONE);
                                        menuItem_picture = (MenuItem)menuactivity.findItem(R.id.add_photo);
                                        menuItem_picture.setVisible(true);
                                    }
                                }).start();

                            }
                        });
                final AlertDialog alertDialog = imageshow.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        ImageView imageView = (ImageView) alertDialog.findViewById(R.id.image_show);
                        Picasso.with(AddNote.this).load(new File(CurrentPhotoPath)).resize(imageView.getWidth(),imageView.getHeight()).into(imageView);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AddNote.this,ImageViewActivity.class);
                                intent.putExtra(PHOTO_PATH_VIEWER_ACTIVITY,CurrentPhotoPath);
                                startActivity(intent);
                            }
                        });
                    }
                });

                alertDialog.show();

            }
        });

        headerviewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View addcustom = inflater.inflate(R.layout.add_list_edit_text, null);
                final EditText edit_add =(EditText)addcustom.findViewById(R.id.add_list_edit_text);
                final AlertDialog.Builder adddialog = new AlertDialog.Builder(AddNote.this);
                adddialog.setTitle("Add Item")
                        .setView(addcustom)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listItemObjects.add(new ListItemObject(edit_add.getText().toString(), false));
                                    listItemAdapter.notifyDataSetChanged();
                                    Utils.setListViewHeightBasedOnChildren(listViewmain);
                                }
                            }).setNegativeButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listItemObjects.add(new ListItemObject(edit_add.getText().toString(), false));
                        listItemAdapter.notifyDataSetChanged();
                        Utils.setListViewHeightBasedOnChildren(listViewmain);

                    }
                });
                final AlertDialog finaladddailog = adddialog.create();
                finaladddailog.show();

            }
        });

        listViewmain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listItemObjects.get(i - 1).getselected() == false)
                    listItemObjects.get(i - 1).setselected(true);
                else
                    listItemObjects.get(i - 1).setselected(false);
                listItemAdapter.notifyDataSetChanged();
            }
        });

        listViewmain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int  position = i;
                AlertDialog.Builder itemchoice = new AlertDialog.Builder(AddNote.this);
                itemchoice.setMessage("Are You Sure U Wana Delete ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listItemObjects.remove(position-1);
                                listItemAdapter.notifyDataSetChanged();
                                Utils.setListViewHeightBasedOnChildren(listViewmain);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuactivity = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_note_simple, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_label : labelvisibility(item);
                                     break;
            case R.id.add_photo:    imageButton.setAlpha(1.0f);
                                    menuItem_picture = item;
                                    takepictureoption();
                                    break;
            case R.id.change_gradient :startActivityForResult(new Intent(this, GridColorView.class), CHOOSE_COLOR);
                                       break;
            case R.id.add_note_action_bar_button :  if (UPDATE_NEW == 0)
                                                    addnote();
                                                    else
                                                    updatenotescursor();
                                                    break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GALLERY_INTENT :if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                PhotoPathGallery = cursor.getString(columnIndex);
                CurrentPhotoPath = PhotoPathGallery;
                cursor.close();
                imageButton.setVisibility(View.VISIBLE);
                menuItem_picture = (MenuItem)menuactivity.findItem(R.id.add_photo);
                menuItem_picture.setVisible(false);
                Picasso.with(AddNote.this).load(new File(PhotoPathGallery)).resize(imageButton.getWidth(), imageButton.getHeight()).into(imageButton);
                imageset = 1;
                PATH_NUMBER_INTENT = 1;
                }else{
                if (imageset !=1)
                imageButton.setVisibility(View.GONE);}
                break;
            case CAMERA_REQUEST:if (resultCode == RESULT_OK) {
                imageButton.setVisibility(View.VISIBLE);
                if (menuItem_picture != null)
                menuItem_picture.setVisible(false);
                CurrentPhotoPath = PhotoPathCamera;
                Picasso.with(AddNote.this).load(new File(PhotoPathCamera)).resize(imageButton.getWidth(),imageButton.getHeight()).into(imageButton);
                imageset = 1;
                PATH_NUMBER_INTENT = 1;
                }else {
                if (imageset !=1)
                imageButton.setVisibility(View.GONE);
                    }
                break;
            case  CHOOSE_COLOR :if (resultCode == RESULT_OK){
                lmain_layout.setBackground(getResources().getDrawable(imageids[integer]));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionbarcolor[integer])));
            }break;
        }
    }


    private void ForceMenuKeyField(){
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            // presumably, not relevant
        }
    }

    private void simplenoteview(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c6ca495c")));
        integer = 0;
        imageButton.setVisibility(View.GONE);
    }

    private void listnoteview(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff027d45")));
        integer = 1;
        lmain_layout.setBackground(getResources().getDrawable(R.drawable.list_note_layout));
        listview_layout.setVisibility(View.VISIBLE);
        listViewmain.setVisibility(View.VISIBLE);
        imageButton.setVisibility(View.GONE);
        enote.setVisibility(View.GONE);
        addlist();
    }

    private void cameranoteview() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff21727d")));
        integer = 2;
        imageButton.setVisibility(View.INVISIBLE);
        takepictureoption();
        lmain_layout.setBackground(getResources().getDrawable(R.drawable.picture_note_layout));
    }

    private void voicenoteview(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff95603c")));
        integer = 3;
        lmain_layout.setBackground(getResources().getDrawable(R.drawable.voice_note_layout));
    }

    private void labelvisibility(MenuItem menuItem){
        if (elabel.getVisibility() == View.GONE){
            elabel.setVisibility(View.VISIBLE);
            menuItem.setTitle("Remove Label");
        }
        else {
            elabel.setVisibility(View.GONE);
            menuItem.setTitle("Add A Label");
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Note_It_Add_Image "+ timeStamp + " " ;
        File temp = new File(Environment.getExternalStorageDirectory() + "/Note IT");
        temp.mkdir();
        File orignal = new  File(temp.getAbsolutePath()+"/Images");
        orignal.mkdir();
        File image = File.createTempFile(imageFileName, ".jpg", orignal);
        PhotoPathCamera = image.getAbsolutePath();
        return image;
    }

    private  void setGalleryIntent(){
        Intent galleryintent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryintent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(galleryintent, GALLERY_INTENT);
            if (imageset != 1)
            imageButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setCameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            File imagebuttonfile = null;
            try{
                imagebuttonfile = createImageFile();
            }catch (Exception e){}
            if (imagebuttonfile != null){
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagebuttonfile));
                startActivityForResult(intent, CAMERA_REQUEST);
                if (imageset != 1)
                imageButton.setVisibility(View.INVISIBLE);


            }
        }
    }

    private void takepictureoption(){
        AlertDialog.Builder takepicturealert = new AlertDialog.Builder(AddNote.this);
        String str[] = {"Gallery","Camera"};
        ArrayAdapter<String> temp = new ArrayAdapter<String>(AddNote.this,android.R.layout.select_dialog_item,str);
        takepicturealert.setTitle("Add A Picture");
        takepicturealert.setAdapter(temp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:setGalleryIntent();
                            break;
                    case 1:setCameraIntent();
                            break;
                }
            }
        }).show();
    }
    private void addlist(){
        listItemAdapter = new ListItemAdapter(this,listItemObjects);
        listViewmain.setHeaderDividersEnabled(true);
        listViewmain.addHeaderView(headerview);
        listViewmain.setAdapter(listItemAdapter);
        Utils.setListViewHeightBasedOnChildren(listViewmain);
    }

    public void addnote(){
            long id = 0;
            String Title = etitle.getText().toString();
            String Body = enote.getText().toString();
            String LayoutColor = actionbarcolor[integer] ;
            String Label;
            if (elabel.getVisibility() == View.VISIBLE )
            Label = elabel.getText().toString();
            else
            Label = null;
            String Date_created = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
            String List,Checked_List;
            if (listItemObjects.size() == 0){
                List = null;
                Checked_List=null;
            }
            else {
                String string= Utils.converttoarraylist(listItemObjects);
                String s[] = string.split(",-,-,");
                List = s[0];
                Checked_List = s[1];
            }
            int alarmset = 0;
            int pathnumber = 1;
            //if CurrentPhotoView is Null;
            try {
                if (CurrentPhotoPath == null){
                    if (CHOICE_NOTE == 2) {
                        pathnumber = 2;
                        CurrentPhotoPath = Utils.storedrawablebitmap(Utils.getBitmapFromView(listViewmain));
                    }
                    else {
                        pathnumber = 3;
                        enote.setCursorVisible(false);
                        enote.setBackground(null);
                        CurrentPhotoPath = Utils.storedrawablebitmap(Utils.getBitmapFromView(enote));
                    }
                }
        }catch (IOException e){}
            try {
                NotesAddDBHelper dbHelper = new NotesAddDBHelper(this);
                id = dbHelper.insertNotes(Title, Body, CurrentPhotoPath, LayoutColor, Label, Date_created, List, Checked_List, alarmset, pathnumber);
            } catch (SQLException e) {
            }
        Toast.makeText(getApplicationContext(),pathnumber+"",Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    public void updatedatabase(String Path){
        TempPath = Path;
        Cursor c = null;
        try {
            NotesAddDBHelper dbHelper = new NotesAddDBHelper(getApplicationContext());
            c = dbHelper.retrieveNote(Path);
        }catch (SQLException e){}
        if (c !=null) {
            if (c.moveToFirst()) {
                int i;
                etitle.setText(c.getString(1));
                enote.setText(c.getString(2));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(c.getString(4))));
                if (c.getString(5) != null ){
                    elabel.setVisibility(View.VISIBLE);
                    elabel.setText(c.getString(5));
                }
                for (i=0;i<actionbarcolor.length;i++)
                    if (c.getString(4).equals(actionbarcolor[i]))
                        break;
                integer = i;
                lmain_layout.setBackground(getResources().getDrawable(imageids[i]));
                PATH_NUMBER_INTENT = c.getInt(10);
                if (PATH_NUMBER_INTENT == 1)
                {
                    CurrentPhotoPath = Path;
                    imageButton.setVisibility(View.VISIBLE);
                    Picasso.with(AddNote.this).load(new File(CurrentPhotoPath)).resize(100,108).into(imageButton);
                }else {
                    CurrentPhotoPath = null;
                }
                if (c.getString(7)!=null) {
                    listview_layout.setVisibility(View.VISIBLE);
                    listViewmain.setVisibility(View.VISIBLE);
                    imageButton.setVisibility(View.GONE);
                    enote.setVisibility(View.GONE);
                    String listtexts[] = c.getString(7).split("_,_");
                    String listvalues[] = c.getString(8).split("_,_");
                    listItemObjects = Utils.getlistobjects(listtexts,listvalues);
                    addlist();
                }
                Toast.makeText(getApplicationContext(),PATH_NUMBER_INTENT+"",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updatenotescursor(){
        String Title = etitle.getText().toString();
        String Body = enote.getText().toString();
        String LayoutColor = actionbarcolor[integer] ;
        String Label ;
        if (elabel.getVisibility() == View.VISIBLE )
            Label = elabel.getText().toString();
        else
            Label = null;
        String Date_created = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
        String List,Checked_List;
        if (listItemObjects.size() == 0){
            List = null;
            Checked_List=null;
        }
        else {
            String string= Utils.converttoarraylist(listItemObjects);
            String s[] = string.split(",-,-,");
            List = s[0];
            Checked_List = s[1];
        }
        try {
            if (CurrentPhotoPath == null) {
                if (PATH_NUMBER_INTENT == 2) {
                    CurrentPhotoPath = Utils.storedrawablebitmap(Utils.getBitmapFromView(listViewmain));
                } else {
                    enote.setCursorVisible(false);
                    enote.setBackground(new ColorDrawable(Color.WHITE));
                    CurrentPhotoPath = Utils.storedrawablebitmap(Utils.getBitmapFromView(enote));
                }
            }
        }catch (IOException e){}
        try {
            NotesAddDBHelper helper = new NotesAddDBHelper(this);
            if (helper.updateNote(TempPath,Title,Body,CurrentPhotoPath,LayoutColor,Label,Date_created,List,Checked_List,0,PATH_NUMBER_INTENT))
                Toast.makeText(this,PATH_NUMBER_INTENT+"",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"Not Updated",Toast.LENGTH_LONG).show();
        }catch (SQLException e){}
        if (PATH_NUMBER_INTENT != 1)
        Utils.deletebitmap(TempPath);
        Intent data = new Intent();
        data.putExtra(UPDATED_PATH_INTENT,CurrentPhotoPath);
        setResult(RESULT_OK,data);
        finish();
    }

}
