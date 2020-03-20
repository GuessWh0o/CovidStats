package com.guesswho.covidstats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guesswho.covidstats.R
import com.guesswho.covidstats.model.ListModel
import com.guesswho.covidstats.networking.Repository
import com.guesswho.covidstats.ui.main.WebActivity
import kotlinx.android.synthetic.main.rate_item.view.*
import java.util.*


/**
 *
 * @author Maxim on 2020-01-29
 */
open class CaseListAdapter(private val cases: ArrayList<ListModel>) :
    RecyclerView.Adapter<CaseListAdapter.RateViewHolder>(), IGetCases {

    fun update(newCurrencies: List<ListModel>) {
        cases.clear()
        cases.addAll(newCurrencies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = RateViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.rate_item, parent, false)
    )

    override fun getItemCount() = cases.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val currentCase = cases[position]

        holder.itemView.apply {
            rootView.tv_country_name.text = currentCase.countryName
            rootView.tv_country_confirmed.text = currentCase.confirmedCases.toString()
            rootView.tv_country_death.text = currentCase.deaths.toString()
            rootView.tv_country_recovered.text = currentCase.recovered.toString()

            rootView.btn_favorite.background = if (currentCase.isFavorite) resources.getDrawable(
                R.drawable.ic_favorite_selected,
                null
            ) else resources.getDrawable(R.drawable.ic_favorite_unselected, null)
            rootView.btn_favorite.setOnClickListener {
                selectFavorite(position)
            }
            setOnClickListener {
                WebActivity.newInstance(context, currentCase.countryName.toLowerCase())
            }
        }
    }

    open fun selectFavorite(position: Int) {
        val newFavoriteState = !cases[position].isFavorite
        cases[position].isFavorite = newFavoriteState
        Repository.favoriteChange(newFavoriteState, cases[position].countryCode)
        notifyItemChanged(position)
    }

    class RateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getCases(): List<ListModel> {
        return this.cases
    }
}