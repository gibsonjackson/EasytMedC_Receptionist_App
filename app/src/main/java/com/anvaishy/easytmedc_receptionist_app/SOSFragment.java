package com.anvaishy.easytmedc_receptionist_app;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Date;

public class SOSFragment extends Fragment {

    private SOSViewModel mViewModel;

    public static SOSFragment newInstance() {
        return new SOSFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("SOS Requests");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sos, container, false);

        RecyclerView sos_list = root.findViewById(R.id.sos_list);
        sos_list.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
        // TODO: Use the ViewModel
        final Observer<ArrayList<RequestSOS>> listObserver = sosList -> {
            Log.d("o", "call me daddy" + sosList.size());
            SOSAdapter adapter = new SOSAdapter(sosList);
            sos_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.currentList.observe(getViewLifecycleOwner(), listObserver);

        RadioGroup rGroup = (RadioGroup) root.findViewById(R.id.radioGroup);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.today) {
                    mViewModel.getListToday();
                } else {
                    mViewModel.getList();
                }
            }
        });

        return root;
    }

}