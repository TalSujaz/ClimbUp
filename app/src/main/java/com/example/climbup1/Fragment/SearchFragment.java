package com.example.climbup1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.climbup1.Adapter.UserAdapter;
import com.example.climbup1.Model.userProfile;
import com.example.climbup1.R;
import com.example.climbup1.Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<userProfile> mUsers;
    private Spinner spinner;
    private static final String[] paths = {"All","Boulder", "Speed", "Rock"};
    EditText search_bar;
    String filed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        spinner = (Spinner) view.findViewById(R.id.searchspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar=view.findViewById(R.id.search_bar);

        mUsers=new ArrayList<>();
        userAdapter=new UserAdapter(getContext(),mUsers,true);
        recyclerView.setAdapter(userAdapter);

        readUsers();
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    public void searchUsers(String s){
        Query query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    userProfile userProfile=snapshot.getValue(userProfile.class);
                    if(userProfile.filed.equals(filed)||filed.equals("All"))
                      mUsers.add(userProfile);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void  readUsers(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(search_bar.getText().toString().equals("")){
                    mUsers.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        userProfile userProfile= snapshot.getValue(userProfile.class);
                        if(userProfile.filed.equals(filed)||filed.equals("All"))
                            mUsers.add(userProfile);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        readUsers();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        filed=paths[0];
    }
}
