package com.ar.simplecommerce.data.cache;

/**
 * Created by RadyaLabs PC on 15/12/2017.
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ar.simplecommerce.helper.Constant;
import com.ar.simplecommerce.model.cache.ModelCache;

@Database(entities = {ModelCache.class}, version = 1, exportSchema = false)
public abstract class CacheDB extends RoomDatabase {

    private static CacheDB INSTANCE;

    public abstract CacheDao cacheDao();

    public static CacheDB getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CacheDB.class, Constant.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}