package com.lordalmighty.unitedforchrist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private Button Send;
    private EditText req;
    private DatabaseReference myRef;
    private String id;
    private long num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Send=findViewById(R.id.snd);
        req=findViewById(R.id.desc);
        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        myRef= FirebaseDatabase.getInstance().getReference().child("Requests").push().getRef();
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = req.getText().toString();
                myRef.child("desc").setValue(txt);
                myRef.child("Pray_count").setValue(0);
                startActivity(new Intent(PostActivity.this,MainActivity.class));
            }
        });
    }
}
