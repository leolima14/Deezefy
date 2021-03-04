package com.example.deezefy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText mEmail, mPassword, mData_nascimento, mNome_artista, mNome, mBiografia, mAno, mSobrenome, mTel;
    TextView mLoginBtn;
    Button mRegistrar;
    SwitchCompat sArtista, sOuvinte;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.email_adress);
        mPassword = findViewById(R.id.password);
        mData_nascimento = findViewById(R.id.data_nascimento);
        mRegistrar = findViewById(R.id.button);
        mLoginBtn = findViewById(R.id.logar);
        sArtista = findViewById(R.id.artista);
        sOuvinte = findViewById(R.id.ouvinte);
        mNome_artista = findViewById(R.id.nome_artista);
        mBiografia = findViewById(R.id.biografia);
        mAno = findViewById(R.id.ano_formacao);
        mNome = findViewById(R.id.nome_ouvinte);
        mSobrenome = findViewById(R.id.sobrenome);
        mTel = findViewById(R.id.telefone);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                String data = mData_nascimento.getText().toString().trim();
                Log.d("TAG", email);
                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            UserID = fAuth.getCurrentUser().getUid();
                            DocumentReference db = fStore.collection("user").document(UserID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("data", data);
                            db.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "User successfully written!");
                                }
                            });
                            if(sArtista.isChecked()){
                                String nome_artistico = mNome_artista.getText().toString();
                                String bio = mBiografia.getText().toString();
                                String ano = mAno.getText().toString();
                                Map<String, Object> art = new HashMap<>();
                                art.put("Nome Artistico", nome_artistico);
                                art.put("Biografia", bio);
                                art.put("Ano",ano);
                                DocumentReference dbArt = fStore.collection("artista").document(UserID);
                                dbArt.set(art).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "Artista successfully written!");
                                    }
                                });
                            }
                            if(sOuvinte.isChecked()){
                                String nome = mNome.getText().toString();
                                String sobrenome = mSobrenome.getText().toString();
                                String telefone = mTel.getText().toString();
                                Map<String, Object> ouv = new HashMap<>();
                                ouv.put("Nome", nome);
                                ouv.put("Sobrenome", sobrenome);
                                ouv.put("Telefone", telefone);
                                DocumentReference dbOuv = fStore.collection("ouvinte").document(UserID);
                                dbOuv.set(ouv).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "Ouvinte successfully written!");
                                    }
                                });
                            }
                        }
                        else{
                            Log.d("TAG", "Error");
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });



    }
}