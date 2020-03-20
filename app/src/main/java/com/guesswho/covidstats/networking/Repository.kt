package com.guesswho.covidstats.networking

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.guesswho.covidstats.model.CasesResponse
import com.guesswho.covidstats.model.ListModel


/**
 *
 * @author Maxim on 19.03.20
 */
object Repository {
    private const val KEY_FAV = "FAVORITE_LIST"
    private var pref: SharedPreferences? = null

    fun init(context: Context) {
        this.pref = context.applicationContext
            .getSharedPreferences("CoronaPrefs", Context.MODE_PRIVATE)
    }

    fun restoreFavoriteList() {
        pref?.getStringSet(KEY_FAV, LinkedHashSet<String>())?.let { favorites ->
            favoriteList.clear()
            Log.i("restoreFavoriteList","Favorites = { $favorites }")
            if (favorites.isNotEmpty()) {
                val filteredList = listByCountry.filter { favorites.contains(it.countryCode) }.toSet().toList()
                filteredList.forEach { it.isFavorite = true }
                Log.i("restoreFavoriteList","favorite filteredList = { $filteredList }")
                favoriteList.addAll(filteredList)
                favoriteList.forEach { favorite ->
                    listByCountry.find { it.countryCode == favorite.countryCode }?.isFavorite = true
                }
            }
        }
    }

    fun favoriteChange(isFavorite: Boolean, countryCode: String) {
        pref?.let { prefs ->
            prefs.getStringSet(KEY_FAV, LinkedHashSet<String>())?.let {
                val newSet = it
                Log.i("favoriteChange","$countryCode Is Favorite $isFavorite")
                if (isFavorite) {
                    val added = newSet.add(countryCode)
                    Log.i("favoriteChange","$countryCode New Favorite Added $added")
                } else {
                    val removed = newSet.remove(countryCode)
                    Log.i("favoriteChange","$countryCode New Favorite Removed $removed")
                }
                prefs.edit()?.putStringSet(KEY_FAV, newSet)?.apply()
                restoreFavoriteList()
            }
        }
    }


    val casesList = MutableLiveData<CasesResponse>()
    val listByCountry = ArrayList<ListModel>()
    val favoriteList = ArrayList<ListModel>()
}