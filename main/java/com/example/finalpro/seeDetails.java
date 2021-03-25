package com.example.finalpro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

public class seeDetails extends Activity implements DialogInterface.OnClickListener
{
    private DBManager dbManager;
    Button Addtocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_details);
        /* Button */
        Addtocal=(Button)findViewById(R.id.BAddToCal);


        /* open database */
        dbManager = new DBManager(this);
        dbManager.open();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String jd = intent.getStringExtra("jd");
        String cn = intent.getStringExtra("cn");
        String date = intent.getStringExtra("date");
        String status = intent.getStringExtra("status");
        /* insert data to the TextView */
        TextView t;
        t=(TextView) findViewById(R.id.jd);
        t.setText(jd);
        t=(TextView) findViewById(R.id.cn);
        t.setText(cn);
        t=(TextView) findViewById(R.id.date);
        t.setText(date);
        t=(TextView) findViewById(R.id.status);
        t.setText(status);
        long _id = Long.parseLong(id);
        if(status.equals("schedule interview"))
            JI_details(_id);
        if(status.equals("Job offer"))
            JO_details(_id);


        Addtocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=(TextView) findViewById(R.id.TVwhen);
                String d=tv.getText().toString();
                String[] parts=d.split("/");
                tv=(TextView) findViewById(R.id.TVwith);
                String w=tv.getText().toString();
                tv=(TextView) findViewById(R.id.TVwhere);
                String l=tv.getText().toString();
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(Integer.parseInt(parts[2]),Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0]), 7, 30);
                Calendar endTime = Calendar.getInstance();
                endTime.set(Integer.parseInt(parts[2]),Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0]), 10, 30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Job Interview")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "At "+cn+", with "+w)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, l)
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);

            }

        });
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    private void JI_details(long id)
    {
        Cursor cursor =dbManager.GetDataById_JI(id);
        if(cursor.getCount()>0) {
            /* set visibility*/
            TableRow tr=(TableRow)findViewById(R.id.TRwhen);
            tr.setVisibility(View.VISIBLE);
            tr=(TableRow)findViewById(R.id.TRwhere);
            tr.setVisibility(View.VISIBLE);
            tr=(TableRow)findViewById(R.id.TRwith);
            tr.setVisibility(View.VISIBLE);
            /* show button */
            Addtocal.setVisibility(View.VISIBLE);
                /* insert data to textviews */
           TextView t=(TextView) findViewById(R.id.TVwhen);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WhenT)));
             t=(TextView) findViewById(R.id.TVwhere);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Where)));
             t=(TextView) findViewById(R.id.TVwith);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WithWho)));

        }
    }
    private void JO_details(long id)
    {
        Cursor cursor =dbManager.GetDataById_JO(id);
        if(cursor.getCount()>0) {
            /* set visibility*/
            TableRow tr=(TableRow)findViewById(R.id.TRHours);
            tr.setVisibility(View.VISIBLE);
            tr=(TableRow)findViewById(R.id.TRDays);
            tr.setVisibility(View.VISIBLE);
            tr=(TableRow)findViewById(R.id.TRJT);
            tr.setVisibility(View.VISIBLE);
            tr=(TableRow)findViewById(R.id.TRPC);
            tr.setVisibility(View.VISIBLE);
            tr=(TableRow)findViewById(R.id.TRB);
            tr.setVisibility(View.VISIBLE);
            /* insert data to textviews */
            TextView t=(TextView) findViewById(R.id.TVHours);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Hours)));
           t=(TextView) findViewById(R.id.TVDays);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Days)));
            t=(TextView) findViewById(R.id.TVJT);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Position)));
            t=(TextView) findViewById(R.id.TVPC);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PayCheck)));
            t=(TextView) findViewById(R.id.TVB);
            t.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Bonus)));
        }
    }
}
