package com.guesswho.covidstats.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.guesswho.covidstats.ActivityAll
import com.guesswho.covidstats.R
import kotlinx.android.synthetic.main.activity_launcher.*


class LauncherActivity : AppCompatActivity() {

    private val generalViewModel: GeneralViewModel by viewModels()

    private val viewStateObserver = Observer<ViewState> { state ->
        when (state) {
            ViewState.LOADED -> {
                startActivity(Intent(this, ActivityAll::class.java))
            }
            else -> {
                progress_bar_splash.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        generalViewModel.states.observe(this, viewStateObserver)
        generalViewModel.fetchData()
    }


}
