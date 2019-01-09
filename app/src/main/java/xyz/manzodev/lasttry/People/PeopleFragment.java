package xyz.manzodev.lasttry.People;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Dump.DumpData;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentPeopleBinding;

import static xyz.manzodev.lasttry.Utils.Req.TAG;

public class PeopleFragment extends Fragment {
    FragmentPeopleBinding fragmentPeopleBinding;
    ArrayList<Model> models;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: PeopleFragment");
        fragmentPeopleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_people, container, false);

        getData();
        MainAdapter mainAdapter = new MainAdapter(models,getContext());
        fragmentPeopleBinding.rvPeople.setAdapter(mainAdapter);
        fragmentPeopleBinding.rvPeople.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return fragmentPeopleBinding.getRoot();
    }

    void getData(){
        models = DumpData.getModelData();
    }

}
