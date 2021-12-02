package com.example.climbup1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {
    EditText misson;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        misson=findViewById(R.id.misson);
        send=findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String missontxt;
                missontxt=misson.getText().toString().trim();
                if(!missontxt.equals("")){
                    sendmisson(missontxt);
                }
            }
        });
    }
    private void sendmisson(String misson){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Missons");

        String missonid=reference.push().getKey();

        HashMap<String, Object> hashMap= new HashMap<>();
        hashMap.put("missonid", missonid);
        hashMap.put("misson", misson);

        reference.child(missonid).setValue(hashMap);
        startActivity(new Intent(AdminActivity.this,AdminActivity.class));
    }
}