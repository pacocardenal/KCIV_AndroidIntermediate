package io.keepcoding.madridshops.domain.interactors;

public interface GetIfReloadCacheInteractor {
    void execute(Runnable onCacheNeedReload, Runnable onCacheDoNotNeedReload);
}
