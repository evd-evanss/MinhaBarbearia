package com.sayhitoiot.minhabarbearia.Activityes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sayhitoiot.minhabarbearia.Adapters.HorarioAdapter;
import com.sayhitoiot.minhabarbearia.Adapters.ServicesAdapter;
import com.sayhitoiot.minhabarbearia.Model.AgendamentoModel;
import com.sayhitoiot.minhabarbearia.Model.SelectHoraModel;
import com.sayhitoiot.minhabarbearia.Model.Servico;
import com.sayhitoiot.minhabarbearia.Model.ServicoModel;
import com.sayhitoiot.minhabarbearia.R;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import static com.sayhitoiot.minhabarbearia.Activityes.LoginActivity.sharedPrefManager;

public class AgendaActivity extends AppCompatActivity {
    CollapsibleCalendar collapsibleCalendar;
    public static TextView tv_Dia;
    public static TextView tv_Hora;
    public static TextView tv_Total;
    Button btnAgendar;
    private String nome;
    String dia;
    String mes;
    String ano;
    private String id;
    //Referencias Database
    private DatabaseReference refAgendaBarbeiros;
    private DatabaseReference refAgendaClientes;
    private DatabaseReference refAgenda;
    private DatabaseReference refServicos;
    private DatabaseReference refHorarioSelecionado;
    String smart_key;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewHorarios;
    private RecyclerView recyclerViewServicos;
    //private AgendaAdapter adapter;
    private HorarioAdapter horarioAdapter;
    private ServicesAdapter servicesAdapter;

    private ArrayList<ServicoModel> servicoModelArrayList = new ArrayList<>();
    private ArrayList<Servico> servicoArrayList = new ArrayList<>();
    private ArrayList<SelectHoraModel> horasArrayList = new ArrayList<>();
    public static Servico s = new Servico();
    public static String service;

   FirebaseAuth mAuth;
    //private SharedPrefManager sharedPrefManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        //recyclerView = findViewById(R.id.recycler_agenda);
        recyclerViewHorarios = findViewById(R.id.recycler_horarios);
        recyclerViewServicos = findViewById(R.id.recycler_agenda);
        tv_Total = findViewById(R.id.tv_total);
        tv_Dia = findViewById(R.id.tv_dia);
        tv_Hora = findViewById(R.id.tv_hora);
        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        nome = dados.getString("nome", "");
        id = dados.getString("id", "");
        //Inicializa referencias
        refAgendaBarbeiros = FirebaseDatabase.getInstance().getReference().child("AgendaBarbeiros");
        refAgendaClientes = FirebaseDatabase.getInstance().getReference().child("AgendaClientes");
        refServicos = FirebaseDatabase.getInstance().getReference().child("Serviços");
        refHorarioSelecionado = FirebaseDatabase.getInstance().getReference().child("Hora");
        refAgenda = refAgendaBarbeiros.child(nome);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        //listenerFirebase();
        listenerSelectHorasSimple();
//        initRecyclerViewHorario();
        listenerServicos();

