package com.example.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private List<Portal> mPortals;

    private PortalAdapter mPortal;

    private RecyclerView mRecyclerView;

    GestureDetector mGestureDetector;

    public static final String EXTRA_REMINDER = "Portal";

    public static final String WEBPAGE = "Webpage";

    public static final int REQUESTCODE = 1234;
    private int mModifyPosition;

    private Portal newPortal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(MainActivity.this, WebsiteActivity.class));
                startActivityForResult(new Intent(MainActivity.this, UpdateActivity.class), REQUESTCODE);


            }
        });

        mRecyclerView = findViewById(R.id.RecyclerView);

        mPortals = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mPortal = new PortalAdapter(mPortals);

        mRecyclerView.setAdapter(mPortal);

        newPortal = (Portal) getIntent().getParcelableExtra(UpdateActivity.EXTRA_PORTAL);

        mPortals.add(new Portal("Google","https://www.google.com/"));

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override

            public boolean onSingleTapUp(MotionEvent e) {

                return true;

            }

        });

        mRecyclerView.addOnItemTouchListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUESTCODE) {

            if (resultCode == RESULT_OK) {

                Portal updatedPortal = data.getParcelableExtra(MainActivity.EXTRA_REMINDER);

                // New timestamp: timestamp of update

                mPortals.add(updatedPortal);

                updateUI();

            }

        }

    }
    private void updateUI() {

        if (mPortal == null) {

            mPortal = new PortalAdapter(mPortals);

            mRecyclerView.setAdapter(mPortal);

        } else {

            mPortal.notifyDataSetChanged();

        }

    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        int mAdapterPosition = recyclerView.getChildAdapterPosition(child);

        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

            System.out.println("Clicked");

            Portal clickedPortal = mPortals.get(mAdapterPosition);

            System.out.println(clickedPortal.getUrl());

            //Click


            Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
            intent.putExtra(WEBPAGE, clickedPortal);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
