package io.keepcoding.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractorCompletion;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.GetIfReloadCacheInteractor;
import io.keepcoding.madridshops.domain.interactors.GetIfReloadCacheInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.InteractorErrorCompletion;
import io.keepcoding.madridshops.domain.interactors.SaveAllActivitiesIntoCacheInteractor;
import io.keepcoding.madridshops.domain.interactors.SaveAllActivitiesIntoCacheInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.SetAllActivitiesAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.SetAllActivitiesAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.SetAllShopsAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.SetAllShopsAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.managers.cache.ClearCacheManager;
import io.keepcoding.madridshops.domain.managers.cache.ClearCacheManagerDAOImpl;
import io.keepcoding.madridshops.domain.managers.cache.SaveAllActivitiesIntoCacheManager;
import io.keepcoding.madridshops.domain.managers.cache.SaveAllActivitiesIntoCacheManagerDAOImpl;
import io.keepcoding.madridshops.domain.managers.network.ActivitiesNetworkManager;
import io.keepcoding.madridshops.domain.managers.network.GetAllActivitiesManagerImpl;
import io.keepcoding.madridshops.domain.model.Activities;
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
        } else {
            // TODO: Cache data
            checkCacheData();
        }
    }

    private void checkCacheData() {
        GetAllActivitiesAreCachedInteractor getAllActivitiesAreCachedInteractor = new GetAllActivitiesAreCachedInteractorImpl(this);
        getAllActivitiesAreCachedInteractor.execute(new Runnable() {
            @Override
            public void run() {
                // Check the 7 days rule
                checkIfCacheNeedsToReload();
            }
        }, new Runnable() {
            @Override
            public void run() {
                obtainActivitiesList();
            }
        });
    }

    private void checkIfCacheNeedsToReload() {
        GetIfReloadCacheInteractor getIfReloadCacheInteractor = new GetIfReloadCacheInteractorImpl(this);
        getIfReloadCacheInteractor.execute(new Runnable() {
            @Override
            public void run() {
                obtainActivitiesList();
            }
        }, new Runnable() {
            @Override
            public void run() {
                // Nothing to do
            }
        });
    }

    private void obtainActivitiesList() {
        progressBar.setVisibility(View.VISIBLE);

        ActivitiesNetworkManager manager = new GetAllActivitiesManagerImpl(this);
        GetAllActivitiesInteractor getAllActivitiesInteractor = new GetAllActivitiesInteractorImpl(manager);
        getAllActivitiesInteractor.execute(
                new GetAllActivitiesInteractorCompletion() {
                    @Override
                    public void completion(Activities activities) {
                        SaveAllActivitiesIntoCacheManager saveManager = new SaveAllActivitiesIntoCacheManagerDAOImpl(getBaseContext());
                        SaveAllActivitiesIntoCacheInteractor saveInteractor = new SaveAllActivitiesIntoCacheInteractorImpl(saveManager);
                        saveInteractor.execute(activities, new Runnable() {
                            @Override
                            public void run() {
                                SetAllActivitiesAreCachedInteractor setAllActivitiesAreCachedInteractor = new SetAllActivitiesAreCachedInteractorImpl(getBaseContext());
                                setAllActivitiesAreCachedInteractor.execute(true);
                            }
                        });
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                },
                new InteractorErrorCompletion() {
                    @Override
                    public void onError(String errorDescription) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
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
        progressBar.setVisibility(View.VISIBLE);
        ClearCacheManager clearCacheManager = new ClearCacheManagerDAOImpl(this);
        ClearCacheInteractor clearCacheInteractor = new ClearCacheInteractorImpl(clearCacheManager);
        clearCacheInteractor.execute(new Runnable() {
            @Override
            public void run() {
                SetAllShopsAreCachedInteractor setAllShopsAreCachedInteractor = new SetAllShopsAreCachedInteractorImpl(getBaseContext());
                setAllShopsAreCachedInteractor.execute(false);
                SetAllActivitiesAreCachedInteractor setAllActivitiesAreCachedInteractor = new SetAllActivitiesAreCachedInteractorImpl(getBaseContext());
                setAllActivitiesAreCachedInteractor.execute(false);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),
                        "Cache cleared!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
