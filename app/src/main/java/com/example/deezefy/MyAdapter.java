package com.example.deezefy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Musica> musicas;

    MyAdapter(ArrayList<Musica> m){
        musicas = m;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Musica m = musicas.get(position);
        holder.nome.setText(m.nome);
        holder.duracao.setText(String.valueOf(m.duracao));
        holder.artista.setText(m.artista);
    }

    @Override
    public int getItemCount() {
        return musicas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, duracao, artista;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeMusica);
            duracao = itemView.findViewById(R.id.id_duracao);
            artista = itemView.findViewById(R.id.nome2);
        }
    }
}
