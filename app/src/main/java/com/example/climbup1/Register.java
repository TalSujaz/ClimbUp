package com.example.climbup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.climbup1.Model.userProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText userName,userPassword,userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    String email,name,password,filed;
    ProgressDialog pd;
    private Spinner spinner;
    private static final String[] paths = {"All","Boulder", "Speed", "Rock"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();
        userName=(EditText)findViewById(R.id.regname);
        userPassword=(EditText)findViewById(R.id.passwordreg);
        userEmail=(EditText)findViewById(R.id.emailreg);
        regButton=(Button) findViewById(R.id.regbtn);
        userLogin=(TextView) findViewById(R.id.regexist);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(Register.this);
                pd.setMessage("Please wait...");
                pd.show();
                if(validate())
                {
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Register.this,navActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Fail..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
    private void sendUserData(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String userid=firebaseUser.getUid();

        reference=FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        HashMap<String, Object>hashMap= new HashMap<>();
        hashMap.put("id",userid);
        hashMap.put("email",email);
        hashMap.put("name",name);
        hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/theta-cider-207610.appspot.com/o/profile.png?alt=media&token=6eb49edc-d046-4796-ada6-e226793a09dd");
        hashMap.put("bio","");
        hashMap.put("filed",filed);
        reference.setValue(hashMap);
    }
    private boolean validate(){
        Boolean result = false;
        name=userName.getText().toString();
        password=userPassword.getText().toString();
        email=userEmail.getText().toString();

        if (name.isEmpty()|| password.isEmpty() ||
                email.isEmpty()||filed.isEmpty())
        {
            Toast.makeText(getApplicationContext(),
                    "please complte all fileds",
                    Toast.LENGTH_LONG).show();
        }else if(password.length()<6){
            Toast.makeText(getApplicationContext(),
                    "Password must have 6 characters",
                    Toast.LENGTH_LONG).show();
        } else {
            result=true;
        }
        return result;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                filed=paths[0];
                break;
            case 1:
                filed=paths[1];
                break;
            case 2:
                filed=paths[2];
                break;
            case 3:
                filed=paths[3];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    }
