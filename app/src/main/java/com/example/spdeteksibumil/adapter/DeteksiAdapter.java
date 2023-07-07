package com.example.spdeteksibumil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.model.ModelDeteksi;

import java.util.ArrayList;

public class DeteksiAdapter extends RecyclerView.Adapter<DeteksiAdapter.DeteksiHolder> {

    int varGlobal = 0;
    private Context ctx;
    private ArrayList<ModelDeteksi> modelDeteksiArrayList;

    private ArrayList<ModelDeteksi> selectedPenyakit;

    public DeteksiAdapter(Context context, ArrayList<ModelDeteksi> items){
        this.ctx = context;
        this.modelDeteksiArrayList = new ArrayList<>();
        this.modelDeteksiArrayList.addAll(items);
    }

    @Override
    public DeteksiAdapter.DeteksiHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gejala, parent, false);
        return new DeteksiAdapter.DeteksiHolder(view);
    }

    @Override
    public void onBindViewHolder(DeteksiHolder holder, final int position) {
        final ModelDeteksi data = modelDeteksiArrayList.get(position);

        holder.cbGejala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton checkboxView, boolean isChecked) {
                ModelDeteksi modelDeteksi = (ModelDeteksi) checkboxView.getTag();

                if(isChecked) {
                    if (varGlobal >= 6) {
                        Toast.makeText(ctx, "Maaf, Maksimal Memilih 6 Gejala", Toast.LENGTH_SHORT).show();
                        checkboxView.setChecked(false);
                    } else {
                        modelDeteksi.setSelected(isChecked);
                        varGlobal++;
                    }
                } else {
                    if (varGlobal <= 3) {
                        Toast.makeText(ctx, "Minimal Harus Memilih 3 Gejala", Toast.LENGTH_SHORT).show();
                        checkboxView.setChecked(true);
                    } else {
                        modelDeteksi.setSelected(isChecked);
                        varGlobal--;
                    }
                }
            }
        });

        holder.cbGejala.setText(data.getStGejala());
        holder.cbGejala.setChecked(data.isSelected());
        holder.cbGejala.setTag(data);
    }

    @Override
    public int getItemCount() { return modelDeteksiArrayList.size(); }

    static class DeteksiHolder extends RecyclerView.ViewHolder {
        CheckBox cbGejala;

        public DeteksiHolder(View itemView) {
            super(itemView);
            cbGejala = itemView.findViewById(R.id.cbGejala);
        }
    }
}
