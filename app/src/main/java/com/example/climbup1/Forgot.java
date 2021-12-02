package com.example.climbup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        email=(EditText)findViewById(R.id.fremail);
        reset=(Button)findViewById(R.id.frreset);
        firebaseAuth=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                if(useremail.equals("")){
                    Toast.makeText(getApplicationContext(),"enter an email",Toast.LENGTH_LONG).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "sending an email for a reset", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(Forgot.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"error on sending the email",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
