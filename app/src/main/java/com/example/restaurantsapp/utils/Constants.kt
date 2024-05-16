package com.example.restaurantsapp.utils

object Constants {
    const val IMAGE_SIZE = "800x600"
    const val API_URL = "https://api.foursquare.com/"
    const val HEADER_ACCEPT = "accept"
    const val HEADER_ACCEPT_VALUE = "application/json"
    const val HEADER_AUTH = "Authorization"
    const val HEADER_AUTH_VALUE = "fsq3DfAdKRH0ofX2LB45qJ4i+xPLMtWliU2dVKV1G9Oa5tU="
}

object Query{
    const val PLACE = "restaurants"
    const val DISTANCE = "DISTANCE"
    const val RESTAURANT_LIST_FIELDS = "fsq_id,name,closed_bucket"
    const val RESTAURANT_DETAILS_FIELDS = "name,closed_bucket,geocodes,location"
}
