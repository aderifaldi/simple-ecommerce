package com.ar.simplecommerce.data.cache;

import com.ar.simplecommerce.model.cache.ModelCache;

/**
 * Created by RadyaLabs PC on 12/12/2017.
 */

public class CacheManager {

    public static void storeCache(CacheDB cacheDB, int cacheType, String data) {
        ModelCache cached = cacheDB.cacheDao().loadCacheById(cacheType);
        ModelCache cache = new ModelCache();

        if (cached == null){
            cache.id = cacheType;
            cache.json = "";
            cacheDB.cacheDao().insertCache(cache);
        }else {
            cache.id = cacheType;
            cache.json = data;
            cacheDB.cacheDao().updateCache(cache);
        }

    }

}
