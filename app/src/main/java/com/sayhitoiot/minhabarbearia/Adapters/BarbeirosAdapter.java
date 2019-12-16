package com.sayhitoiot.minhabarbearia.Adapters;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sayhitoiot.minhabarbearia.Activityes.AgendaActivity;
import com.sayhitoiot.minhabarbearia.Barbeiro;
import com.sayhitoiot.minhabarbearia.Model.BarbeirosModel;
import com.sayhitoiot.minhabarbearia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BarbeirosAdapter extends RecyclerView.Adapter<BarbeirosAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Barbeiro> barbeirosArrayList;
    private Context ctx;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public BarbeirosAdapter(Context ctx, ArrayList<Barbeiro> barbeirosArrayList){
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.barbeirosArrayList = barbeirosArrayList;
    }

    @Override
    public BarbeirosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BarbeirosAdapter.MyViewHolder holder, final int position) {

        holder.time.setText(barbeirosArrayList.get(position).getNome());
        //Toast.makeText(ctx, storageRef.child(barbeirosArrayList.get(position).getPatchImage()).toString(), Toast.LENGTH_LONG).show();
        storageRef.child("fotos_barbeiros").child(barbeirosArrayList.get(position).getPatchImage()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.get()
                        .load(uri)
                        .fit()
                        .into(holder.iv);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //Toast.makeText(ctx, exception.toString(), Toast.LENGTH_LONG).show();
            }
        });

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AgendaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nome", barbeirosArrayList.get(position).getNome());
                intent.putExtra("id", barbeirosArrayList.get(position).getId());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barbeirosArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        CircleImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.tv);
            iv =  itemView.findViewById(R.id.iv);
        }

    }
}