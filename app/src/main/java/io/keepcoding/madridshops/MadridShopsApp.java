package io.keepcoding.madridshops;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.squareup.picasso.Picasso;


public class MadridShopsApp extends MultiDexApplication {

    public static final String APP_NAME = MadridShopsApp.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();

        // init app

        Log.d(APP_NAME, "App starting " + BuildConfig.BASE_URL);

        // Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);

/*        ShopService.startRunningService(this);

        Resources resources = getResources();
        Intent intent = new Intent(this, ShopListActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Ticker")    // Set the "ticker" text which is sent to accessibility services.
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle("You're spyed now")
                .setContentText("Notification Text")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 0;
        notificationManager.notify(notificationId, notification);*/

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // low memory: dump something
    }
}
