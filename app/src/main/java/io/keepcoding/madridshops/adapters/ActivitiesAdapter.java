package io.keepcoding.madridshops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;
import io.keepcoding.madridshops.views.ActivityRowViewHolder;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityRowViewHolder> {
    private Activities activities;
    private LayoutInflater inflater;

    public ActivitiesAdapter(final Activities activities, final Context context) {
        this.activities = activities;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_activity, parent, false);

        ActivityRowViewHolder viewHolder = new ActivityRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActivityRowViewHolder activityRow, int position) {
        Activity activity = this.activities.get(position);
        activityRow.setActivity(activity);
    }

    @Override
    public int getItemCount() {
        if (this.activities != null) {
            return (int) this.activities.size();
        }
        return 0;
    }
}
