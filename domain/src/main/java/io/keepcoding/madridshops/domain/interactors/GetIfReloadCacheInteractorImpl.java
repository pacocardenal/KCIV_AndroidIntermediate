package io.keepcoding.madridshops.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.lang.ref.WeakReference;
import java.util.Date;

public class GetIfReloadCacheInteractorImpl implements GetIfReloadCacheInteractor {

    private WeakReference<Context> context;

    public GetIfReloadCacheInteractorImpl(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public void execute(Runnable onCacheNeedReload, Runnable onCacheDoNotNeedReload) {
        Date date = new Date(System.currentTimeMillis());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.get());
        long dateCached = preferences.getLong(SetActualDateCachedInteractor.DATE_CACHED, date.getTime());

        Date cachedDate = new Date(dateCached);
        DateTime cachedDateTime = new DateTime(cachedDate.getTime());
        DateTime actualDateTime = new DateTime();

        int days = Days.daysBetween(cachedDateTime, actualDateTime).getDays();

        if (days > 7) {
            onCacheNeedReload.run();
        } else {
            onCacheDoNotNeedReload.run();
        }
    }

}
