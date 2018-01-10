package com.ar.simplecommerce.helper;

/**
 * Created by aderifaldi on 2017-12-24.
 */

public class Constant {

    public static final String DB_NAME = "simple-commerce-db";

    public static final int SPLASH_DELAY = 3000;

    public class Cache{
        public static final int PRODUCT = 1;
    }

    public class Api{
        public static final String BASE_URL = "https://api.bukalapak.com/v2/";
        public static final String SUCCESS = "OK";

        public static final int TIMEOUT = 60;
        public static final int LIMIT = 10;
        public static final int OFFSET = 0;
    }

    public class Preference{
        public static final String PREFERENCE_NAME = "simple-commerce-preference";

        public static final String IS_LOGIN = "isLOgin";
        public static final String USER_NAME = "userName";
    }

    public class Test{
        public static final String USERNAME = "android";
        public static final String PASSWORD = "developer";
    }

}
