package com.anvaishy.easytmedc_receptionist_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private ArrayList<Doctor> dataSet;
    private final OnItemClickListener listener1;
    private final OnItemClickListener listener2;

    public interface OnItemClickListener {
        void onItemClick(Doctor item);
    }

    public DoctorAdapter(ArrayList<Doctor> dataSet, OnItemClickListener listener1, OnItemClickListener listener2) {
        this.dataSet = dataSet;
        this.listener1 = listener1;
        this.listener2 = listener2;
    }

    public void updateList(ArrayList<Doctor> data) {
        dataSet = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView spec;
        private final TextView start;
        private final TextView end;
        private final Button button;
        private final CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            spec = itemView.findViewById(R.id.spec);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            button = itemView.findViewById(R.id.delete);
            card = itemView.findViewById(R.id.docCard);
        }

        public TextView getName() {
            return name;
        }

        public TextView getSpec() {
            return spec;
        }

        public TextView getStart() {
            return start;
        }

        public TextView getEnd() {
            return end;
        }

        public void setListener(OnItemClickListener listener1, OnItemClickListener listener2, Doctor doctor) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener1.onItemClick(doctor);
                }
            });

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener2.onItemClick(doctor);
                }
            });
        }
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
        Doctor doc = dataSet.get(position);

        holder.getName().setText(doc.getName());
        holder.getSpec().setText(doc.getSpecialisation());
        holder.getStart().setText(doc.getStartTime());
        holder.getEnd().setText(doc.getEndTime());
        holder.setListener(listener1, listener2, doc);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
