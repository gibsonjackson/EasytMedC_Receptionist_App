package com.anvaishy.easytmedc_receptionist_app.meddocs;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.anvaishy.easytmedc_receptionist_app.activity.SignedInActivity;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anvaishy.easytmedc_receptionist_app.R;
import com.anvaishy.easytmedc_receptionist_app.activity.MainActivity;
import com.anvaishy.easytmedc_receptionist_app.activity.SignedInActivity;
import com.anvaishy.easytmedc_receptionist_app.meddocs.Document;

import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private ArrayList<Document> dataSet;
    private final OnItemClickListener listener1;

    public interface OnItemClickListener {
        void onItemClick(Document item);
    }

    public DocumentAdapter(ArrayList<Document> dataSet, OnItemClickListener listener1) {
        this.dataSet = dataSet;
        this.listener1 = listener1;
    }

    public void updateList(ArrayList<Document> data) {
        dataSet = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.docname);
            button = itemView.findViewById(R.id.docurl);
        }

        public TextView getName() {
            return name;
        }

        public void setListener(OnItemClickListener listener1, Document document) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener1.onItemClick(document);
                }
            });
        }
    }

    @NonNull
    @Override
    public DocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.ViewHolder holder, int position) {
        Document doc = dataSet.get(position);

        holder.getName().setText(doc.getName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.setListener(listener1, doc);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
