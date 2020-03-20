package com.guesswho.covidstats.model

/**
 *
 * @author Maxim on 19.03.20
 */
class ListModel(val countryName: String, val countryCode: String,
                val confirmedCases: Long, val newConfirmedCases: Long,
                val deaths: Long, val newDeaths: Long,
                val recovered: Long, val newRecovered: Long, var isFavorite: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        if (other is ListModel) {
            return (this.countryName == other.countryName && this.countryCode == other.countryCode)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = countryName.hashCode()
        result = 31 * result + countryCode.hashCode()
        return result
    }
}