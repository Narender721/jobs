package com.tesco.AccessManager_v2.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UnitPaths{

        public static final String BASEPATH_UNIT = "/unit";

        public static final String GETUNITS = "/unit/getUnits";

        public static final String UNIT_ID = "/unit/getUnit/{unit_Id}";

        public static final String ADDUNIT = "/unit/addUnit";

        public static final String UPDATE = "/unit/update";

        public static final String DELETE = "/unit/delete/{unit_Id}";

        public static final String DATA_NULL = "{\"unit_Id\":\"0\",\"unit_Name\":\"Store 0\"}";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UserPaths{

        public static final String BASEPATH_USER = "/user";

        public static final String GET_USERS = "/user/getUsers";

        public static final String USER_ID = "/user/getUser/{user_Id}";

        public static final String ADD_USER = "/user/addUser";

        public static final String UPDATE_USER = "/user/update";

        public static final String DELETE_USER = "/user/delete/{user_Id}";

        public static final String GET_USER_BYUNIT = "/user/getUsersByUnit/{unit_Id}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class LocationPaths{

        public static final String GET_LOCATION = "/location";
        public static final String BEARER_BODYJSON = "{\"grant_type\":\"client_credentials\",\"client_id\":\"\",\"client_secret\":\"\"}";
        public static final String BEARER_URI = "https://api-ppe.tesco.com/identity/v4/issue-token/token";
        public static final String LOCATIONAPI_URI = "https://api-ppe.tesco.com/tescolocation/v4/locations?fields=lifecycle&limit=100&offset=200";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class KafkaPaths{

        public static final String SEND_KAKFA = "/send";
        public static final String RECEIVE_KAKFA = "/receive";

    }
}
