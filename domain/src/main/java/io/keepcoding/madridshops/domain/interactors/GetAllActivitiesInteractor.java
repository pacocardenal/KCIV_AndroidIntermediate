package io.keepcoding.madridshops.domain.interactors;

import android.support.annotation.NonNull;

public interface GetAllActivitiesInteractor {
    public void execute(@NonNull final GetAllActivitiesInteractorCompletion completion);
}
