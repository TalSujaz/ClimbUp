package com.example.climbup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.climbup1.Fragment.HomeFragment;
import com.example.climbup1.Fragment.NotificationFragment;
import com.example.climbup1.Fragment.ProfileFragment;
import com.example.climbup1.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class navActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

      bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

      Bundle intent=getIntent().getExtras();
      if(intent!=null){
          String publisher= intent.getString("publisherid");

          SharedPreferences.Editor editor=getSharedPreferences("PRFS",MODE_PRIVATE).edit();
          editor.putString("profileid",publisher);
          editor.apply();

          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_continer,new ProfileFragment()).commit();
      }else {
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_continer,new HomeFragment()).commit();
      }

    }
  private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
          new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  switch (item.getItemId()){
                      case R.id.nav_home:
                          selectedFragment=new HomeFragment();
                          break;
                      case R.id.nav_search:
                          selectedFragment=new SearchFragment();
                          break;
                      case R.id.nav_add:
                          selectedFragment=null;
                          startActivity(new Intent(navActivity.this,Addpic.class));
                          break;
                      case R.id.nav_notifi:
                          selectedFragment=new NotificationFragment();
                          break;
                      case R.id.nav_profile:
                          SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                          editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                          editor.apply();
                          selectedFragment=new ProfileFragment();
                          break;
                  }
                  if(selectedFragment!=null){
                      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_continer,selectedFragment).commit();
                  }
                  return true;
              }
          };
}
