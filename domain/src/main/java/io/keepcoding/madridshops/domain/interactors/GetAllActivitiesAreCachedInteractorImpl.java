package io.keepcoding.madridshops.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class GetAllActivitiesAreCachedInteractorImpl implements GetAllActivitiesAreCachedInteractor {

    private WeakReference<Context> context;

    public GetAllActivitiesAreCachedInteractorImpl(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public void execute(Runnable onAllActivitiesAreCached, Runnable onAllActivitiesAreNotCached) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.get());
        boolean activitiesSaved = preferences.getBoolean(SetAllActivitiesAreCachedInteractor.ACTIVITIES_SAVED, false);

        if (activitiesSaved) {
            onAllActivitiesAreCached.run();
        } else {
            onAllActivitiesAreNotCached.run();
        }

    }
}
