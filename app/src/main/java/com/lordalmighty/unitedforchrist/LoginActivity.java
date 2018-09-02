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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="LoginActivity";
    private Button loginbtn,Demo;
    private EditText editEmail,editPassword;
    private TextView textViewlogin;
    private ProgressDialog ProgressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Demo=(Button)findViewById(R.id.demo);
        Demo.setOnClickListener(this);
        loginbtn=(Button)findViewById(R.id.loginButton);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        ProgressDialog=new ProgressDialog(this);
        editEmail=(EditText)findViewById(R.id.TextEmail);
        editPassword=(EditText)findViewById(R.id.TextPassword);
        textViewlogin=(TextView)findViewById(R.id.textViewlogin);
        loginbtn.setOnClickListener(this);

        textViewlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==loginbtn){
            Userlogin();
        }
        if(v==textViewlogin){
            finish();
            startActivity(new Intent(this,SignUpActivity.class));
        }

    }

    private void Userlogin() {
        String Email=editEmail.getText().toString().trim();
        String Password=editPassword.getText().toString().trim();
        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Please Enter the Email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Please Enter the Password",Toast.LENGTH_SHORT).show();
            return ;
        }
        else if(Password.length()<8){
            Toast.makeText(this,"Password must be minimum of 8 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog.setMessage("Logging In...");
        ProgressDialog.setCanceledOnTouchOutside(false);
        ProgressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ProgressDialog.hide();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else{
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "User Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }
}
