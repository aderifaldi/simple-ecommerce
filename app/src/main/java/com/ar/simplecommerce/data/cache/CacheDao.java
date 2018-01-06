package com.ar.simplecommerce.data.cache;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ar.simplecommerce.model.cache.ModelCache;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by RadyaLabs PC on 15/12/2017.
 */
@Dao
public interface CacheDao {

    @Query("select * from ModelCache where id = :id")
    ModelCache loadCacheById(int id);

    @Insert(onConflict = IGNORE)
    void insertCache(ModelCache cache);

    @Update(onConflict = REPLACE)
    void updateCache(ModelCache cache);

}
