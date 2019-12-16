package com.sayhitoiot.minhabarbearia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.sayhitoiot.minhabarbearia.Activityes.AgendaActivity;
import com.sayhitoiot.minhabarbearia.Model.SelectHoraModel;
import com.sayhitoiot.minhabarbearia.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    DatabaseReference refHorarioSelecionado;
    public ArrayList<SelectHoraModel> listHorarios;
    private Context ctx;
    private List<Boolean> list_button;
    String horario = " ";
    public HorarioAdapter(Context ctx, ArrayList<SelectHoraModel> listHorarios){
        this.refHorarioSelecionado = refHorarioSelecionado;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.listHorarios = listHorarios;
        list_button = new ArrayList<Boolean>(Arrays.asList(new Boolean[this.listHorarios.size()]));
        for (int x = 0; x<list_button.size(); x++){
            list_button.set(x,false);
        }
    }

    @Override
    public HorarioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.horario_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HorarioAdapter.MyViewHolder holder, final int position) {
            holder.tv_horario.setText(listHorarios.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return listHorarios.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_horario;
        CardView cdv_horario;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_horario =  itemView.findViewById(R.id.tv_hora);
            cdv_horario =  itemView.findViewById(R.id.cdv_horario);

            cdv_horario.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(!list_button.get(getAdapterPosition())){
                        if(horario.contains(" ")){
//                            refHorarioSelecionado.child(""+(getAdapterPosition()+1))
//                                    .child(listHorarios.get(getAdapterPosition()).getKey())
//                                    .setValue(true);
                            list_button.set(getAdapterPosition(), true);
                            cdv_horario.setCardBackgroundColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
                            tv_horario.setTextColor(ctx.getResources().getColor(R.color.colorText));
                            horario = tv_horario.getText().toString();
                            AgendaActivity.tv_Hora.setText("Horário: "+horario);
                        }
                    }else{
                        list_button.set(getAdapterPosition(), false);
                        cdv_horario.setCardBackgroundColor(ctx.getResources().getColor(R.color.colorText));
                        tv_horario.setTextColor(ctx.getResources().getColor(R.color.colorBlack));
                        horario = " ";
                        AgendaActivity.tv_Hora.setText("Horário: ");
                    }

                    return true;
                }
            });

        }

    }
}