package com.example.deezefy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMusica extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID, nome;
    EditText mNome, mDuracao;
    TextView nomeArtista, mArtista;
    Button sAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_musica);
        mNome = findViewById(R.id.addNome);
        mDuracao = findViewById(R.id.duracao);
        nomeArtista = findViewById(R.id.nomeArtista);
        mArtista = findViewById(R.id.nomeArt);
        sAdd = findViewById(R.id.criar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        DocumentReference dbArt = fStore.collection("artista").document(UserID);
        dbArt.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        nome = document.getString("Nome Artistico");
                        mArtista.setText(nome);
                    }
                }
            }
        });
    }

    public void Criar(View view) {
        String nomeMusica = mNome.getText().toString().trim();
        int duracao = Integer.parseInt(mDuracao.getText().toString().trim());
        DocumentReference db = fStore.collection("musica").document(UserID);
        Map<String, Object> novaMusica = new HashMap<>();
        novaMusica.put("Nome", nomeMusica);
        novaMusica.put("Duracao", duracao);
        novaMusica.put("Artista", nome);
        db.set(novaMusica).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Musica", "Nova musica adicionada");
            }
        });
    }
}