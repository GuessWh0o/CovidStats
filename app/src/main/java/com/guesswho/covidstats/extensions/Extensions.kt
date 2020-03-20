package com.guesswho.covidstats.extensions

import com.guesswho.covidstats.model.CasesResponse
import com.guesswho.covidstats.model.ListModel
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author Maxim on 2020-01-30
 */

fun CasesResponse.getDataByCountries(): List<ListModel> {
    val confirmed: MutableList<ListModel> = ArrayList()

    val today = Date(System.currentTimeMillis())
    val dateFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.GERMANY)

    fun getTotalConfirmed(countryCode: String) : Pair<Long, Long> {
        val filteredList =  this.confirmed.locations.filter { it.countryCode == countryCode }
        val total = filteredList.sumByDouble { it.latest.toDouble() }.toLong()
        var totalNew = 0L
//        val new = filteredList.forEach {
//            it.history.entries.forEach {entry ->
//                try {
//                    val parsedDate = dateFormat.parse(entry.key)
//                    if(parsedDate != null) {
//                        if(dateFormat.format(parsedDate) == dateFormat.format(today)) {
//                            totalNew += entry.value
//                        }
//                    }
//                } catch (e: Exception) {
//                    //Do Nothing
//                }
//            }
//
//        }
        return Pair(total, totalNew)
    }

    fun getTotalDeath(countryCode: String) : Long {
        return deaths.locations.filter { it.countryCode == countryCode }.sumByDouble {
            it.latest.toDouble()
        }.toLong()
    }

    fun getTotalRecover(countryCode: String) : Long {
        return recovered.locations.filter { it.countryCode == countryCode }.sumByDouble {
            it.latest.toDouble()
        }.toLong()
    }


    this.confirmed.locations.forEach {
        val countryCode = it.countryCode
        val totalConfirmedForThisCountry = getTotalConfirmed(countryCode)
        val totalDeathForThisCountry = getTotalDeath(countryCode)
        val totalRecoveredForThisCountry = getTotalRecover(countryCode)
        confirmed.add(ListModel(countryName = it.country, countryCode = it.countryCode,
            confirmedCases = totalConfirmedForThisCountry.first, newConfirmedCases = totalConfirmedForThisCountry.second,
            deaths = totalDeathForThisCountry, newDeaths = 0,
            recovered = totalRecoveredForThisCountry, newRecovered = 0))
    }

    return confirmed.sortedBy { it.countryName }.toSet().toList()
}