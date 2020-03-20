package com.guesswho.covidstats.adapter

import com.guesswho.covidstats.model.ListModel

/**
 *
 * @author Maxim on 20.03.20
 */
interface IGetCases {
    fun getCases() : List<ListModel>
}