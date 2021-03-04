package com.example.deezefy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Perfil extends AppCompatActivity {
    TextView mArtista, mOuvinte;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mArtista = findViewById(R.id.isArtista);
        mOuvinte = findViewById(R.id.isOuvinte);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        DocumentReference dbArt = fStore.collection("artista").document(UserID);
        DocumentReference dbOuv = fStore.collection("ouvinte").document(UserID);
        dbArt.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        mArtista.setVisibility(View.VISIBLE);
                        mArtista.setText(document.getString("Nome Artistico"));
                    }
                }
            }
        });
        dbOuv.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        mOuvinte.setVisibility(View.VISIBLE);
                        mOuvinte.setText(document.getString("Nome"));
                    }
                }
            }
        });
    }
}