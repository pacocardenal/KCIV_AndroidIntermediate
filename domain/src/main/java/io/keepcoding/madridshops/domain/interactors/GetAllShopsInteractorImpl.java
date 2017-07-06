package io.keepcoding.madridshops.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import io.keepcoding.madridshops.domain.managers.network.GetAllShopsManagerCompletion;
import io.keepcoding.madridshops.domain.managers.network.ManagerErrorCompletion;
import io.keepcoding.madridshops.domain.managers.network.ShopsNetworkManager;
import io.keepcoding.madridshops.domain.managers.network.entities.ShopEntity;
import io.keepcoding.madridshops.domain.managers.network.mappers.ShopEntityIntoShopsMapper;
import io.keepcoding.madridshops.domain.model.Shops;

public class GetAllShopsInteractorImpl implements GetAllShopsInteractor {
    private ShopsNetworkManager shopsNetworkManager;

    public GetAllShopsInteractorImpl(@NonNull final ShopsNetworkManager shopsNetworkManager) {
        this.shopsNetworkManager = shopsNetworkManager;
    }

    @Override
    public void execute(@NonNull final GetAllShopsInteractorCompletion completion, @Nullable final InteractorErrorCompletion onError) {
        if (this.shopsNetworkManager == null) {
            if (onError == null) {
                throw new IllegalStateException("Network manager can't be null");
            } else {
                onError.onError("");
            }
        }

        this.shopsNetworkManager.getShopsFromServer(new GetAllShopsManagerCompletion() {
            @Override
            public void completion(@NonNull List<ShopEntity> shopEntities) {
                Log.d("SHOP", shopEntities.toString());
                if (completion != null) {
                    Shops shops = ShopEntityIntoShopsMapper.map(shopEntities);
                    completion.completion(shops);
                }
            }
        }, new ManagerErrorCompletion() {
            @Override
            public void onError(String errorDescription) {
                if (onError != null) {
                    onError.onError(errorDescription);
                }
            }
        });
    }
}
