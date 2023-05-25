package com.team1.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    ImageView load;
    private ArrayList<Food> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<Food> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

        //com.team1.myapplication.LastMealActivity@6c47ce5

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        StorageReference pathReference = storageReference.child(arrayList.get(position).getImageName());

//        Glide.with(holder.itemView)
//                .load(arrayList.get(position).getImageName())
//                .into(holder.iv_profile);


        holder.tv_mealName.setText(arrayList.get(position).getMealName());
        holder.tv_saveDate.setText(arrayList.get(position).getSaveDate());



        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView).load(uri).into(holder.iv_profile);
                Log.d("제발    ! ㅅ", uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0 );
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;
        TextView tv_mealName;
        TextView tv_saveDate;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_profile = itemView.findViewById(R.id.iv_profile);

            this.tv_mealName = itemView.findViewById(R.id.tv_mealName);

            this.tv_saveDate = itemView.findViewById(R.id.tv_saveDate);

        }
    }
}
