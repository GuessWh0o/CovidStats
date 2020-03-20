package com.guesswho.covidstats.adapter

import com.guesswho.covidstats.model.ListModel
import java.util.*


/**
 *
 * @author Maxim on 2020-01-29
 */
class FavoriteCaseListAdapter(private val favoriteCases: ArrayList<ListModel>) : CaseListAdapter(favoriteCases) {

    override fun getItemCount() = favoriteCases.size

    override fun selectFavorite(position: Int) {
        super.selectFavorite(position)
        favoriteCases.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getCases(): List<ListModel> {
        return this.favoriteCases
    }
}