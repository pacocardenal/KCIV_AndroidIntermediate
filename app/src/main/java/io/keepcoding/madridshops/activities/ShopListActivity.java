package io.keepcoding.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.interactors.GetAllShopsInteractor;
import io.keepcoding.madridshops.domain.interactors.GetAllShopsInteractorCompletion;
import io.keepcoding.madridshops.domain.interactors.GetAllShopsInteractorFakeImpl;
import io.keepcoding.madridshops.domain.interactors.InteractorErrorCompletion;
import io.keepcoding.madridshops.domain.model.Shops;

public class ShopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        // obtain shops list

        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorFakeImpl();
        getAllShopsInteractor.execute(
            new GetAllShopsInteractorCompletion() {
                @Override
                public void completion(Shops shops) {
                    System.out.println("Hello hello");
                }
            },
            new InteractorErrorCompletion() {
                @Override
                public void onError(String errorDescription) {

                }
            }
        );


    }
}
