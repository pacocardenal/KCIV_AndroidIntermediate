package io.keepcoding.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.keepcoding.madridshops.R;
import io.keepcoding.madridshops.domain.model.Shop;

public class ShopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Shop.of(1, "Mi tienda").
                setAddress("C/ kjld").
                setLatitude(10).
                setLongitude(10);

    }
}
