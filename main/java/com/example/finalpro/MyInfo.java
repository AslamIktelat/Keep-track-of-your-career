package com.example.finalpro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyInfo  extends AppCompatActivity {
    ImageButton imageButton,imageButtonWeb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo);
        imageButton=(ImageButton) findViewById(R.id.imageButtonsend);
        imageButtonWeb=(ImageButton) findViewById(R.id.imageButtonsend1);
        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aslamiktelat1.wixsite.com/website/andapp"));
                startActivity(browserIntent);
            }

        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"aslamiktelat1@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback");
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                   Context context = getApplicationContext();
                   int duration = Toast.LENGTH_SHORT;
                    CharSequence  text = "There are no email client installed on your device.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }

        });
    }
}
