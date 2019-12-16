package com.sayhitoiot.minhabarbearia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.sayhitoiot.minhabarbearia.Model.Servico;
import com.sayhitoiot.minhabarbearia.Model.ServicoModel;
import com.sayhitoiot.minhabarbearia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.common.primitives.Ints.tryParse;
import static com.sayhitoiot.minhabarbearia.Activityes.AgendaActivity.s;
import static com.sayhitoiot.minhabarbearia.Activityes.AgendaActivity.service;
import static com.sayhitoiot.minhabarbearia.R.color.cardview_dark_background;
import static com.sayhitoiot.minhabarbearia.R.color.unselected;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Servico> servicoModelArrayList;
    private Context ctx;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private List<Boolean> list_button;
    Integer custo = 0;

    public ServicesAdapter(Context ctx, ArrayList<Servico> servicoModelArrayList){
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.servicoModelArrayList = servicoModelArrayList;
        list_button = new ArrayList<Boolean>(Arrays.asList(new Boolean[this.servicoModelArrayList.size()]));
        for (int x = 0; x<list_button.size(); x++){
            list_button.set(x,false);
        }
    }

    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.servico_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ServicesAdapter.MyViewHolder holder, final int position) {
        holder.tvService.setText(servicoModelArrayList.get(position).getServico());
        holder.tvValor.setText(servicoModelArrayList.get(position).getValor());

    }

    @Override
    public int getItemCount() {
        return servicoModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvService;
        TextView tvValor;
        CardView cardViewService;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tv_service);
            tvValor =  itemView.findViewById(R.id.tv_valor);
            cardViewService =  itemView.findViewById(R.id.cdv_service);

            cardViewService.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(!list_button.get(getAdapterPosition())){

                            list_button.set(getAdapterPosition(), true);
                            service = servicoModelArrayList.get(getAdapterPosition()).getServico();
                            s.setServico(servicoModelArrayList.get(getAdapterPosition()).getServico());
                            s.setValor(servicoModelArrayList.get(getAdapterPosition()).getValor());
                            cardViewService.setCardBackgroundColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
                            tvService.setTextColor(ctx.getResources().getColor(R.color.colorText));
                            String valor  = tvValor.getText().toString();
                            String s[] = valor.split(",");;
                            somarCusto(s[0]);
                    }else{
                        list_button.set(getAdapterPosition(), false);
                        cardViewService.setCardBackgroundColor(ctx.getResources().getColor(R.color.colorText));
                        tvService.setTextColor(ctx.getResources().getColor(R.color.colorBlack));
                        String valor  = tvValor.getText().toString();
                        String s[] = valor.split(",");;
                        subtrairCusto(s[0]);
                    }

                    return true;
                }
            });
        }

    }

    private void somarCusto(String valor){
        custo += tryParse(valor);
        AgendaActivity.tv_Total.setText("Valor Total: R$ "+custo+",00");
    }

    private void subtrairCusto(String valor) {
        if (valor != null) {
            custo -= tryParse(valor);
            AgendaActivity.tv_Total.setText("Valor Total: R$ " + custo + ",00");
        }
    }

}