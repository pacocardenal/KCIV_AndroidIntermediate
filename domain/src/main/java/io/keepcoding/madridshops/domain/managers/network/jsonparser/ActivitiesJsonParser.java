package io.keepcoding.madridshops.domain.managers.network.jsonparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import io.keepcoding.madridshops.domain.managers.network.entities.ActivitiesResponseEntity;
import io.keepcoding.madridshops.domain.managers.network.entities.ActivityEntity;

public class ActivitiesJsonParser {
    public List<ActivityEntity> parse(String response) {
        if (response == null) {
            return  null;
        }

        List<ActivityEntity> activityEntities = null;

        try {
            Gson gson = new GsonBuilder().create();

            Reader reader = new StringReader(response);
            ActivitiesResponseEntity activitiesResponseEntity = gson.fromJson(reader, ActivitiesResponseEntity.class);
            activityEntities = activitiesResponseEntity.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activityEntities;
    }
}
