package com.example.spdeteksibumil.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.activities.DetailPenyakitActivity;
import com.example.spdeteksibumil.model.Penyakit;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class PenyakitAdapter extends RecyclerView.Adapter<PenyakitAdapter.PenyakitHolder> {

    private Context ctx;
    private ArrayList<Penyakit> modelDaftarPenyakitArrayList;

    public PenyakitAdapter(Context context, ArrayList<Penyakit> items) {
        this.ctx = context;
        this.modelDaftarPenyakitArrayList = items;
    }

    @NonNull
    @Override
    public PenyakitAdapter.PenyakitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penyakit, parent, false);
        return new PenyakitHolder(view);
    }

    @Override
    public void onBindViewHolder(PenyakitAdapter.PenyakitHolder holder, final int position) {
        final Penyakit data = modelDaftarPenyakitArrayList.get(position);

        holder.KodePenyakit.setText(data.getKode());
        holder.NamaPenyakit.setText(data.getNama());

        holder.cvListData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, DetailPenyakitActivity.class)
                        .putExtra("KODE_PENYAKIT", data.getKode()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelDaftarPenyakitArrayList.size();
    }

    static class PenyakitHolder extends RecyclerView.ViewHolder {
        public MaterialCardView cvListData;
        public TextView KodePenyakit;
        public TextView NamaPenyakit;

        public PenyakitHolder(View itemView) {
            super(itemView);
            cvListData = itemView.findViewById(R.id.cvListData);
            KodePenyakit = itemView.findViewById(R.id.KodePenyakit);
            NamaPenyakit = itemView.findViewById(R.id.NamaPenyakit);
        }
    }
}
