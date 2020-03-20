package com.guesswho.covidstats.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.guesswho.covidstats.R
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
    }

    override fun onStart() {
        super.onStart()
        val country = intent.extras?.getString("COUNTRY", null)
        setUpWebView()
        if(country != null) {
            webview.loadUrl("https://www.worldometers.info/coronavirus/country/$country")
        } else {
            webview.loadUrl("https://www.worldometers.info/coronavirus/#countries")
        }
    }


    private fun setUpWebView() {
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean { // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                if(url.contains("404")) {
                    Toast.makeText(this@WebActivity, "Your country details are not available...", Toast.LENGTH_SHORT).show()
                    view.loadUrl("https://www.worldometers.info/coronavirus/#countries")
                } else {
                    view.loadUrl(url)
                }

                return false // then it is not handled by default action
            }
        }
    }

    companion object {

        fun newInstance(context: Context, country: String) {
            val args = Bundle()
            args.putString("COUNTRY", country)
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtras(args)
            context.startActivity(intent)
        }
    }
}
