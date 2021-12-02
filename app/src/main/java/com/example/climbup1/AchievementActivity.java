package com.example.climbup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.climbup1.Adapter.AchievementAdapter;
import com.example.climbup1.Adapter.CommentsAdapter;
import com.example.climbup1.Model.Achievement;
import com.example.climbup1.Model.Comment;
import com.example.climbup1.Model.Misson;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AchievementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AchievementAdapter achievementAdapter;
    private List<Misson> missonList;

    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Achievement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        missonList=new ArrayList<>();

        achievementAdapter= new AchievementAdapter(this,missonList);
        recyclerView.setAdapter(achievementAdapter);

        getMissons();
    }
   private void getMissons(){
       DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Missons");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               missonList.clear();
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Misson misson=snapshot.getValue(Misson.class);
                   missonList.add(misson);
                   achievementAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }
   }

