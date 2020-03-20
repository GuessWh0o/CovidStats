package com.guesswho.covidstats.ui.main.pager_elements

import androidx.lifecycle.Observer
import com.guesswho.covidstats.adapter.CaseListAdapter
import com.guesswho.covidstats.adapter.FavoriteCaseListAdapter
import com.guesswho.covidstats.networking.Repository

class FavoritesListFragment : ListFragment() {

    override val adapter = FavoriteCaseListAdapter(arrayListOf())

    override fun registerDataListener() {
        Repository.casesList.observe(viewLifecycleOwner, Observer {
            adapter.update(Repository.favoriteList)
        })
    }

    override fun doOnResume() {
        adapter.update(Repository.favoriteList)
    }

    override fun getCasesAdapter(): CaseListAdapter {
        return this.adapter
    }
}