package io.keepcoding.madridshops.domain.managers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.keepcoding.madridshops.domain.model.Activity;

public class ActivityDAO implements DAOReadable<Activity>, DAOWritable<Activity> {

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
        return 0;
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
