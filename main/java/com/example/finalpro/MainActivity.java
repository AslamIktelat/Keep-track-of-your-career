package com.example.finalpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton IBAdd,IBEdit,IBSearch;
    TextView TVAdd,TVEdit,TVSearch;
    private DBManager dbManager;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ImageView*/
        imageView=(ImageView) findViewById(R.id.imageView4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyInfo.class);
                startActivityForResult(intent, 2);
            }

        });
        /* ImageButtons */
        IBAdd=(ImageButton) findViewById(R.id.IBAdd);
        IBEdit=(ImageButton)findViewById(R.id.IBEdit);
        IBSearch=(ImageButton)findViewById(R.id.IBSearch);

        /* TextViews */
        TVAdd=(TextView) findViewById(R.id.TVAdd);
        TVEdit=(TextView) findViewById(R.id.TVEdit);
        TVSearch=(TextView) findViewById(R.id.TVSearch);


        /* connect the buttons to the right activitys */
        /*IB=ImageButton*/
        IBEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Edit.class);
                startActivityForResult(intent, 2);
            }

        });

        IBAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddApp.class);
                startActivityForResult(intent, 2);
            }

        });

        IBSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SeeAll.class);
                startActivity(intent);
            }

        });
        countAndshow();



    }
    /* I use this to refresh the main activity every time the user get back from another activitys */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        countAndshow();
    }
    private void countAndshow()
    {
        /* go to the database and count how many "Waiting for answer" ,"schedule interview" ,"Job offer" and print the counters*/
        TVAdd.setText("");
        TVEdit.setText("");
        TVSearch.setText("");

        try {
            dbManager = new DBManager(this);
            dbManager.open();
            Cursor cursor = dbManager.fetch();
            int w=0,i=0,j=0,c=0;
            for(int ii=0;ii<cursor.getCount();ii++)
            {
                // Get version from Cursor
                String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Status));
                if (status.equals( "Waiting for answer")) w++;
                if (status.equals("schedule interview")) i++;
                if (status.equals( "Job offer")) j++;
                c++;
                cursor.moveToNext();
            }

            if(c==0) {
                TVAdd.setText("You have not sent your cv to any company yet");
                TVEdit.setText("");
                TVSearch.setText("");

            }
            else {
                TVAdd.setText("You have sent your cv to " + c + " companys");
                if(w>0)
                    TVEdit.setText("You have "+w+" application in waiting status\n");
                if(i>0)
                    TVEdit.setText(TVEdit.getText()+"You have "+i+" job interviews\n");
                if(j>0) {
                    TVEdit.setText(TVEdit.getText()+"You have " + j + " job offers");
                    double p=(double) j/(double)c;
                    int pp=(int) (p*100);
                    TVSearch.setText("You have received job offers at "+pp+"% of your job applications");
                }
            }

        }
        catch (SQLException sqlException)
        {
            TVEdit.setText("ERROR:"+sqlException.getMessage());
        }
        catch (Exception ex)
        {
            TVEdit.setText("ERROR:"+ex.getMessage()+" "+ex.toString());
        }
    }
}