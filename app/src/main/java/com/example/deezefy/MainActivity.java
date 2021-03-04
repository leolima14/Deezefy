package com.example.deezefy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore fStore;
    TextView mAddMus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getUid() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }else{
            fStore = FirebaseFirestore.getInstance();
            mAddMus = findViewById(R.id.addMusica);
            DocumentReference db = fStore.collection("artista").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            mAddMus.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void addMusica(View view) {
        startActivity(new Intent(getApplicationContext(), AddMusica.class));
        finish();
    }

    public void listaMusica(View view) {
        startActivity(new Intent(getApplicationContext(), ListaMusica.class));
        finish();
    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(), Perfil.class));
        finish();
    }
}