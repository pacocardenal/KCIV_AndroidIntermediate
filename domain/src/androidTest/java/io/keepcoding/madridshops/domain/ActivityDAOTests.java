package io.keepcoding.madridshops.domain;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.keepcoding.madridshops.domain.managers.db.ActivityDAO;
import io.keepcoding.madridshops.domain.model.Activity;

import static org.junit.Assert.assertNotNull;
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

    @Test
    public void given_inserted_activities_DAO_queries_all_activities() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        ActivityDAO sut = new ActivityDAO(appContext);

        insertActivity(sut, 1, "Activity test", "address", 10, 11);

        List<Activity> activities = sut.query();

        assertNotNull(activities);
        assertTrue(activities.size() >= 1);
    }

    private Activity insertActivity(ActivityDAO sut, long id, String name, String address, float latitude, float longitude) {
        Activity activity = Activity.of(id, name)
                .setAddress(address)
                .setLatitude(latitude)
                .setLongitude(longitude);

        long insertedId = sut.insert(activity);
        return activity;
    }

    private void insertActivities() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        ActivityDAO sut = new ActivityDAO(appContext);
        for (int i = 0; i <10; i++) {
            insertActivity(sut, i, "Shop " + i, "Address " + i, i + 1, i);
        }
    }
}
