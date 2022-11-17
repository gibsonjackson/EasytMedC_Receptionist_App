package com.anvaishy.easytmedc_receptionist_app;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DoctorListFragment extends Fragment {

    private DoctorListViewModel mViewModel;

    public static DoctorListFragment newInstance() {
        return new DoctorListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Doctor List");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.doctor_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Spinner spinner = root.findViewById(R.id.spinner);

        ( (FloatingActionButton) root.findViewById(R.id.addDocFab) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Fragment fragment = new AddDoctorFragment();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mViewModel = new ViewModelProvider(this).get(DoctorListViewModel.class);
        // TODO: Use the ViewModel
        final Observer<ArrayList<Doctor>> listObserver = doctors -> {
            DoctorAdapter adapter = new DoctorAdapter(doctors, new DoctorAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Doctor item) {
                    mViewModel.deleteDoctor(item);
                    getParentFragmentManager().beginTransaction().replace(R.id.main_content, new DoctorListFragment()).commit();
                }
            },
                    new DoctorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Doctor item) {

                            Fragment frag = new AddDoctorFragment();
                            Bundle args = new Bundle();

                            args.putString("name", item.getName());
                            args.putString("start", item.getStartTime());
                            args.putString("end", item.getEndTime());
                            args.putString("spec", item.getSpecialisation());
                            args.putString("ID", item.getDocID());

                            frag.setArguments(args);

                            FragmentTransaction ft = getParentFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.main_content, frag);
                            ft.commit();
                        }
                    });
            recyclerView.setAdapter(adapter);
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getList().observe(getViewLifecycleOwner(), listObserver);

        final Observer<ArrayList<String>> specObserver = specs -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, specs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();
            spinner.setAdapter(adapter);
        };

        mViewModel.getSpecList().observe(getViewLifecycleOwner(), specObserver);

        return root;
    }

}