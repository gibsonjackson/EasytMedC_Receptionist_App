package com.anvaishy.easytmedc_receptionist_app.sos;

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
import android.widget.RadioGroup;

import com.anvaishy.easytmedc_receptionist_app.R;

import java.util.ArrayList;

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
            Log.d("Log returns", sosList.toString());
            SOSAdapter adapter = new SOSAdapter(sosList, new SOSAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RequestSOS item) {
                    mViewModel.respondSOS(item);
                }
            }, new SOSAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RequestSOS item) {
                    double lat = item.getLocation().getLatitude();
                    double lon = item.getLocation().getLongitude();
                    Uri link = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + lat + ","  + lon);

                    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, link);
                    // Make the Intent explicit by setting the Google Maps package
//                    mapIntent.setPackage("com.google.android.apps.maps");

                    // Attempt to start an activity that can handle the Intent
                    startActivity(mapIntent);
                }
            });
            sos_list.setAdapter(adapter);
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getList().observe(getViewLifecycleOwner(), listObserver);


        RadioGroup rGroup = (RadioGroup) root.findViewById(R.id.radioGroup);

        final Observer<Boolean> todayObserver = today -> {
            if(today) rGroup.check(R.id.today);
            else rGroup.check(R.id.history);

            mViewModel.getList();
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.isToday.observe(getViewLifecycleOwner(), todayObserver);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.today) {
                    mViewModel.isToday.setValue(true);
                } else {
                    mViewModel.isToday.setValue(false);
                }
            }
        });

        return root;
    }

}