package io.keepcoding.madridshops.domain.managers.network.mappers;

import java.util.List;

import io.keepcoding.madridshops.domain.managers.network.entities.ActivityEntity;
import io.keepcoding.madridshops.domain.model.Activities;
import io.keepcoding.madridshops.domain.model.Activity;

public class ActivityEntityIntoActivitiesMapper {

    /**
     *
     * @param activityEntities
     * @return null is activityEntities is null or activityEntities is empty else a Activities aggregate
     */
    public static Activities map(List<ActivityEntity> activityEntities) {
        Activities activities = new Activities();

        for (ActivityEntity activityEntity : activityEntities ) {
            Activity activity = Activity.of(activityEntity.getId(), activityEntity.getName());

            activity.setDescription(activityEntity.getDescription_es());
            activity.setLatitude(ShopEntityIntoShopsMapper.getCorrectCoordinateComponent(activityEntity.getGps_lat()));
            activity.setLongitude(ShopEntityIntoShopsMapper.getCorrectCoordinateComponent(activityEntity.getGps_lon()));
            activity.setAddress(activityEntity.getAddress());
            activity.setUrl(activityEntity.getUrl());
            activity.setImageUrl(activityEntity.getImg());
            activity.setLogoUrl(activityEntity.getLogo_img());

            activities.add(activity);
        }

        return activities;
    }

}
