package com.guesswho.covidstats.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.guesswho.covidstats.R
import com.guesswho.covidstats.adapter.CaseListAdapter
import com.guesswho.covidstats.ui.main.pager_elements.FavoritesListFragment
import com.guesswho.covidstats.ui.main.pager_elements.GeneralInfoFragment
import com.guesswho.covidstats.ui.main.pager_elements.IWithAdapter
import com.guesswho.covidstats.ui.main.pager_elements.ListFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_0,
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val steps = listOf(
        GeneralInfoFragment(),
        ListFragment(),
        FavoritesListFragment()
    )

    fun getFragmentAdapter(position: Int) : CaseListAdapter? {
        return if(steps[position] is IWithAdapter) {
            (steps[position] as IWithAdapter).getCasesAdapter()
        } else null
    }

    override fun getItem(position: Int): Fragment {
        return steps[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}