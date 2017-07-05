package io.keepcoding.madridshops.domain.managers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.keepcoding.madridshops.domain.model.Activity;

import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_ADDRESS;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_DESCRIPTION;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_IMAGE_URL;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_LATITUDE;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_LONGITUDE;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_NAME;
import static io.keepcoding.madridshops.domain.managers.db.DBConstants.KEY_ACTIVITY_URL;

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
    public Activity query(long id) {
        return null;
    }

    @Override
    public List<Activity> query() {
        return null;
    }

    @Nullable
    @Override
    public List<Activity> query(String where, String[] whereArgs, String orderBy) {
        return null;
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
