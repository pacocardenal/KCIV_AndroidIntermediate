package io.keepcoding.madridshops.domain.interactors;

import android.support.annotation.NonNull;

import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;

public class GetAllActivitiesInteractorFakeImpl implements GetAllActivitiesInteractor {
    @Override
    public void execute(@NonNull final GetAllActivitiesInteractorCompletion completion) {
        Activities activities = new Activities();

        for (int i = 0; i < 10; i++) {
            Activity activity = Activity.of(i, "My shop " + i);
            activities.add(activity);
        }

        completion.completion(activities);
    }
}
