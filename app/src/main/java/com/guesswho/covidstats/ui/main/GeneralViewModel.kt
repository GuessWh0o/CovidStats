package com.guesswho.covidstats.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guesswho.covidstats.extensions.getDataByCountries
import com.guesswho.covidstats.networking.Repository
import com.guesswho.covidstats.networking.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class GeneralViewModel : ViewModel() {

    val states: MutableLiveData<ViewState> = MutableLiveData(ViewState.EMPTY)
    private val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun fetchData() {
        states.value = ViewState.LOADING
        scope.launch {
            val allData = RetrofitClient.api.getAll()
            val list = allData.getDataByCountries()
            Repository.listByCountry.addAll(list)
            Repository.restoreFavoriteList()
            states.postValue(ViewState.LOADED)
            Repository.casesList.postValue(allData)
        }
    }
}