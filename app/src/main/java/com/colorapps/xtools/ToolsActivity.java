package com.colorapps.xtools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.colorapps.xtools.adapter.GridAdapter;
import com.colorapps.xtools.model.AllTools;
import com.colorapps.xtools.utils.Tools;
import com.colorapps.xtools.widget.SpaceItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToolsActivity extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();


    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Engineering Tools");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpaceItems(2, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);

        List<AllTools> items = new ArrayList<>();

        TypedArray toolsMenu = getResources().obtainTypedArray(R.array.xtools);

        TypedArray itemDetails;

        for (int i = 0; i < toolsMenu.length(); i++){
            int resId = toolsMenu.getResourceId(i, -1);
            if (resId < 0) {
                continue;
            }
            itemDetails = getResources().obtainTypedArray(resId);
            int ind = 2;
            int des = 1;
            Spanned sp = Html.fromHtml(itemDetails.getString(des));
            items.add(new AllTools(itemDetails.getResourceId(ind, -1), itemDetails.getString(0), itemDetails.getString(des)));
        }

        mAdapter = new GridAdapter(this,items);
        recyclerView.setAdapter(mAdapter);


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.team) {
            startActivity(new Intent(this, MembersActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

