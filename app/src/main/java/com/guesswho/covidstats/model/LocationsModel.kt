package com.guesswho.covidstats.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Maxim on 19.03.20
 */
data class LocationsModel(val country: String, @SerializedName("country_code")val countryCode: String, val latest: Long, val history: Map<String, Long>)