package io.keepcoding.madridshops.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesFromCacheInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesFromCacheInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractorCompletion;
import io.keepcoding.madridshops.domain.interactors.GetAllActivitiesInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.InteractorErrorCompletion;
import io.keepcoding.madridshops.domain.interactors.SaveAllActivitiesIntoCacheInteractor;
import io.keepcoding.madridshops.domain.interactors.SaveAllActivitiesIntoCacheInteractorImpl;
import io.keepcoding.madridshops.domain.interactors.SetAllActivitiesAreCachedInteractor;
import io.keepcoding.madridshops.domain.interactors.SetAllActivitiesAreCachedInteractorImpl;
import io.keepcoding.madridshops.domain.managers.cache.GetAllActivitiesFromCacheManager;
import io.keepcoding.madridshops.domain.managers.cache.GetAllActivitiesFromCacheManagerDAOImpl;
import io.keepcoding.madridshops.domain.managers.cache.SaveAllActivitiesIntoCacheManager;
import io.keepcoding.madridshops.domain.managers.cache.SaveAllActivitiesIntoCacheManagerDAOImpl;
import io.keepcoding.madridshops.domain.managers.network.ActivitiesNetworkManager;
import io.keepcoding.madridshops.domain.managers.network.GetAllActivitiesManagerImpl;
import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;
import io.keepcoding.madridshops.fragments.ActivitiesFragment;
import io.keepcoding.madridshops.navigator.Navigator;
import io.keepcoding.madridshops.util.map.MapPinnable;
import io.keepcoding.madridshops.util.map.MapUtil;
import io.keepcoding.madridshops.util.map.model.ActivityPin;
import io.keepcoding.madridshops.views.OnElementClick;

import static io.keepcoding.madridshops.util.map.MapUtil.centerMapInPosition;

public class ActivityListActivity extends AppCompatActivity {

    @BindView(R.id.activity_activity_list__progress_bar) ProgressBar progressBar;

    ActivitiesFragment activitiesFragment;
    private SupportMapFragment mapFragment;
    public GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);
        ButterKnife.bind(this);

        activitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__fragment_activities);

        checkCacheData();
        initializeMap();

    }

    private void checkCacheData() {
        GetAllActivitiesAreCachedInteractor getAllActivitiesAreCachedInteractor = new GetAllActivitiesAreCachedInteractorImpl(this);
        getAllActivitiesAreCachedInteractor.execute(new Runnable() {
            @Override
            public void run() {
                // all cached already, no need to download things, just read from DB
                readDataFromCache();
            }
        }, new Runnable() {
            @Override
            public void run() {
                // nothing cached yet
                obtainActivitiesList();
            }
        });
    }

    private void initializeMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                // check if map is created successfully or not
                if (googleMap == null) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    map = googleMap;
                    checkCacheData();
                    addDataToMap(googleMap);

                }
            }
        });
    }

    public void addDataToMap(GoogleMap map) {

/*        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/

        centerMapInPosition(map, 40.411335, -3.674908);
        map.getUiSettings().setZoomControlsEnabled(true);
/*        map.setMyLocationEnabled(true);*/

        MarkerOptions retiroMarkerOptions = new MarkerOptions()
                .position(new LatLng(40.411335, -3.674908))
                .title("Hello world").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        MarkerOptions retiroMarkerOptions2 = new MarkerOptions()
                .position(new LatLng(40.42, -3.674908))
                .title("Hello world 2").icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_camera));

        Marker marker = map.addMarker(retiroMarkerOptions);
        Marker marker2 = map.addMarker(retiroMarkerOptions2);
    }

    private void readDataFromCache() {
        GetAllActivitiesFromCacheManager getAllActivitiesFromCacheManager = new GetAllActivitiesFromCacheManagerDAOImpl(this);
        GetAllActivitiesFromCacheInteractor getAllActivitiesFromCacheInteractor = new GetAllActivitiesFromCacheInteractorImpl(getAllActivitiesFromCacheManager);
        getAllActivitiesFromCacheInteractor.execute(new GetAllActivitiesInteractorCompletion() {
            @Override
            public void completion(@NonNull Activities activities) {
                configActivitiesFragment(activities);
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

                        configActivitiesFragment(activities);
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

    private void configActivitiesFragment(final Activities activities) {
        activitiesFragment.setActivities(activities);
        activitiesFragment.setOnElementClickListener(new OnElementClick<Activity>() {
            @Override
            public void clickedOn(@NonNull Activity element, int position) {
                // TODO: finish
                Navigator.navigateFromActivityListActivityToActivityDetailActivity(ActivityListActivity.this, element, position);
            }
        });
        
        putActivityPinsOnMap(activities);
    }

    private void putActivityPinsOnMap(Activities activities) {
        List<MapPinnable> activityPins = ActivityPin.activityPinsFromActivities(activities);
        MapUtil.addPins(activityPins, map, this);
    }
}
