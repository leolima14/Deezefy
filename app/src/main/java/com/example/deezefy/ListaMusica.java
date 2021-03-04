package com.example.deezefy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ListaMusica extends AppCompatActivity {

    RecyclerView rView;
    FirebaseAuth fAuth;
    Button mHome;
    FirebaseFirestore fStore;
    String UserID;
    ArrayList<Musica> musicas;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_musica);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        rView = findViewById(R.id.listaMusicas);
        mHome = findViewById(R.id.backHome);
        rView.setLayoutManager(new LinearLayoutManager(this));
        musicas = new ArrayList<Musica>();
        CollectionReference db = fStore.collection("musica");
        db.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                        String nomeArtista = document.getString("Artista");
                        int duracao = document.getLong("Duracao").intValue();
                        String nome = document.getString("Nome");
                        Musica m = new Musica(nomeArtista, duracao, nome);
                        musicas.add(m);
                    }
                    myAdapter = new MyAdapter(musicas);
                    rView.setAdapter(myAdapter);
                }
                Log.d("Size", String.valueOf(musicas.size()));

            }
        });
    }

    public void backHome(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}