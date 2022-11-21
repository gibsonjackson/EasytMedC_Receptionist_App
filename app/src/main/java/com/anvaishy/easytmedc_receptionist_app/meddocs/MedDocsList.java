package com.anvaishy.easytmedc_receptionist_app.meddocs;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anvaishy.easytmedc_receptionist_app.R;
import com.anvaishy.easytmedc_receptionist_app.doctors.Doctor;
import com.anvaishy.easytmedc_receptionist_app.doctors.DoctorAdapter;
import com.anvaishy.easytmedc_receptionist_app.doctors.DoctorListViewModel;

import java.util.ArrayList;

public class MedDocsList extends Fragment {

    private MedDocsListViewModel mViewModel;
    TextView Title;
    String ID;
    public MedDocsList() {

    }
    String getID(String ref){
        ref = ref.toLowerCase();
        ref = ref.trim();
        return ref+"@hyderabad.bits-pilani.ac.in";
    }
    public static MedDocsList newInstance() {
        return new MedDocsList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_med_docs_list, container, false);
        Title=root.findViewById(R.id.DocumentID);
        Bundle args = getArguments();
        ID = (String) args.get("ID");

        Log.e("passing: ",ID+"---");
        ID = getID(ID);
        Log.e("passing: ",ID+"---");
        Title.setText(ID);
        RecyclerView recyclerView = root.findViewById(R.id.doc_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewModel = new ViewModelProvider(this).get(MedDocsListViewModel.class);
        final Observer<ArrayList<Document>> listObserver = documents -> {
            DocumentAdapter documentAdapter = new DocumentAdapter(documents, new DocumentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Document item) {
                String url = item.getLink();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            recyclerView.setAdapter(documentAdapter);
        };
        mViewModel.getList(ID).observe(getViewLifecycleOwner(), listObserver);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MedDocsListViewModel.class);
        // TODO: Use the ViewModel
    }

}