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
import com.sayhitoiot.minhabarbearia.Model.AgendamentoModel;
import com.sayhitoiot.minhabarbearia.Model.BarbeirosModel;
import com.sayhitoiot.minhabarbearia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<AgendamentoModel> agendamentoArrayList;
    private Context ctx;

    public AgendaAdapter(Context ctx, ArrayList<AgendamentoModel> agendamentoArrayList){
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.agendamentoArrayList = agendamentoArrayList;
    }

    @Override
    public AgendaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.agenda_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AgendaAdapter.MyViewHolder holder, final int position) {
        String data = agendamentoArrayList.get(position).getDia()+
                " / "+agendamentoArrayList.get(position).getMes()+
                " / "+agendamentoArrayList.get(position).getAno();
        holder.tv_barbeiro.setText("Barbeiro: "+(agendamentoArrayList.get(position).getBarbeiro()));
        holder.tv_dia.setText("Data: "+(agendamentoArrayList.get(position).getDia()));
        holder.tv_horario.setText("Horário: "+(agendamentoArrayList.get(position).getHorario()));
        holder.tv_service.setText("Serviço: "+(agendamentoArrayList.get(position).getServico()));
        holder.tv_price.setText("Preço: R$ "+(agendamentoArrayList.get(position).getPreco()));
    }

    @Override
    public int getItemCount() {
        return agendamentoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_dia;
        TextView tv_horario;
        TextView tv_service;
        TextView tv_price;
        TextView tv_barbeiro;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_dia = itemView.findViewById(R.id.id_dia);
            tv_horario =  itemView.findViewById(R.id.id_horario);
            tv_service =  itemView.findViewById(R.id.id_service);
            tv_price =  itemView.findViewById(R.id.id_price);
            tv_barbeiro =  itemView.findViewById(R.id.id_barbeiro);
        }

    }
}