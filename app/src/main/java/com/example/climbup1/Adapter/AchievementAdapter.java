package com.example.climbup1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.climbup1.Model.Achievement;
import com.example.climbup1.Model.Misson;
import com.example.climbup1.Model.Post;
import com.example.climbup1.Model.userProfile;
import com.example.climbup1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import okhttp3.internal.cache.DiskLruCache;


public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.viewHolder>{
    private Context mContext;
    private List<Misson> mMisson;
    boolean flag=true;

    private FirebaseUser firebaseUser;

    public AchievementAdapter(Context mContext, List<Misson> mMisson) {
        this.mContext = mContext;
        this.mMisson = mMisson;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.missons_item,parent,false);

        return new AchievementAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        loadMissons(firebaseUser.getUid(),holder.misson,holder.checkbox);

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String misson=holder.misson.getText().toString();
          //      addAchienement(firebaseUser.getUid(),misson);
            }
        });
    }
    private void loadMissons(final String id, final TextView missons, final ImageView imageView){

        for (Misson mis:mMisson) {
            if (AlreadyDone(mis,id)) {
                missons.setText(mis.getMisson());
                imageView.setImageResource(R.drawable.checkbox);
                imageView.setTag("done");
            } else {
                missons.setText(mis.getMisson());
                imageView.setImageResource(R.drawable.emptycheckbox);
                imageView.setTag("not");
            }
        }
    }

    private boolean AlreadyDone(final Misson misson, String id){
        final boolean[] flag = {false};
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Achievements").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Achievement achievement= snapshot.getValue(Achievement.class);
                    if(misson.getMissonid().equals(achievement.getMissonid())){
                        flag[0] =true;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return flag[0];
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public ImageView checkbox;
        public TextView misson;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            checkbox=itemView.findViewById(R.id.checkbox);
            misson=itemView.findViewById(R.id.misson);

        }
    }
 /*   private void addAchienement(String userid,String misson){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Achievements").child(userid);

        HashMap<String,Object> hashMap= new HashMap<>();
        hashMap.put("missonid",missonid);
        hashMap.put("userid",userid);

        reference.push().setValue(hashMap);
    }*/

    }

