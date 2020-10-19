package com.easycodingg.imagesearchapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.easycodingg.imagesearchapp.R
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileFragment: Fragment(R.layout.fragment_user_profile){

    private val args: UserProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileUrl = args.profileUrl

        CoroutineScope(Dispatchers.Main).launch {
            webView.apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        pbUserProfile.visibility = View.GONE
                        webView.visibility = View.VISIBLE
                    }
                }
                loadUrl(profileUrl)
            }
        }
    }
}