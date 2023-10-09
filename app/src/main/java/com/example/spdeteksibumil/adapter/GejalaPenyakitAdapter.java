package com.example.spdeteksibumil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;

import java.util.ArrayList;
import java.util.List;

public class GejalaPenyakitAdapter extends RecyclerView.Adapter<GejalaPenyakitAdapter.ViewHolder> {

    private ArrayList<String> dataList;
    private Context context;

    public GejalaPenyakitAdapter(Context context, ArrayList<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // ViewHolder untuk menyimpan referensi tampilan item dalam RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textNumber;
        public TextView textItem;

        public ViewHolder(View itemView) {
            super(itemView);
            textNumber = itemView.findViewById(R.id.numberGejala);
            textItem = itemView.findViewById(R.id.namaGejala);
        }
    }

    // Membuat tampilan item baru (dipanggil oleh layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gejala, parent, false);
        return new ViewHolder(itemView);
    }

    // Mengganti konten tampilan item (dipanggil oleh layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = dataList.get(position);
        holder.textNumber.setText(Integer.toString(position + 1) + ".");
        holder.textItem.setText(item);
    }

    // Jumlah item dalam data set Anda (dipanggil oleh layout manager)
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


