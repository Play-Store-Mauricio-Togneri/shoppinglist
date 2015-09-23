package com.mauriciotogneri.common.wearable;

public class WearableApi
{
    public static final class Paths
    {
        public static final String GET_FAVORITE_STOPS = "/get_favorite_stops";
        public static final String RESULT_FAVORITE_STOPS = "/result_favorite_stops";

        public static final String GET_DEPARTURES = "/get_departures";
        public static final String RESULT_DEPARTURES = "/result_departures";

        public static final String GET_TRIP = "/get_trip";
        public static final String RESULT_TRIP = "/result_trip";

        public static final String INCREASE_STOP_HIT_COUNT = "/increase_stop_hint_count";
    }

    public static final class Messages
    {
        public static Message getFavoriteStops(String nodeId)
        {
            return new Message(nodeId, Paths.GET_FAVORITE_STOPS);
        }

        //        public static Message resultFavoriteStops(String nodeId, List<Stop> stops)
        //        {
        //            return new Message(nodeId, Paths.RESULT_FAVORITE_STOPS, JsonUtils.toJson(stops));
        //        }
    }
}