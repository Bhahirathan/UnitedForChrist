package com.lordalmighty.unitedforchrist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lordalmighty.unitedforchrist.activities.Bible;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tv,coun,coun_btn;
    private int flag;
    private String no;
    String keys;
    private long num,count;
    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Requests, MainActivity.NewsViewHolder> mPeopleRVAdapter;
    private LinearLayoutManager mLayoutManager;
    private static FirebaseDatabase Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv = findViewById(R.id.tv);

        coun = findViewById(R.id.count);
        update();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     no=String.valueOf(num);

                Intent intent=new Intent(MainActivity.this,PostActivity.class);
                intent.putExtra("id", no);
                startActivity(intent);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPeopleRV = findViewById(R.id.myRecyclerView);
        final DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        final Query personsQuery = personsRef.orderByKey();
        mPeopleRV.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mPeopleRV.setLayoutManager(mLayoutManager);

        final FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Requests>().setQuery(personsQuery, Requests.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Requests, NewsViewHolder>(personsOptions)
        {
            @Override
            protected void onBindViewHolder(final MainActivity.NewsViewHolder holder, final int position, final Requests model)
            {
                holder.setTitle(model.getName());
                holder.setDesc(model.getDesc());
                holder.coun_.setText(String.valueOf(model.getPray_count()));
                holder.coun_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s=getRef(position).getKey();
                        holder.coun_.setText(String.valueOf(model.getPray_count()));
                    }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            @Override
            public MainActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_row, parent, false);
                return new MainActivity.NewsViewHolder(view);
            }
        };
        mPeopleRV.setAdapter(mPeopleRVAdapter);
        personsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num = dataSnapshot.getChildrenCount();
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView coun_btn,coun_;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            coun_ = mView.findViewById(R.id.count);
            coun_btn = mView.findViewById(R.id.prayed);
        }

        public void setTitle(String title) {
            TextView post_title = mView.findViewById(R.id.name);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = mView.findViewById(R.id.desc);
            post_desc.setText(desc);
        }
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

public void update(){
    String sun="Sunday";
    String mon="Monday";
    String tue="Tuesday";
    String w="Wednesday";
    String thu="Thursday";
    String f="Friday";
    String sat="Saturday";
    String su="Prayer For Self";
    String mo="Prayer for Waiting";
    String tu="Prayer For Salvation";
    String wed="Prayer For Sick And Needy";
    String th="Prayer For Church And Ministries";
    String fr="Prayer For Our Own Place And Nation";
    String sa="Prayer For Students";
    String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());

    if(sun.equals(weekday_name))
        tv.setText(su);
    if(mon.equals(weekday_name))
        tv.setText(mo);
    if(tue.equals(weekday_name))
        tv.setText(tu);
    if(w.equals(weekday_name))
        tv.setText(wed);
    if(thu.equals(weekday_name))
        tv.setText(th);
    if(f.equals(weekday_name))
        tv.setText(fr);
    if(sat.equals(weekday_name))
        tv.setText(sa);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

      if (id == R.id.nav_home) {
            startActivity(new Intent(this,MainActivity.class));
        } else if (id==R.id.Bible) {
          startActivity(new Intent(this,Bible.class));
      }else if (id == R.id.nav_prayer) {

        } else if (id == R.id.login_or_sign) {
          Intent signup=new Intent(MainActivity.this,LoginActivity.class);
          startActivity(signup);
        } else if (id == R.id.nav_share) {
          Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
          sharingIntent.setType("text/plain");
          String shareBody = "Link will be Updated Soon";
          String shareSub = "United For Christ";
          sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
          sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
          startActivity(Intent.createChooser(sharingIntent, "Share using"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
