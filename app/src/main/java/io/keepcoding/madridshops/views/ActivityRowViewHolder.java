package io.keepcoding.madridshops.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.model.Activity;

public class ActivityRowViewHolder extends RecyclerView.ViewHolder {

    private TextView activityNameTextView;
    private ImageView activityLogoImageView;

    public ActivityRowViewHolder(View rowActivity) {
        super(rowActivity);

        activityNameTextView = (TextView) rowActivity.findViewById(R.id.row_activity__activity_name);
        activityLogoImageView = (ImageView) rowActivity.findViewById(R.id.row_activity__activity_logo);
    }

    public void setActivity(Activity activity) {
        if (activity == null) {
            return;
        }

        activityNameTextView.setText(activity.getName());
        // TODO: put image here
    }

}
