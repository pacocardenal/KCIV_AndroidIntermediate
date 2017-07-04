package io.keepcoding.madridshops.domain;

import org.junit.Test;

import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;

import static org.junit.Assert.*;

public class ActivitiesUnitTests {
    @Test
    public void after_creation_activities_size_is_zero() throws  Exception {
        Activities sut = new Activities();

        assertEquals(0, sut.size());
    }

    @Test
    public void activities_adding_one_activity_size_is_one() throws Exception {
        Activities sut = new Activities();

        sut.add(Activity.of(1, "My activity"));

        assertEquals(1, sut.size());
    }

    @Test
    public void activities_adding_one_activity_and_deleting_size_is_zero() throws Exception {
        Activities sut = new Activities();

        Activity activity = Activity.of(1, "My activity");
        sut.add(activity);
        sut.delete(activity);

        assertEquals(0, sut.size());
    }

    @Test
    public void activity_adding_one_activity_and_getting_returns_that_activity() throws Exception {
        Activities sut = new Activities();

        Activity activity = Activity.of(1, "My activity");
        sut.add(activity);
        Activity activity1 = sut.get(0);

        assertEquals(activity.getId(), activity1.getId());
        assertEquals(activity.getName(), activity1.getName());
    }

    @Test
    public void activities_adding_several_activities_return_all_activities() throws Exception {
        Activities sut = new Activities();

        for (int i = 0; i < 10; i++) {
            Activity activity = Activity.of(i, "My shop " + i);
            sut.add(activity);
        }

        assertEquals(10, sut.size());
        assertEquals(10, sut.allActivities().size());
        assertEquals(0, sut.allActivities().get(0).getId());
        assertEquals("My shop 0", sut.allActivities().get(0).getName());
    }
}
