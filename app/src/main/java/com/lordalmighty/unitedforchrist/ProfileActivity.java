package com.lordalmighty.unitedforchrist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout,home,Profilebtn,demo;
    private TextView UserTextView,welcome;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        home=(Button)findViewById(R.id.Homebtn);

        demo=(Button)findViewById(R.id.Demo);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();
        logout=(Button)findViewById(R.id.logoutButton);
        Profilebtn=(Button)findViewById(R.id.profilebtn);
        UserTextView=(TextView)findViewById(R.id.textViewlogin);
        welcome=(TextView)findViewById(R.id.textwelcome);
        welcome.setText("As "+user.getEmail());
        demo.setOnClickListener(this);
        logout.setOnClickListener(this);
        Profilebtn.setOnClickListener(this);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(v==demo){
            finish();
            startActivity(new Intent(this,ShowProfileActivity.class));
        }
        if(v==home){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        if(v==Profilebtn){
            finish();
            startActivity(new Intent(this,UserActivity.class));
        }
    }
}
