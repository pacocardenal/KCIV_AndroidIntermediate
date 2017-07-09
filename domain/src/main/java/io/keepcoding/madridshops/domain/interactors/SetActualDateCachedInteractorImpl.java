package io.keepcoding.madridshops.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SetActualDateCachedInteractorImpl implements SetActualDateCachedInteractor {
    private WeakReference<Context> context;

    public SetActualDateCachedInteractorImpl(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public void execute(long dateCached) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.get());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(SetActualDateCachedInteractorImpl.DATE_CACHED, dateCached);

        editor.commit();
    }
}