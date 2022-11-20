package com.anvaishy.easytmedc_receptionist_app.meddocs;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvaishy.easytmedc_receptionist_app.R;

public class MedDocsFragment extends Fragment {

    private MedDocsViewModel mViewModel;

    public static MedDocsFragment newInstance() {
        return new MedDocsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_med_docs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MedDocsViewModel.class);
        // TODO: Use the ViewModel
    }

}