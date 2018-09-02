package com.lordalmighty.unitedforchrist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference databaseReference;
    private Button Savebtn;
    private EditText name,phno;
    private FirebaseAuth firebaseAuth;
    String Name,Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Savebtn=(Button)findViewById(R.id.savebtn);
        name=(EditText)findViewById(R.id.name);

        phno=(EditText)findViewById(R.id.editTextphone);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        Savebtn.setOnClickListener(this);

    }



    private void Userinfo(){
        Name=name.getText().toString().trim();
        Phone=phno.getText().toString().trim();
        Userinfo userinfo=new Userinfo(Name,Phone);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinfo);
        Toast.makeText(this,"Information Saved!!!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onClick(View v) {

        if(v==Savebtn){
            Userinfo();
        }
    }
}
