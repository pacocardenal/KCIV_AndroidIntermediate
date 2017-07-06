package io.keepcoding.madridshops.domain.managers.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ShopsNetworkManager {
    void getShopsFromServer(@NonNull final GetAllShopsManagerCompletion completion,
                            @Nullable final ManagerErrorCompletion errorCompletion);
}
