package com.anvaishy.easytmedc_receptionist_app.meddocs;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.anvaishy.easytmedc_receptionist_app.R;
import com.anvaishy.easytmedc_receptionist_app.doctors.AddDoctorFragment;

public class MedDocsFragment extends Fragment {

    private MedDocsViewModel mViewModel;
    public static MedDocsFragment newInstance() {
        return new MedDocsFragment();
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Medical Document Search");
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_med_docs, container, false);
        EditText editText = root.findViewById(R.id.nameField);
        root.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Log.e("passing: ",editText.getText().toString());

                Fragment fragment = new MedDocsList();
                Bundle bundle = new Bundle();
                bundle.putString("ID",editText.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MedDocsViewModel.class);
        // TODO: Use the ViewModel
    }

}