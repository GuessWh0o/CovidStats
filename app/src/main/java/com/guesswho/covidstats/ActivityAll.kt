package com.guesswho.covidstats

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.guesswho.covidstats.adapter.CaseListAdapter
import com.guesswho.covidstats.adapter.ViewPagerTransformer
import com.guesswho.covidstats.model.ListModel
import com.guesswho.covidstats.networking.Repository
import com.guesswho.covidstats.ui.main.SectionsPagerAdapter


class ActivityAll : AppCompatActivity() {

    private lateinit var menu: Menu
    private lateinit var viewPager: ViewPager
    private lateinit var pageAdapter: SectionsPagerAdapter

    private var adapter: CaseListAdapter? = null
    private var initialList: MutableList<ListModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repository.init(this.applicationContext)
        setContentView(R.layout.activity_all)

        setUpViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        this.menu = menu

        val manager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search: SearchView = menu.findItem(R.id.search).actionView as SearchView
        search.setSearchableInfo(manager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                val newData = initialList?.filter { it.countryName.toLowerCase().startsWith(newText.toLowerCase()) }
                if(newData != null) {
                    adapter?.update(newData)
                } else if(newText.trim().isEmpty() && initialList != null) {
                    adapter?.update(initialList!!)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }
        })
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val pageNum: Int = viewPager.currentItem
        if(pageNum != 0) {
            this.adapter = pageAdapter.getFragmentAdapter(pageNum)
            adapter?.getCases()?.let {
                this.initialList = ArrayList()
                this.initialList?.addAll(it)
            }
        }
        menu.getItem(0).isVisible = pageNum != 0
        return true
    }


    private fun setUpViewPager() {
        pageAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.setPageTransformer(true, ViewPagerTransformer())
        viewPager.adapter = pageAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //Do nothing
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //Do nothing
            }

            override fun onPageSelected(position: Int) {
                invalidateOptionsMenu()
            }
        })
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}