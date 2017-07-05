package io.keepcoding.madridshops.domain;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.keepcoding.madridshops.domain.managers.db.ActivityDAO;
import io.keepcoding.madridshops.domain.model.Activity;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ActivityDAOTests {
    @Test
    public void given_activity_DAO_inserts_activity() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        ActivityDAO sut = new ActivityDAO(appContext);
        Activity activity = Activity.of(1, "Activity test")
                .setAddress("address")
                .setLatitude(10)
                .setLongitude(11);

        long id = sut.insert(activity);
        assertTrue(id > 0);
    }
}
