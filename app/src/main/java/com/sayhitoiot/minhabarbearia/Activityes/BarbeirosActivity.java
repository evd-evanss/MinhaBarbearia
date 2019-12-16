package com.sayhitoiot.minhabarbearia.Activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sayhitoiot.minhabarbearia.Adapters.AgendaAdapter;
import com.sayhitoiot.minhabarbearia.Adapters.BarbeirosAdapter;
import com.sayhitoiot.minhabarbearia.Barbeiro;
import com.sayhitoiot.minhabarbearia.Model.AgendamentoModel;
import com.sayhitoiot.minhabarbearia.Model.BarbeirosModel;
import com.sayhitoiot.minhabarbearia.R;
import com.sayhitoiot.minhabarbearia.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sayhitoiot.minhabarbearia.Activityes.LoginActivity.mAuth;
import static com.sayhitoiot.minhabarbearia.Activityes.LoginActivity.sharedPrefManager;

public class BarbeirosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewAgenda;
    private ArrayList<Barbeiro> barbeirosArrayList = new ArrayList<>();;
    private ArrayList<AgendamentoModel> agendamentoModelArrayList = new ArrayList<>();
    private DatabaseReference refAgenda;
    private DatabaseReference refAgendaCliente;
    private DatabaseReference refBarbeiros;
    private BarbeirosAdapter adapter;
    private AgendaAdapter agendaAdapter;
    private CircleImageView userImageView;
    private TextView userTextView;
    private TextView emailTextView;
    private TextView diaTextView;
    private TextView mesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbeiros);
        refAgenda = FirebaseDatabase.getInstance().getReference().child("Hora");

        userImageView = findViewById(R.id.iv_account);
        userTextView = findViewById(R.id.tv_nameuser);
        emailTextView = findViewById(R.id.tv_email);
        diaTextView = findViewById(R.id.tv_dia);
        mesTextView = findViewById(R.id.tv_mes);
        initUI();
        refBarbeiros = FirebaseDatabase.getInstance().getReference().child("Barbeiros");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        refAgendaCliente = FirebaseDatabase.getInstance().getReference().child("AgendaClientes").child(user.getUid());
        //RecyclerVIew
        recyclerView = findViewById(R.id.recycler);
        recyclerViewAgenda = findViewById(R.id.recyclerViewAgenda);
        initRecyclerViewAgenda();

       listenerFirebase();

        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long offset = snapshot.getValue(Long.class);
                long estimatedServerTimeMs = System.currentTimeMillis() + offset;
                String date = getDate(estimatedServerTimeMs);
                if(!date.isEmpty()){
                    String[] dateSplit = date.split("-");
                    diaTextView.setText(dateSplit[0]);
                    mesTextView.setText(getMesExtenso(dateSplit[1]));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getMesExtenso(String s) {
        String mes= "";
        if(s.contentEquals("01")){
            mes = "Janeiro";
        }
        if(s.contentEquals("02")){
            mes = "Fevereiro";
        }
        if(s.contentEquals("03")){
            mes = "Março";
        }
        if(s.contentEquals("04")){
            mes = "Abril";
        }
        if(s.contentEquals("05")){
            mes = "Maio";
        }
        if(s.contentEquals("06")){
            mes = "Junho";
        }
        if(s.contentEquals("07")){
            mes = "Julho";
        }
        if(s.contentEquals("08")){
            mes = "Agosto";
        }
        if(s.contentEquals("09")){
            mes = "Setembro";
        }
        if(s.contentEquals("10")){
            mes = "Outubro";
        }
        if(s.contentEquals("11")){
            mes = "Novembro";
        }
        if(s.contentEquals("12")){
            mes = "Dezembro";
        }
        return mes;
    }

    private void initUI() {
        try{
            Picasso.get()
                    .load(sharedPrefManager.getPhoto())
                    .fit()
                    .into(userImageView);
            userTextView.setText(sharedPrefManager.getName());
            emailTextView.setText(sharedPrefManager.getUserEmail());
        }catch (Exception e){

        }

    }

    private void listenerFirebase() {
        refBarbeiros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                barbeirosArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Barbeiro b = postSnapshot.getValue(Barbeiro.class);
                    barbeirosArrayList.add(b);
                }
                adapter = new BarbeirosAdapter(getApplicationContext(), barbeirosArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerViewAgenda() {
        refAgendaCliente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                agendamentoModelArrayList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        AgendamentoModel agendamentoModel = postSnapshot.getValue(AgendamentoModel.class);
                        agendamentoModelArrayList.add(agendamentoModel);
                    }
                }
                agendaAdapter = new AgendaAdapter(getApplicationContext(), agendamentoModelArrayList);
                recyclerViewAgenda.setAdapter(agendaAdapter);
                recyclerViewAgenda.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        return date;
    }
}
