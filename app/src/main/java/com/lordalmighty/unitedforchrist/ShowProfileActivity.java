package com.lordalmighty.unitedforchrist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowProfileActivity extends AppCompatActivity {
        private DatabaseReference mdatabase;
        private FirebaseAuth firebaseAuth;
        TextView Gotmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        Gotmobile=(TextView)findViewById(R.id.gotmobile);
        FirebaseUser user= firebaseAuth.getCurrentUser();
        mdatabase= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Phone");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String phone=dataSnapshot.getValue().toString();
                Gotmobile.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
