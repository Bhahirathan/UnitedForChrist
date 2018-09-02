package com.lordalmighty.unitedforchrist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;



public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG="SignUpActivity";
    private Button RegisterButton;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    String email,password;
    boolean check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        progressDialog=new ProgressDialog(this);
        RegisterButton=(Button)findViewById(R.id.RegisterButton);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        textViewSignin=(TextView) findViewById(R.id.textViewSignin);
        RegisterButton.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void RegisterUser(){

        email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter the Email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter the Password",Toast.LENGTH_SHORT).show();
            return ;
        }
        else if(password.length()<8){
            Toast.makeText(this,"Password must be minimum of 8 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


            sendEmailVerification();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.hide();

                            if (task.isSuccessful()) {


                                finish();
                                Toast.makeText(SignUpActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserActivity.class));

                            } else {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(SignUpActivity.this, "User Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }



    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // email
                                Toast.makeText(SignUpActivity.this,"Check Your E-mail For Verification",Toast.LENGTH_SHORT).show();
                                // after email is sent just logout the user and finish this activity
                                FirebaseAuth.getInstance().signOut();
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view){
        if(view==RegisterButton){
            RegisterUser();
        }
        if(view==textViewSignin){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }


}
