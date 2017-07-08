package io.keepcoding.madridshops.domain.interactors;

import io.keepcoding.madridshops.domain.managers.cache.ClearCacheManager;

public class ClearCacheInteractorImpl implements ClearCacheInteractor {
    private ClearCacheManager manager;

    public ClearCacheInteractorImpl(ClearCacheManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(Runnable completion) {
        // TODO: Next time the App is started, if more than 7 days has passed the cache will be invalidated (deleted) and everything will be...
        manager.execute(completion);
    }
}
