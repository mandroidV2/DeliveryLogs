package com.app.deliverieslogs.common;

public interface Constants {

    String BASE_URL = "https://mock-api-mobile.dev.lalamove.com";
    String URL_GET_DELIVERIES = BASE_URL + "/deliveries?limit=20";

    // object param
    String DELIVERY_LOG_OBJ_KEY = "delivery_log";

    // shared pref key
    String PREF_DELIVERY_JSON = "PREF_DELIVERY_JSON";
}
