package com.guesswho.covidstats.ui.main.pager_elements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.guesswho.covidstats.R
import com.guesswho.covidstats.networking.Repository
import com.guesswho.covidstats.ui.main.GeneralViewModel
import com.guesswho.covidstats.ui.main.ViewState
import kotlinx.android.synthetic.main.fragment_activity_all.*
import kotlinx.android.synthetic.main.fragment_activity_all.view.*
import kotlinx.android.synthetic.main.overall_view.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class GeneralInfoFragment : Fragment() {

    private lateinit var generalViewModel: GeneralViewModel
    private lateinit var overallView: View


    private val viewStateObserver = Observer<ViewState> { state ->
        when (state) {
            ViewState.LOADING -> {
                swipe_refresh.isRefreshing = true
            }
            else -> {
                swipe_refresh.isRefreshing = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generalViewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_activity_all, container, false)
        this.overallView = root.overall_view

        root.swipe_refresh.setOnRefreshListener {
            generalViewModel.fetchData()
        }

        Repository.casesList.observe(viewLifecycleOwner, Observer { caseResponse ->
            root.tv_confirmed.text = caseResponse.latest.confirmed.toString()
            root.tv_death.text = caseResponse.latest.deaths.toString()
            root.tv_recovered.text = caseResponse.latest.recovered.toString()
        })
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generalViewModel.states.observe(viewLifecycleOwner, viewStateObserver)
    }
}