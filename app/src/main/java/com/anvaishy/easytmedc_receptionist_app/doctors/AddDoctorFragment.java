package com.anvaishy.easytmedc_receptionist_app.doctors;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.anvaishy.easytmedc_receptionist_app.R;
import com.anvaishy.easytmedc_receptionist_app.databinding.FragmentAddDoctorBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class AddDoctorFragment extends Fragment {

    private AddDoctorViewModel mViewModel;

    public static AddDoctorFragment newInstance() {
        return new AddDoctorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentAddDoctorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_doctor, container,
                false);

        View root = binding.getRoot();

        Bundle args = getArguments();

        EditText name = root.findViewById(R.id.nameField);
        Spinner spinner = root.findViewById(R.id.docspinner);
        Button start = root.findViewById(R.id.startTime);
        Button end = root.findViewById(R.id.endTime);
        Button addOrEdit = (Button) root.findViewById(R.id.addDoctor);
        mViewModel = new ViewModelProvider(this).get(AddDoctorViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(mViewModel);

        if(args != null) {
            mViewModel.name.setValue(args.getString("name"));
            mViewModel.start.setValue(args.getString("start"));
            name.setFocusable(false);
            mViewModel.end.setValue(args.getString("end"));
            addOrEdit.setText("Edit Doctor");
        }

        final Observer<ArrayList<String>> specObserver = specs -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, specs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(args != null) mViewModel.spec.setValue(adapter.getPosition(args.getString("spec")));
            spinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            spinner.setSelection(mViewModel.spec.getValue());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("Some data: -+--",i+" "+l);
                    mViewModel.spec.setValue(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Log.d("Pos", " " + specs.size());
        };

        mViewModel.getSpecList().observe(getViewLifecycleOwner(), specObserver);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mHour, mMinute;
                String curr = start.getText().toString();
                if(curr.equals("Start Time")) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                }

                else {
                    int h = Integer.parseInt(curr.substring(0, 2));
                    int min = Integer.parseInt(curr.substring(3, 5));
                    String m = curr.substring(curr.length() - 2);
                    if(m.equals("AM") && h == 12) {
                        h -= 12;
                    }

                    else if(m.equals("PM") && h != 12) h += 12;

                    mHour = h;
                    mMinute = min;
                }


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                StringBuilder str = new StringBuilder();
                                if((hourOfDay != 0 && hourOfDay < 10) || (hourOfDay > 12 && hourOfDay < 22)) str.append("0");

                                if(hourOfDay > 12) str.append(hourOfDay - 12);
                                else if (hourOfDay == 0) str.append(12);
                                else str.append(hourOfDay);

                                str.append(':');

                                if(minute < 10) str.append("0");
                                str.append(minute);

                                if(hourOfDay >= 12) str.append(" PM");
                                else str.append(" AM");
                                mViewModel.start.setValue(str.toString());
                            }
                        }, mHour, mMinute, false);
                Log.e("Some data: ---","Time Selected");
                timePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int mHour, mMinute;
                String curr = end.getText().toString();
                if(curr.equals("End Time")) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                }

                else {
                    int h = Integer.parseInt(curr.substring(0, 2));
                    int min = Integer.parseInt(curr.substring(3, 5));
                    String m = curr.substring(curr.length() - 2);
                    if(m.equals("AM") && h == 12) {
                        h -= 12;
                    }

                    else if(m.equals("PM") && h != 12) h += 12;

                    mHour = h;
                    mMinute = min;
                }

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                StringBuilder str = new StringBuilder();
                                if(hourOfDay < 10 || (hourOfDay > 12 && hourOfDay < 22)) str.append("0");

                                if(hourOfDay > 12) str.append(hourOfDay - 12);
                                else str.append(hourOfDay);

                                str.append(':');

                                if(minute < 10) str.append("0");
                                str.append(minute);

                                if(hourOfDay >= 12) str.append(" PM");
                                else str.append(" AM");
                                mViewModel.end.setValue(str.toString());
                            }
                        }, mHour, mMinute, false);
                Log.e("Some data: ---","Time Selected");
                timePickerDialog.show();
            }
        });


        addOrEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(args == null) mViewModel.add();
                else mViewModel.edit(args.getString("ID"));
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;
    }


}