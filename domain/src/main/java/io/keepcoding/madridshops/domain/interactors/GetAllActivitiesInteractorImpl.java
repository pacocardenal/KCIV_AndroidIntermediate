package io.keepcoding.madridshops.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import io.keepcoding.madridshops.domain.managers.network.ActivitiesNetworkManager;
import io.keepcoding.madridshops.domain.managers.network.GetAllActivitiesManagerCompletion;
import io.keepcoding.madridshops.domain.managers.network.ManagerErrorCompletion;
import io.keepcoding.madridshops.domain.managers.network.entities.ActivityEntity;

public class GetAllActivitiesInteractorImpl implements GetAllActivitiesInteractor {

    private ActivitiesNetworkManager networkManager;

    public GetAllActivitiesInteractorImpl(@NonNull final ActivitiesNetworkManager networkManager) {
        this.networkManager = networkManager;
    }
    
    @Override
    public void execute(@NonNull GetAllActivitiesInteractorCompletion completion, @Nullable InteractorErrorCompletion onError) {
        if (this.networkManager == null) {
            if (onError == null) {
                throw new IllegalStateException("Network manager can't be null");
            } else {
                onError.onError("");
            }
        }
        this.networkManager.getActivitiesFromServer(new GetAllActivitiesManagerCompletion() {
            @Override
            public void completion(@NonNull List<ActivityEntity> activityEntities) {
                Log.d("ACTIVITY", activityEntities.toString());
            }
        }, new ManagerErrorCompletion() {
            @Override
            public void onError(String errorDescription) {

            }
        });

    }

}
