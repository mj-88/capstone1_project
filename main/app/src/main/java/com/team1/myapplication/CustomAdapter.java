package com.team1.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    ImageView load;
    private ArrayList<Food> arrayList;
    private Context context;


//    StorageReference pathReference = storageReference.child(arrayList.get(position).getImageName());

    public CustomAdapter(ArrayList<Food> arrayList2, Context context) {
        this.arrayList = arrayList2;
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


        StorageReference ImageReference = FirebaseStorage.getInstance().getReference("image").child(arrayList.get(position).getImageName());
        ImageReference.getDownloadUrl().addOnSuccessListener(
                uri -> Glide.with(context)
                            .load(uri)
                            .dontAnimate()
                            .override(200,200)
                             .fitCenter()
                            .into(holder.iv_food)
        );
//        youtube
//        Glide.with(holder.itemView)
//                .load(arrayList.get(position).getImageUri())
//                .error(R.drawable.camera_button)
//                .dontAnimate()
//                .override(200,200)
//                .fitCenter()
//                .into(holder.iv_food);


        holder.tv_mealName.setText(arrayList.get(position).getMealName());
        holder.tv_saveDate.setText(arrayList.get(position).getSaveDate());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0 );
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_food;
        TextView tv_mealName;
        TextView tv_saveDate;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_food = itemView.findViewById(R.id.iv_food);

            this.tv_mealName = itemView.findViewById(R.id.tv_mealName);

            this.tv_saveDate = itemView.findViewById(R.id.tv_saveDate);

        }
    }
}
