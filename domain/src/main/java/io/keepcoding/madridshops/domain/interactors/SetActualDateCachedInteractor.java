package io.keepcoding.madridshops.domain.interactors;

public interface SetActualDateCachedInteractor {
    public static final String DATE_CACHED = "DATE_CACHED";

    void execute(long dateCached);
}
