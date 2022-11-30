package com.anvaishy.easytmedc_receptionist_app.medpass;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anvaishy.easytmedc_receptionist_app.R;
import com.anvaishy.easytmedc_receptionist_app.doctors.Doctor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.ViewHolder> {

    private ArrayList<MedicalPassRequestGlobal> dataSet;
    private final PassAdapter.OnItemClickListener listener1;
    private final PassAdapter.OnItemClickListener listener2;

    public interface OnItemClickListener {
        void onItemClick(MedicalPassRequestGlobal item);
    }

    public PassAdapter(ArrayList<MedicalPassRequestGlobal> dataSet, PassAdapter.OnItemClickListener listener1, PassAdapter.OnItemClickListener listener2) {
        this.dataSet = dataSet;
        this.listener1 = listener1;
        this.listener2 = listener2;
    }

    public void updateList(ArrayList<MedicalPassRequestGlobal> data) {
        dataSet = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView desc;
        private final TextView start;
        private final TextView end;
        private final TextView name;
        private final TextView email;
        private final Button accept;
        private final Button reject;
        private final CardView card;
        private final CardView desc_card;
        private final CardView arri_card;
        private final CardView depa_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.med_pass_desc);
            start = itemView.findViewById(R.id.depart_time);
            end = itemView.findViewById(R.id.arrival_time);
            accept = itemView.findViewById(R.id.Accept);
            reject = itemView.findViewById(R.id.Reject);
            card = itemView.findViewById(R.id.requestCard);
            desc_card = itemView.findViewById(R.id.desc_card);
            email = itemView.findViewById(R.id.student_email);
            name = itemView.findViewById(R.id.passname);
            arri_card = itemView.findViewById(R.id.arri_card);
            depa_card = itemView.findViewById(R.id.depa_card);
        }




        public void setListener(PassAdapter.OnItemClickListener listener1, PassAdapter.OnItemClickListener listener2, MedicalPassRequestGlobal doctor) {
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener1.onItemClick(doctor);
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener2.onItemClick(doctor);
                }
            });
        }

        public TextView getDesc() {
            return desc;
        }

        public TextView getStart() {
            return start;
        }

        public TextView getEnd() {
            return end;
        }

        public CardView getCard() {
            return card;
        }
    }

    @NonNull
    @Override
    public PassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_pass_request_card, parent, false);

        return new PassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassAdapter.ViewHolder holder, int position) {
        MedicalPassRequestGlobal doc = dataSet.get(position);

        int status = doc.getStatus();
        if(status==2){

            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
            holder.card.setCardBackgroundColor(Color.RED);
            holder.desc_card.setCardBackgroundColor(Color.RED);
            holder.arri_card.setCardBackgroundColor(Color.RED);
            holder.depa_card.setCardBackgroundColor(Color.RED);
        }
        if(status==1){
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
            holder.card.setCardBackgroundColor(Color.GREEN);
            holder.desc_card.setCardBackgroundColor(Color.GREEN);
            holder.arri_card.setCardBackgroundColor(Color.GREEN);
            holder.depa_card.setCardBackgroundColor(Color.GREEN);
        }
        holder.desc.setText(doc.getDescription());
        holder.name.setText(doc.getName());

        holder.start.setText(doc.getDepart().toDate().toString());
        holder.end.setText(doc.getArrival().toDate().toString());
        holder.email.setText(doc.getUid());
        holder.setListener(listener1, listener2, doc);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
