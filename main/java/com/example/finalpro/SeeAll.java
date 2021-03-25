package com.example.finalpro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class SeeAll extends AppCompatActivity {
    TableLayout tableLayout;
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private SearchView searchView;
    private Cursor cursor;
    final String[] from = new String[] { DatabaseHelper._ID,DatabaseHelper.Description,
            DatabaseHelper.Company, DatabaseHelper.SendDate,DatabaseHelper.Status };

    final int[] to = new int[] { R.id.id,R.id.jd, R.id.cn, R.id.date ,R.id.status };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeall);

        /* open database */
        dbManager = new DBManager(this);
        dbManager.open();
         cursor = dbManager.fetch();
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
      //  listView.setTextFilterEnabled(true);
        // OnCLickListiner For List Items go to seeDetails.class
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                TextView TVjd = (TextView) view.findViewById(R.id.jd);
                TextView TVcn = (TextView) view.findViewById(R.id.cn);
                TextView TVdate = (TextView) view.findViewById(R.id.date);
                TextView TVstatus = (TextView) view.findViewById(R.id.status);
                TextView TVid = (TextView) view.findViewById(R.id.id);
                Intent modify_intent = new Intent(getApplicationContext(), seeDetails.class);
                modify_intent.putExtra("jd", TVjd.getText().toString());
                modify_intent.putExtra("cn", TVcn.getText().toString());
                modify_intent.putExtra("date", TVdate.getText().toString());
                modify_intent.putExtra("status", TVstatus.getText().toString());
                modify_intent.putExtra("id", TVid.getText().toString());
                startActivity(modify_intent);
            }
        });

        /* search in the database */
        searchView=(SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    return true;
                }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursor = dbManager.fetchByCompanyN(newText);
                if(cursor.getCount()>0) {
                    adapter = new SimpleCursorAdapter(SeeAll.this, R.layout.activity_view_record, cursor, from, to, 0);
                    listView.setAdapter(adapter);
                }
                else
                {
                    cursor = dbManager.fetch();
                    adapter = new SimpleCursorAdapter(SeeAll.this, R.layout.activity_view_record, cursor, from, to, 0);
                    listView.setAdapter(adapter);
                }
               return true;
            }
        });


    }
}
