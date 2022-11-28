package com.anvaishy.easytmedc_receptionist_app.doctors;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anvaishy.easytmedc_receptionist_app.DataRepository;

import java.util.ArrayList;

public class AddDoctorViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<Integer> spec = new MutableLiveData<>();
    public MutableLiveData<String> start = new MutableLiveData<>("Start Time");
    public MutableLiveData<String> end = new MutableLiveData<>("End Time");


    private MutableLiveData<ArrayList<String>> specList;

    public void add() {
        Doctor d = new Doctor(name.getValue(), specList.getValue().get(spec.getValue()), start.getValue(), end.getValue(), "");
        DataRepository.addDoctor(d);
    }

    public MutableLiveData<ArrayList<String>> getSpecList() {
        if (specList == null) {
            specList = new MutableLiveData<>();
        }
        SpecListTask runner = new SpecListTask();
        runner.execute();
        return specList;
    }

    private class SpecListTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return DataRepository.getSpecs();

        }

        @Override
        protected void onPostExecute(ArrayList<String> specs) {
            specList.setValue(specs);
        }
    }

    public void edit(String id) {
        Log.e("Some data",spec.getValue()+"---");
        Doctor d = new Doctor(name.getValue(), specList.getValue().get(spec.getValue()), start.getValue(), end.getValue(), id);
        DataRepository.editDoctor(d);
    }
}