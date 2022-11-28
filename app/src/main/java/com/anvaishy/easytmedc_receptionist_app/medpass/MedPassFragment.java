package com.anvaishy.easytmedc_receptionist_app.medpass;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvaishy.easytmedc_receptionist_app.DataRepository;
import com.anvaishy.easytmedc_receptionist_app.R;
import com.anvaishy.easytmedc_receptionist_app.doctors.AddDoctorFragment;

import java.util.ArrayList;

public class MedPassFragment extends Fragment {

    private MedPassViewModel mViewModel;

    public static MedPassFragment newInstance() {
        return new MedPassFragment();
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Medical Pass Request");


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_med_pass, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.pass_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewModel = new ViewModelProvider(this).get(MedPassViewModel.class);
        final Observer<ArrayList<MedicalPassRequestGlobal>> listObserver = medicalPassRequestGlobals -> {
            PassAdapter passAdapter = new PassAdapter(
                    medicalPassRequestGlobals,
                    new PassAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MedicalPassRequestGlobal item) {
                            DataRepository.updatePass(item,1);
                            mViewModel.getList();

                        }
                    },
                    new PassAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MedicalPassRequestGlobal item) {
                            DataRepository.updatePass(item,2);
                            mViewModel.getList();
                        }
                    }

            );
            recyclerView.setAdapter(passAdapter);
        };
        mViewModel.getList().observe(getViewLifecycleOwner(), listObserver);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MedPassViewModel.class);
        // TODO: Use the ViewModel
    }

}