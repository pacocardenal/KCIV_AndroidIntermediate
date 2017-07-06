package io.keepcoding.madridshops.domain.interactors;

public interface GetAllActivitiesAreCachedInteractor {
    void execute(Runnable onAllActivitiesAreCached, Runnable onAllActivitiesAreNotCached);
}
