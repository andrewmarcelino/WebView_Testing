package com.example.webviewtesting

import AndroidJSInterface
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView

class HomeFragment : Fragment() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view:View = inflater.inflate(R.layout.fragment_home,container,false)

        val webViewId = view.findViewById<WebView>(R.id.webview)
        val errorAnim = view.findViewById<LottieAnimationView>(R.id.error_animation)
        val progressBar = view.findViewById<ProgressBar>(R.id.webview_progress)
        val button = view.findViewById<Button>(R.id.button)

        webView = webViewId.apply {
            loadUrl("file:///android_asset/file.html")
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                private var lookupError:Boolean = false

                override fun onPageStarted(webView: WebView?, url: String?, favicon: Bitmap?) {
                    //Reset error value
                    lookupError = false

                    //Hide Error Animation and WebView
                    errorAnim.visibility = View.GONE
                    webViewId.animate().alpha(0.0f).duration = 100

                    //Show Progress Bar
                    progressBar.visibility = View.VISIBLE
                    Log.d(null,"Page Start Loading")
                    super.onPageStarted(webView, url, favicon)
                }

                override fun onPageFinished(webView: WebView?, url: String?) {
                    Log.d(null,"Page Finished Loading")
                    if(!lookupError) {
                        //Show page if not error
                        webViewId.visibility = View.VISIBLE
                        webViewId.animate().alpha(1.0f).duration = 100
                    }

                    //Hide Progress Bar
                    progressBar.visibility = View.GONE
                    super.onPageFinished(webView, url)
                }

                override fun onReceivedError(
                    webView: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    if(error?.errorCode == ERROR_HOST_LOOKUP) {
                        //set error variable
                        lookupError = true

                        //show error animation
                        errorAnim.visibility = View.VISIBLE
                        webViewId.visibility = View.INVISIBLE
                    }
                    super.onReceivedError(webView, request, error)
                }
            }
            addJavascriptInterface(AndroidJSInterface(view),"AndroidJSInterface")
            settings.javaScriptEnabled = true
        }

        button.setOnClickListener {
            webView.loadUrl("file:///android_asset/file_input.html")
        }

        return view
    }
}