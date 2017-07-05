package io.keepcoding.madridshops.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.adapters.ActivitiesAdapter;
import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;
import io.keepcoding.madridshops.views.OnElementClick;

public class ActivitiesFragment extends Fragment {

    private RecyclerView activitiesRecyclerView;
    private ActivitiesAdapter adapter;
    private Activities activities;

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        activitiesRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_activities__recycler_view);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;

        adapter = new ActivitiesAdapter(activities, getActivity());
        activitiesRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new OnElementClick<Activity>() {
            @Override
            public void clickedOn(@NonNull Activity activity, int position) {
                Log.d("Click ", activity.getName());
            }
        });
    }

}
