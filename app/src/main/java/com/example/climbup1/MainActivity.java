package com.example.climbup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText email,password;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int counter=5;
    TextView register,forgottv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText)findViewById(R.id.logemail);
        password=(EditText)findViewById(R.id.logpassword);
        Login=(Button)findViewById(R.id.btnLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(email.getText().toString(),password.getText().toString());
            }
        });

        register=(TextView)findViewById(R.id.newuser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

        forgottv=(TextView)findViewById(R.id.forget) ;
        forgottv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Forgot.class));
            }
        });
    }
    private void validate(String userName ,String userPassword) {
        progressDialog.setMessage("checking data");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(userName,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(firebaseAuth.getCurrentUser().getUid());
                            startActivity(new Intent(MainActivity.this,navActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            counter--;
                            progressDialog.dismiss();
                            if (counter==0){
                                Login.setEnabled(false);
                            }
                        }
                    }
                });

    }
}
