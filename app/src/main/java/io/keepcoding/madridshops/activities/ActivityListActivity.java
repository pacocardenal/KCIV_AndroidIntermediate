package io.keepcoding.madridshops.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractorCompletion;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractorFakeImpl;
import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;

public class ActivityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);

        // Obtain activities list
        GetAllActivitiesInteractor getAllActivitiesInteractor = new GetAllActivitiesInteractorFakeImpl();
        getAllActivitiesInteractor.execute(new GetAllActivitiesInteractorCompletion() {
            @Override
            public void completion(Activities activities) {
                System.out.println("Hello hello");
            }
        });
    }
}
