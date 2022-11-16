package com.anvaishy.easytmedc_receptionist_app;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class SOSAdapter extends RecyclerView.Adapter<SOSAdapter.ViewHolder> {

    private final ArrayList<RequestSOS> dataset;

    public SOSAdapter(ArrayList<RequestSOS> dataset) {
        this.dataset = dataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView uid;
        private TextView desc;
        private TextView time;
        private TextView location;
        private CardView card;
        private Button respond;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            uid = itemView.findViewById(R.id.student_id);
            desc = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            card = itemView.findViewById(R.id.card);
            respond = itemView.findViewById(R.id.respond);
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

        public void setLocation(String name) {
            this.location.setText(name);
        }

        public void setClick(boolean response) {

            if(response) {
                card.setCardBackgroundColor(Color.parseColor("#00FF00"));
                respond.setVisibility(View.GONE);
            }
            else {
                respond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        card.setCardBackgroundColor(Color.parseColor("#00FF00"));
                        respond.setVisibility(View.GONE);
                    }
                });
            }
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
        holder.setLocation(request.getLocation());
        holder.setTime(request.getTime());
        holder.setDesc(request.getDesc());
        holder.setClick(request.isResponded());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
