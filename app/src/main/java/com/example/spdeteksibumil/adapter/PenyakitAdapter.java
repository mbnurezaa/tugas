package com.example.spdeteksibumil.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.activities.DetailPenyakitActivity;
import com.example.spdeteksibumil.model.ModelDaftarPenyakit;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class PenyakitAdapter extends RecyclerView.Adapter<PenyakitAdapter.PenyakitHolder> {

    private Context ctx;
    private ArrayList<ModelDaftarPenyakit> modelDaftarPenyakitArrayList;

    public PenyakitAdapter(Context context, ArrayList<ModelDaftarPenyakit> items){
        this.ctx = context;
        this.modelDaftarPenyakitArrayList = items;
    }

    @Override
    public PenyakitAdapter.PenyakitHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penyakit,parent, false );
        return new PenyakitHolder(view);
    }

    @Override
    public void onBindViewHolder(PenyakitAdapter.PenyakitHolder holder, final int position) {
        final ModelDaftarPenyakit data = modelDaftarPenyakitArrayList.get(position);

        holder.KodePenyakit.setText(data.getStrKode());
        holder.NamaPenyakit.setText(data.getStDaftarPenyakit());

        holder.cvListData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ctx.startActivity(new Intent(ctx, DetailPenyakitActivity.class)
                        .putExtra("KODE_PENYAKIT", data.getStrKode()));
            }
        });
    }

    @Override
    public int getItemCount() { return modelDaftarPenyakitArrayList.size(); }

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
