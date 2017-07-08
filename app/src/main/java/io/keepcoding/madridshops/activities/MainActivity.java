package io.keepcoding.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.interactors.ClearCacheInteractor;
import io.keepcoding.madridshops.domain.interactors.ClearCacheInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.SetAllActivitiesAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.SetAllActivitiesAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.SetAllShopsAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.SetAllShopsAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.managers.cache.ClearCacheManager;
import io.keepcoding.madridshops.domain.managers.cache.ClearCacheManagerDAOImpl;
import io.keepcoding.madridshops.navigator.Navigator;
import io.keepcoding.madridshops.util.Internet;
import io.keepcoding.madridshops.util.MainThread;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main__shops_button) Button shopsButton;
    @BindView(R.id.activity_main__activities_button) Button activitiesButton;
    @BindView(R.id.activity_main__progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        shopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.class.getCanonicalName(),"Hello");

                Navigator.navigateFromMainActivityToShopListActivity(MainActivity.this);
            }
        });

        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.class.getCanonicalName(),"Hello activities");

                Navigator.navigateFromMainActivityToActivitiesListActivity(MainActivity.this);
            }
        });

        if (!Internet.isInternetAvailable()) {
            showWithoutInternetAlert();
        }


    }

    private void showWithoutInternetAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.without_internet_connection));
        alert.setCancelable(false);
        alert.setMessage(getString(R.string.without_internet_connection_message));
        alert.show();
    }

    private void launchInBackgroundThread() {

        // onPreexecute

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() { // doInBackground
                Log.d("Hilo", Thread.currentThread().getName());
                final String s = testMultithread();

                // going to main thread, method 1
                // onPostExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shopsButton.setText(s);
                    }
                });

                // method 2

                MainThread.run(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

        thread.start();

    }

    private String testMultithread() {
        final String web = "http://freegeoip.net/json/";
        String result = null;
        try {
            URL url = new URL(web);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            InputStream is = (InputStream) request.getContent();
            result = streamToString(is);
            Log.d("Web", result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    String streamToString(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        for(String line = br.readLine(); line != null; line = br.readLine())
            out.append(line);
        br.close();
        return out.toString();
    }

    @OnClick(R.id.activity_main__clear_cache_button) void clearCache() {
        ClearCacheManager clearCacheManager = new ClearCacheManagerDAOImpl(this);
        ClearCacheInteractor clearCacheInteractor = new ClearCacheInteractorImpl(clearCacheManager);
        clearCacheInteractor.execute(new Runnable() {
            @Override
            public void run() {
                SetAllShopsAreCachedInteractor setAllShopsAreCachedInteractor = new SetAllShopsAreCachedInteractorImpl(getBaseContext());
                setAllShopsAreCachedInteractor.execute(false);
                SetAllActivitiesAreCachedInteractor setAllActivitiesAreCachedInteractor = new SetAllActivitiesAreCachedInteractorImpl(getBaseContext());
                setAllActivitiesAreCachedInteractor.execute(false);
            }
        });
    }
}
