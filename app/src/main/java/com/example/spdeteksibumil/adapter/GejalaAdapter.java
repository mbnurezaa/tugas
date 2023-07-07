package com.example.spdeteksibumil.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.model.Gejala;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GejalaAdapter extends RecyclerView.Adapter<GejalaAdapter.DeteksiHolder> {

    int varGlobal = 0;
    private Context ctx;
    private ArrayList<Gejala> listGejala;

    public GejalaAdapter(Context context, ArrayList<Gejala> items) {
        this.ctx = context;
        this.listGejala = new ArrayList<>();
        this.listGejala.addAll(items);
    }

    @NonNull
    @Override
    public GejalaAdapter.DeteksiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gejala, parent, false);
        return new GejalaAdapter.DeteksiHolder(view);
    }

    @Override
    public void onBindViewHolder(DeteksiHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Gejala data = listGejala.get(position);

        holder.cbGejala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkboxView, boolean isChecked) {
                if (isChecked) {
                    if (getSelectedGejala().size() == 6) {
                        listGejala.get(position).setSelected(false);
                        checkboxView.setChecked(false);
                        Toast.makeText(ctx, "Maaf, Maksimal Memilih 6 Gejala", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                listGejala.get(position).setSelected(isChecked);
                checkboxView.setChecked(isChecked);
            }
        });

        holder.cbGejala.setText(data.getNamaGejala().trim());
        holder.cbGejala.setChecked(data.isSelected());
        holder.cbGejala.setTag(data);
    }

    @Override
    public int getItemCount() {
        return listGejala.size();
    }

    public ArrayList<Gejala> getSelectedGejala() {
        Predicate<Gejala> hasTrue = Gejala::isSelected;
        return (ArrayList<Gejala>) this.listGejala.stream().filter(hasTrue).collect(Collectors.toList());
    }

    public void resetChecked() {
        listGejala.forEach(e -> e.setSelected(false));
        for (int i = 0; i < listGejala.size(); i++) {
            listGejala.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    static class DeteksiHolder extends RecyclerView.ViewHolder {
        CheckBox cbGejala;

        public DeteksiHolder(View itemView) {
            super(itemView);
            cbGejala = itemView.findViewById(R.id.cbGejala);
        }
    }
}
