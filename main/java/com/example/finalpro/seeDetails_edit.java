package com.example.finalpro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class seeDetails_edit extends Activity implements DialogInterface.OnClickListener
{
    private DBManager dbManager;
    private long _id;
    TableRow TRDate,TRPlace,TRWith,TRMD, TRHW1,TRJT,TRB,TRBS,TRBSI,TRBSJO,TRPC;
    Button Bsave,BSI,BSJO;
    EditText ETJD,ETCN,ETIP ,ETWith,ETHours, ETDays, ETTitle, ETBonus,ETPC;
    DatePicker datePicker1,datePicker2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addapp);
        /* EditText */
        ETJD=(EditText)findViewById(R.id.ETJD);//job description
        ETCN=(EditText)findViewById(R.id.ETCN);//Company name
        ETIP =(EditText)findViewById(R.id.ETIP);//place of the interview
        ETWith=(EditText)findViewById(R.id.ETWith);//with who
        ETHours =(EditText)findViewById(R.id.ETHours);//Hours
        ETDays =(EditText)findViewById(R.id.ETDays);//Days
        ETTitle =(EditText)findViewById(R.id.ETTitle);//job title
        ETPC=(EditText)findViewById(R.id.ETPC);//paycheck
        ETBonus=(EditText)findViewById(R.id.ETBonus); //bonus

        /* open database */
        dbManager = new DBManager(this);
        dbManager.open();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        _id = Long.parseLong(id);
        String jd = intent.getStringExtra("jd");
        ETJD.setText(jd);
        ETJD.setEnabled(false);
        String cn = intent.getStringExtra("cn");
        ETCN.setText(cn);
        ETCN.setEnabled(false);
        String date = intent.getStringExtra("date");
        String status = intent.getStringExtra("status");
        String[] parts=date.split("/");
        datePicker2=(DatePicker)findViewById(R.id.datePicker2);
        datePicker1=(DatePicker)findViewById(R.id.datePicker1);
        datePicker1.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        datePicker1.updateDate(Integer.parseInt(parts[2]),Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0]));

        datePicker1.init(Integer.parseInt(parts[2]),Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0]), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePicker.updateDate(Integer.parseInt(parts[2]),Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0]));

            }
        });

        /* Buttons */
        Bsave=(Button)findViewById(R.id.Bsave);/* Button save */
        BSI=(Button)findViewById(R.id.BSI); /* Button save interview */
        BSJO=(Button)findViewById(R.id.BSJO); /* Button save job offer */
        /* All the TableRows we need to hide  */
        TRBS=(TableRow)findViewById(R.id.TRBS);
        TRBS.setVisibility(View.GONE);
        TRBSJO=(TableRow)findViewById(R.id.TRBSJO);
        TRBSJO.setVisibility(View.GONE);
        TRBSI=(TableRow)findViewById(R.id.TRBSI);
        TRBSI.setVisibility(View.GONE);
        TRDate=(TableRow)findViewById(R.id.TRDate);
        TRDate.setVisibility(View.GONE);
        TRPlace = (TableRow) findViewById(R.id.TRPlace);
        TRPlace.setVisibility(View.GONE);
        TRWith = (TableRow) findViewById(R.id.TRWith);
        TRWith.setVisibility(View.GONE);
        TRMD = (TableRow) findViewById(R.id.TRMD);
        TRMD.setVisibility(View.GONE);
        TRHW1 = (TableRow) findViewById(R.id.TRHW);
        TRHW1.setVisibility(View.GONE);
        TRJT = (TableRow) findViewById(R.id.TRJT);
        TRJT.setVisibility(View.GONE);
        TRB = (TableRow) findViewById(R.id.TRB);
        TRB.setVisibility(View.GONE);
        TRPC= (TableRow) findViewById(R.id.TRPC);
        TRPC.setVisibility(View.GONE);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items =new String[]{"empty"};
        if(status.equals("Waiting for answer"))
        {
            items = new String[]{ "Job interview", "Job offer","Delete","Rejected"};
        }
        if(status.equals("schedule interview"))
        {
            items = new String[]{"Job offer", "Delete","Rejected"};
        }
        if(status.equals("Job offer") || status.equals("Rejected"))
        {
            items = new String[]{"Delete"};
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               String sItem= dropdown.getSelectedItem().toString();
               if(sItem.equals("Job offer"))/* job offer */ {
                    TRDate.setVisibility(View.GONE);
                    TRPlace.setVisibility(View.GONE);
                    TRWith.setVisibility(View.GONE);
                    TRBS.setVisibility(View.GONE);
                    TRBSI.setVisibility(View.GONE);
                    TRPC.setVisibility(View.VISIBLE);
                    TRMD.setVisibility(View.VISIBLE);
                    TRHW1.setVisibility(View.VISIBLE);
                    TRJT.setVisibility(View.VISIBLE);
                    TRB.setVisibility(View.VISIBLE);
                    TRBSJO.setVisibility(View.VISIBLE);
                }
                if(sItem.equals("Job interview"))/* job interview */ {
                    TRMD.setVisibility(View.GONE);
                    TRHW1.setVisibility(View.GONE);
                    TRJT.setVisibility(View.GONE);
                    TRB.setVisibility(View.GONE);
                    TRBS.setVisibility(View.GONE);
                    TRBSJO.setVisibility(View.GONE);
                    TRDate.setVisibility(View.VISIBLE);
                    TRPlace.setVisibility(View.VISIBLE);
                    TRWith.setVisibility(View.VISIBLE);
                    TRBSI.setVisibility(View.VISIBLE);
                    TRPC.setVisibility(View.GONE);
                }
                if(sItem.equals("Rejected"))/*  */ {
                    TRBS.setVisibility(View.VISIBLE);
                    Bsave.setText("Save");
                    TRMD.setVisibility(View.GONE);
                    TRHW1.setVisibility(View.GONE);
                    TRJT.setVisibility(View.GONE);
                    TRB.setVisibility(View.GONE);
                    TRBSJO.setVisibility(View.GONE);
                    TRDate.setVisibility(View.GONE);
                    TRPlace.setVisibility(View.GONE);
                    TRWith.setVisibility(View.GONE);
                    TRBSI.setVisibility(View.GONE);
                    TRPC.setVisibility(View.GONE);

                }
                if(sItem.equals("Delete")) {
                    TRBS.setVisibility(View.VISIBLE);
                    Bsave.setText("Delete");

                    TRMD.setVisibility(View.GONE);
                    TRHW1.setVisibility(View.GONE);
                    TRJT.setVisibility(View.GONE);
                    TRB.setVisibility(View.GONE);
                    TRBSJO.setVisibility(View.GONE);
                    TRDate.setVisibility(View.GONE);
                    TRPlace.setVisibility(View.GONE);
                    TRWith.setVisibility(View.GONE);
                    TRBSI.setVisibility(View.GONE);
                    TRPC.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        Bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text="";
                if(Bsave.getText().equals("Delete"))
                {
                    text = "Job application is deleted successfully";
                    //call delete from datebase
                    dbManager.delete(_id);
                }
                else/* save "Rejected" */
                {
                    if(dbManager.update(_id,"Rejected")==0)
                        text="something went wrong....sorry :(";
                    else
                        text="status is successfully saved";
                }

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();

            }
        });

        BSJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text;
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                if(SaveData3()==-1) {
                    text = "One or more of the fields is empty!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    text = "Job offer is saved";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });
        BSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text;
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                if(SaveData2()==-1) {
                    text = "One or more of the fields is empty!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    text = "Interview is saved";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });
    }
    private long SaveData2()
    {
        if(TextUtils.isEmpty(ETIP.getText().toString()) || TextUtils.isEmpty(ETWith.getText().toString()))
        {
            return -1;
        }
        int dbup=dbManager.update(_id,"schedule interview");
        if(dbup==0)
            return -1;
        String d="";
        int m=1+datePicker2.getMonth();
        d=d+datePicker2.getDayOfMonth()+"/"+m+"/"+datePicker2.getYear();
        return dbManager.insertToT2(_id,d,ETIP.getText().toString(),ETWith.getText().toString());
    }
    private long SaveData3()
    {
        if(TextUtils.isEmpty(ETHours.getText().toString()) || TextUtils.isEmpty(ETDays.getText().toString()) ||
                TextUtils.isEmpty(ETTitle.getText().toString()) || TextUtils.isEmpty(ETBonus.getText().toString()) ||TextUtils.isEmpty(ETPC.getText().toString()))
        {
            return -1;
        }
        int dbup=dbManager.update(_id,"Job offer");
        if(dbup==0)
            return -1;
        return dbManager.insertToT3(_id,ETTitle.getText().toString(),ETDays.getText().toString(),ETHours.getText().toString(),ETPC.getText().toString(),ETBonus.getText().toString());
    }



























    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
