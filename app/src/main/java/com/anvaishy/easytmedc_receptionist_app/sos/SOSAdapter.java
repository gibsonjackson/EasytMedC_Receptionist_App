package com.anvaishy.easytmedc_receptionist_app.sos;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anvaishy.easytmedc_receptionist_app.R;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class SOSAdapter extends RecyclerView.Adapter<SOSAdapter.ViewHolder> {

    private final ArrayList<RequestSOS> dataset;
    private final OnItemClickListener listener1, listener2, listener3;

    public interface OnItemClickListener {
        void onItemClick(RequestSOS item);
    }

    public SOSAdapter(ArrayList<RequestSOS> dataset, OnItemClickListener listener1, OnItemClickListener listener2, OnItemClickListener listener3) {
        this.dataset = dataset;
        this.listener1 = listener1;
        this.listener2 = listener2;
        this.listener3 = listener3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView uid;
        private TextView desc;
        private TextView time;
        private Button location;
        private CardView card;
        private Button respond;
        private Button call;
        private TextView phoneno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            uid = itemView.findViewById(R.id.student_id);
            desc = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            card = itemView.findViewById(R.id.card);
            respond = itemView.findViewById(R.id.respond);
            phoneno = itemView.findViewById(R.id.phoneNo);
            call = itemView.findViewById(R.id.call);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setUid(String uid) {
            this.uid.setText(uid);
        }

        public void setDesc(String d) {
            this.desc.setText(d);
        }

        public void setTime(long time) {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a, dd/MM");
            TimeZone tz = TimeZone.getDefault();
            sdf.setTimeZone(tz);

            this.time.setText(sdf.format(date));
        }
        public void setCall(OnItemClickListener listener,RequestSOS requestSOS){
            this.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {listener.onItemClick(requestSOS);}
            });
        }

        public void setLocation(OnItemClickListener listener, RequestSOS request) {
            this.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(request);
                }
            });
        }

        public void setClickAndColor(OnItemClickListener listener, boolean response, RequestSOS request) {

            if(response) {
                card.setCardBackgroundColor(Color.parseColor("#00FF00"));
                respond.setVisibility(View.GONE);
            }
            else {
                respond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(request);
                    }
                });
            }
        }

        public void setPhoneno(String phoneNo) {
            phoneno.setText(phoneNo);
        }
    }

    @NonNull
    @Override
    public SOSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sos_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SOSAdapter.ViewHolder holder, int position) {
        RequestSOS request = dataset.get(position);
        holder.setName(request.getName());
        holder.setUid(request.getSID());
        holder.setLocation(listener2, request);
        holder.setTime(request.getTime());
        holder.setDesc(request.getDesc());
        holder.setClickAndColor(listener1, request.isResponded(), request);
        holder.setPhoneno(request.getPhone());
        holder.setCall(listener3,request);
        if(request.getPhone().isEmpty()){
            holder.call.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
