package io.keepcoding.madridshops.domain.managers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridshops.domain.model.Activity;

import static io.keepcoding.madridshops.domain.managers.db.DBConstants.ALL_ACTIVITY_COLUMNS;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_ADDRESS;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_DESCRIPTION;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_ID;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_IMAGE_URL;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_LATITUDE;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_LONGITUDE;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_NAME;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_URL;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.TABLE_ACTIVITY;

public class ActivityDAO implements DAOReadable<Activity>, DAOWritable<Activity> {

    private static final long EMPTY_SHOP = 0;
    private SQLiteDatabase dbReadConnection;
    private SQLiteDatabase dbWriteConnection;

    public ActivityDAO(DBHelper dbHelper) {
        dbReadConnection = dbHelper.getReadableDatabase();
        dbWriteConnection = dbHelper.getWritableDatabase();
    }

    public ActivityDAO(Context context) {
        this(new DBHelper(context));
    }

    @Override
    public @Nullable Activity query(long id) {
        String idAsString = String.format("%d", id);
        List<Activity> activities = query(KEY_ACTIVITY_ID + " = ?" + id, new String[]{ idAsString }, KEY_ACTIVITY_ID);

        if (activities == null || activities.size() == 0) {
            return  null;
        }

        return activities.get(0);
    }

    @Override
    public @Nullable List<Activity> query() {
        return query(null, null, KEY_ACTIVITY_ID);
    }

    @Nullable
    @Override
    public List<Activity> query(String where, String[] whereArgs, String orderBy) {

        Cursor c = dbReadConnection.query(TABLE_ACTIVITY,   // table name
                ALL_ACTIVITY_COLUMNS,                       // columns I want to obtain
                where,                                      // where
                whereArgs,                                  // where args
                orderBy,                                    // order by
                null,                                       // group
                null);                                      // having

        if (c == null || c.getCount() == 0) {
            return null;
        }

        List<Activity> activityList = new ArrayList<>();

        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY_ACTIVITY_ID));
            String name = c.getString(c.getColumnIndex(KEY_ACTIVITY_NAME));
            String address = c.getString(c.getColumnIndex(KEY_ACTIVITY_ADDRESS));
            String description = c.getString(c.getColumnIndex(KEY_ACTIVITY_DESCRIPTION));
            String imageUrl = c.getString(c.getColumnIndex(KEY_ACTIVITY_IMAGE_URL));
            String logoImageUrl = c.getString(c.getColumnIndex(KEY_ACTIVITY_LOGO_IMAGE_URL));
            String url = c.getString(c.getColumnIndex(KEY_ACTIVITY_URL));
            float latitude = c.getString(c.getColumnIndex(KEY_ACTIVITY_LATITUDE));
            float longitude = c.getString(c.getColumnIndex(KEY_ACTIVITY_LONGITUDE);

            Activity activity = Activity.of(id, name)
                    .setAddress(address)
                    .setDescription(description)
                    .setImageUrl(imageUrl)
                    .setLogoUrl(logoImageUrl)
                    .setLatitude(latitude)
                    .setLongitude(longitude)
                    .setUrl(url);

            activityList.add(activity);
        }

        return activityList;
    }

    @Override
    public int numRecords() {
        return 0;
    }

    @Override
    public long insert(@NonNull Activity element) {
        if (element == null) {
            return EMPTY_SHOP;
        }

        dbWriteConnection.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = dbWriteConnection.insert(DBConstants.TABLE_ACTIVITY, null, getContentValues(element));
            element.setId(id);

            dbWriteConnection.setTransactionSuccessful();
        } finally {
            dbWriteConnection.endTransaction();
        }

        return id;
    }

    private ContentValues getContentValues(Activity activity) {
        final ContentValues contentValues = new ContentValues();

        if (activity == null) {
            return contentValues;
        }

        contentValues.put(KEY_ACTIVITY_NAME, activity.getName());
        contentValues.put(KEY_ACTIVITY_ADDRESS, activity.getAddress());
        contentValues.put(KEY_ACTIVITY_DESCRIPTION, activity.getDescription());
        contentValues.put(KEY_ACTIVITY_IMAGE_URL, activity.getImageUrl());
        contentValues.put(KEY_ACTIVITY_LOGO_IMAGE_URL, activity.getLogoUrl());
        contentValues.put(KEY_ACTIVITY_URL, activity.getUrl());
        contentValues.put(KEY_ACTIVITY_LATITUDE, activity.getLatitude());
        contentValues.put(KEY_ACTIVITY_LONGITUDE, activity.getLongitude());

        return contentValues;
    }

    @Override
    public long update(long id, Activity element) {
        return 0;
    }

    @Override
    public long delete(long id) {
        return 0;
    }

    @Override
    public long delete(Activity element) {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public long delete(String where, String... whereClause) {
        return 0;
    }
}
