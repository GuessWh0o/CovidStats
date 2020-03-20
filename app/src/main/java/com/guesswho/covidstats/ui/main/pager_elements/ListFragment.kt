package com.guesswho.covidstats.ui.main.pager_elements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guesswho.covidstats.R
import com.guesswho.covidstats.adapter.CaseListAdapter
import com.guesswho.covidstats.networking.Repository
import com.guesswho.covidstats.ui.main.GeneralViewModel
import com.guesswho.covidstats.ui.main.ViewState
import kotlinx.android.synthetic.main.fragment_activity_all.*
import kotlinx.android.synthetic.main.fragment_activity_all.view.*

open class ListFragment : Fragment(), IWithAdapter {
    private lateinit var generalViewModel: GeneralViewModel
    private lateinit var recyclerView: RecyclerView
    open val adapter = CaseListAdapter(arrayListOf())

    private val viewStateObserver = Observer<ViewState> { state ->
        when (state) {
            ViewState.LOADING -> {
                swipe_refresh.isRefreshing = true
            }
            ViewState.LOADED -> {
                swipe_refresh.isRefreshing = false
            }
            ViewState.EMPTY -> {
                swipe_refresh.isRefreshing = false
            }
            else -> {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        doOnResume()
    }

    open fun doOnResume() {
        adapter.update(Repository.listByCountry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generalViewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.list_layout, container, false)
        this.recyclerView = root.findViewById(R.id.rv_rate_list)
        this.recyclerView.layoutManager = LinearLayoutManager(activity)
        this.recyclerView.adapter = adapter
        root.swipe_refresh.setOnRefreshListener {
            generalViewModel.fetchData()
        }

        registerDataListener()

        return root
    }

    open fun registerDataListener() {
        Repository.casesList.observe(viewLifecycleOwner, Observer {
            adapter.update(Repository.listByCountry)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generalViewModel.states.observe(viewLifecycleOwner, viewStateObserver)
    }

    override fun getCasesAdapter(): CaseListAdapter {
        return this.adapter
    }
}