        //Calendario
        collapsibleCalendar = findViewById(R.id.my_calendar);
        btnAgendar = findViewById(R.id.btn_agendar);
        collapsibleCalendar.setFirstDayOfWeek(1);


        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((tv_Dia.getText().toString().isEmpty()||tv_Dia.getText()==null)||
                (tv_Hora.getText().toString().isEmpty()||tv_Hora.getText()==null)||
                (tv_Total.getText().toString().isEmpty()||tv_Total.getText()==null)){
                    Toast.makeText(AgendaActivity.this, "Selecione data e hora!", Toast.LENGTH_SHORT).show();
                }else{
                    int endHora = tv_Hora.getText().length();
                    int endDia = tv_Dia.getText().length();

                    if(endHora==9){
                        Toast.makeText(AgendaActivity.this, "Selecione um horário", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(endDia==6){
                        Toast.makeText(AgendaActivity.this, "Selecione uma data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(service==null||service.isEmpty()){
                        Toast.makeText(AgendaActivity.this, "Selecione um serviço", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String data = tv_Dia.getText().subSequence(5,endDia).toString();
                    String hora = tv_Hora.getText().subSequence(9,endHora).toString();
                    verificaDisponibilidaHorario(id,smart_key, data, hora);

                    AgendamentoModel agendamentoModel= new AgendamentoModel();
                    agendamentoModel.setHorario(hora);
                    agendamentoModel.setDia(tv_Dia.getText().subSequence(5,endDia).toString());
                    agendamentoModel.setMes(mes);
                    agendamentoModel.setAno(ano);
                    agendamentoModel.setBarbeiro(nome);
                    agendamentoModel.setServico(service);
                    try {
                        agendamentoModel.setCliente(sharedPrefManager.getName());
                    }catch (Exception e){

                    }
                    if(tv_Total.getText().length()>15){
                        agendamentoModel.setPreco(tv_Total.getText().subSequence(15,21).toString());
//                        refAgendaBarbeiros.child(nome).child(id+ smart_key+hora).setValue(agendamentoModel);
//                        refAgendaClientes.child(user.getUid()).child(agendamentoModel.getServico()).setValue(agendamentoModel);
//                        Toast.makeText(AgendaActivity.this, "Agendamento realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        try {
                            if(compararDatas(dia,mes,ano,hora)){
                                refAgendaBarbeiros.child(nome).child(id+ smart_key+hora).setValue(agendamentoModel);
                                refAgendaClientes.child(user.getUid()).child(agendamentoModel.getServico()).setValue(agendamentoModel);
                                Toast.makeText(AgendaActivity.this, "Agendamento realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AgendaActivity.this, "Escolha uma data ou horário" +
                                        "superior ou igual ao tempo presente!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

//                if(!smart_key.isEmpty()&& smart_key !=null){
//
//                }else{
//                    Toast.makeText(AgendaActivity.this, "Você deves selecionar uma data primeiro!", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                smart_key = day.getDay()+ (day.getMonth() + 1) + day.getYear()+"";
                dia = day.getDay()+"";
                mes = day.getMonth()+1+"";
                ano = day.getYear()+"";
                tv_Dia.setText("Data: "+dia+" / "+mes+" / "+ano);
                //Toast.makeText(AgendaActivity.this, "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });
    }

    private boolean compararDatas(String dia, String mes, String ano, String hora) throws ParseException {
        String data_convert= dia+'-'+mes+'-'+ano+" "+hora;
        //"2015-01-24 17:39
        Date data = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(data_convert);
        Calendar cal = Calendar.getInstance();
        //cal.setTime(data);
        long hoje = cal.getTimeInMillis();
        long data_agenda = data.getTime();
        if(data_agenda>=hoje){
            return true;
        }
        return false;
    }

    private void verificaDisponibilidaHorario(final String id, final String smart_key, final String data, final String hora) {
        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        Query buscaDataeHora = refAgenda.child(id+smart_key+hora).child("horario");
        buscaDataeHora.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    //AgendamentoModel a = dataSnapshot.getValue(AgendamentoModel.class);
                    String compare = dataSnapshot.getValue(String.class);
                    //Toast.makeText(AgendaActivity.this, compare, Toast.LENGTH_SHORT).show();
                    if(compare.contains(hora)){
                        //Toast.makeText(AgendaActivity.this, "horário não disponível", Toast.LENGTH_SHORT).show();
                    }
                    
                } catch (Exception e) {
                        //Toast.makeText(AgendaActivity.this, "horário disponivel", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerViewHorario(){
        horarioAdapter = new HorarioAdapter(getApplicationContext(), horasArrayList);
        recyclerViewHorarios.setItemViewCacheSize(34);
        recyclerViewHorarios.setAdapter(horarioAdapter);
        recyclerViewHorarios.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void listenerServicos() {
        refServicos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                servicoArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Servico servico = new Servico();
                    servico.setServico(postSnapshot.getKey());
                    servico.setValor(postSnapshot.getValue(String.class));
                    servicoArrayList.add(servico);
                }

                servicesAdapter = new ServicesAdapter(getApplicationContext(), servicoArrayList);
                recyclerViewServicos.setItemViewCacheSize(34);
                recyclerViewServicos.setAdapter(servicesAdapter);
                recyclerViewServicos.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void listenerSelectHoras(final String[] horarios) {
        refHorarioSelecionado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                horasArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot getSnapshot : postSnapshot.getChildren()){
                        if(!horarios[Integer.parseInt(postSnapshot.getKey())].contains(getSnapshot.getKey())){
                            Toast.makeText(AgendaActivity.this, getSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                            SelectHoraModel hora = new SelectHoraModel();
                            hora.setKey(getSnapshot.getKey());
                            hora.setState(getSnapshot.getValue(Boolean.class));
                            horasArrayList.add(hora);
                        }else{
                            Toast.makeText(AgendaActivity.this, getSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                if(horasArrayList.size()>0)
                initRecyclerViewHorario();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listenerSelectHorasSimple() {
        refHorarioSelecionado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                horasArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot getSnapshot : postSnapshot.getChildren()){

                            SelectHoraModel hora = new SelectHoraModel();
                            hora.setKey(getSnapshot.getKey());
                            hora.setState(getSnapshot.getValue(Boolean.class));
                            horasArrayList.add(hora);
                    }
                }
                if(horasArrayList.size()>0)
                    initRecyclerViewHorario();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
