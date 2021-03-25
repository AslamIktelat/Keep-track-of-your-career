package com.example.finalpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AddApp extends AppCompatActivity {
    TableRow TRDate,TRPlace,TRWith,TRMD, TRHW1,TRJT,TRB,TRBS,TRBSI,TRBSJO,TRPC;
    Button Bsave,BSI,BSJO;
    EditText ETJD,ETCN,ETIP ,ETWith,ETHours, ETDays, ETTitle, ETBonus,ETPC;
    DatePicker datePicker1,datePicker2;
    private DBManager dbManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addapp);

 try {

                /* open the database */
     dbManager = new DBManager(this);
     dbManager.open();
                /* DatePicker */
     datePicker1=(DatePicker)findViewById(R.id.datePicker1);
     datePicker2=(DatePicker)findViewById(R.id.datePicker2);
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
            /* Buttons */
       Bsave=(Button)findViewById(R.id.Bsave);/* Button save */
       BSI=(Button)findViewById(R.id.BSI); /* Button save interview */
     BSJO=(Button)findViewById(R.id.BSJO); /* Button save job offer */
     BSJO.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             CharSequence text;
             Context context = getApplicationContext();
             int duration = Toast.LENGTH_SHORT;
             if(!TextUtils.isEmpty(ETHours.getText().toString()) && (Integer.parseInt(ETHours.getText().toString())<=0 || Integer.parseInt(ETHours.getText().toString()) >24))
             {
                 text = "Hours cant be zero or greater than 24";
                 Toast toast = Toast.makeText(context, text, duration);
                 toast.show();
             }
             else
             {
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
     Bsave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             CharSequence text;
             Context context = getApplicationContext();
             int duration = Toast.LENGTH_SHORT;
             if(SaveData1("Waiting for answer")==-1) {
                 text = "One or more of the fields is empty!";
                 Toast toast = Toast.makeText(context, text, duration);
                 toast.show();
             }
             else {
                 text = "Data is saved";
                 Toast toast = Toast.makeText(context, text, duration);
                 toast.show();
                 finish();

             }


         }
     });
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

     /* *For each option at the Spinner I hide/show elements at the addapp.xml* */

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Waiting for answer", "Job interview ", "Job offer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 2)/* job offer */ {
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
                if (position == 1)/* job interview */ {
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
                if (position == 0)/* Waiting for answer */ {
                      TRBS.setVisibility(View.VISIBLE);
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
    }
        catch(Exception ex)
        {
            Log.d("ex",""+ex.toString());
            TextView ETJB=(TextView)findViewById(R.id.ETJD);
            ETJD.setText(""+ex.toString());
        }
    }

    private long SaveData1(String s)/* I call this function to save in the database app' job in "Waiting for answer " status */
    {
        if(TextUtils.isEmpty(ETJD.getText().toString()) || TextUtils.isEmpty(ETCN.getText().toString()))
        {
            return -1;
        }
        String d="";
        int m=1+datePicker1.getMonth();
        d=d+datePicker1.getDayOfMonth()+"/"+m+"/"+datePicker1.getYear();
        return dbManager.insertToT1(ETJD.getText().toString(),ETCN.getText().toString(),d,s);/* call insertToT1 from dbManager*/
    }

    private long SaveData2()/* I call this function to save in the database app' job in "schedule interview " status */
    {
        long id=SaveData1("schedule interview");
        if(id==-1) return -1;
        if(TextUtils.isEmpty(ETIP.getText().toString()) || TextUtils.isEmpty(ETWith.getText().toString()))
        {
            return -1;
        }
        String d="";
        int m=1+datePicker2.getMonth();
        d=d+datePicker2.getDayOfMonth()+"/"+m+"/"+datePicker2.getYear();
        return dbManager.insertToT2(id,d,ETIP.getText().toString(),ETWith.getText().toString());/* call insertToT2 dbManager*/
    }
    private long SaveData3()/* I call this function to save in the database app' job in "Job offer" status */
    {
        long id=SaveData1("Job offer");
        if(id==-1) return -1;
        if(TextUtils.isEmpty(ETHours.getText().toString()) || TextUtils.isEmpty(ETDays.getText().toString()) ||
                TextUtils.isEmpty(ETTitle.getText().toString()) || TextUtils.isEmpty(ETBonus.getText().toString()) ||TextUtils.isEmpty(ETPC.getText().toString()))
        {
            return -1;
        }
        /* call insertToT3 dbManager*/
        return dbManager.insertToT3(id,ETTitle.getText().toString(),ETDays.getText().toString(),ETHours.getText().toString(),ETPC.getText().toString(),ETBonus.getText().toString());
    }
}
