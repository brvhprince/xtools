package com.colorapps.xtools;

import android.content.Intent;
import android.os.Bundle;

import com.colorapps.xtools.utils.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolbar();
    }

    private void initToolbar() {
          Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String i_title = intent.getStringExtra("title");
        Integer i_image = intent.getIntExtra("image",-1);
        String i_desc = intent.getStringExtra("description");

        ImageView image = findViewById(R.id.image);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Spanned sp = Html.fromHtml(i_desc);

        Tools.displayImageOriginal(this, image, i_image);
        title.setText(i_title);
        description.setText(sp);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent share = new Intent(android.content.Intent.ACTION_SEND);
               share.setType("text/plain");
               String shareText = new StringBuilder().append(i_title).append(": ").append(i_desc).toString().replaceAll("<(.*?)\\>"," ");
               share.putExtra(android.content.Intent.EXTRA_SUBJECT,i_title);
               share.putExtra(android.content.Intent.EXTRA_TEXT,shareText);
               startActivity(Intent.createChooser(share,"Share Via"));
            }
        });



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}