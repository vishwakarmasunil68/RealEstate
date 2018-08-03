package com.appentus.realestate.webservice;

/**
 * Created by sunil on 20-01-2017.
 */

public class WebServicesUrls {

    public static final String BASE_URL = "http://appentus.me/aqar/api/";
//    public static final String BASE_URL = "http://192.168.0.105/appentus/aqar/index.php/api/";

    public static final String LOGIN_URL = BASE_URL + "api/login";
    public static final String CATEGORY_URL = BASE_URL + "api/property_category";
    public static final String SIZE_URL = BASE_URL + "api/size_list";
    public static final String PRICE_URL = BASE_URL + "api/price_list";
    public static final String SHOW_URL = BASE_URL + "api/show_property";
    public static final String ADD_PROPERTY_URL = BASE_URL + "api/add_property";
    public static final String AMNIETIES= BASE_URL + "api/amenities_list";
    public static final String MANAGE_PROPERTY_URL= BASE_URL + "api/manage_property";
    public static final String GET_PROPERTY= BASE_URL + "api/get_property";
    public static final String UPDATE_PROFILE= BASE_URL + "api/update_profile";
    public static final String SEND_CHAT= BASE_URL + "chat/insert_chat";
    public static final String CHAT_USERS= BASE_URL + "chat/get_chat_user";
    public static final String GET_ALL_CHATS= BASE_URL + "chat/get_user_chat";
    public static final String ADD_TO_FAV= BASE_URL + "api/add_fav";
    public static final String REMOVE_FROM_FAV= BASE_URL + "api/remove_fav";
    public static final String GET_FAV_LIST= BASE_URL + "api/get_fav";
    public static final String UPLOAD_BANNER= BASE_URL + "api/upload_banner";

    public static String getLocationAPI(String key,String lat,String longitude,String keywork){
        String url="https://maps.googleapis.com/maps/api/place/autocomplete/json?location="+lat+","+longitude+"&radius=50000&input="+keywork+"&key="+key;
        return url;
    }


    public static String paymentUrl(String plan_id,String user_id,String plan_name,String amount){
        return "http://appentus.me/aqar/api/api/payment_view?plan_id="+plan_id+"&user_id="+user_id+"&plan_name=rnk&amount="+amount;
    }

}
