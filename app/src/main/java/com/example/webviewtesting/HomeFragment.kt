package com.example.webviewtesting

import AndroidJSInterface
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_home,container,false)
        webView = view.findViewById<WebView>(R.id.webview).apply {
            loadUrl("file:///android_asset/file.html")
            webChromeClient = WebChromeClient()
            addJavascriptInterface(AndroidJSInterface(view),"AndroidJSInterface")
            settings.javaScriptEnabled = true
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            webView.loadUrl("file:///android_asset/file_input.html")
        }
        return view
    }
}