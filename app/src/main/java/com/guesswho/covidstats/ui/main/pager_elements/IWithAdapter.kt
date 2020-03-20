package com.guesswho.covidstats.ui.main.pager_elements

import com.guesswho.covidstats.adapter.CaseListAdapter

/**
 *
 * @author Maxim on 20.03.20
 */
interface IWithAdapter {
    open fun getCasesAdapter(): CaseListAdapter
}