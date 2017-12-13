package com.geliddroid.mysore.Fragmnets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.geliddroid.mysore.R;

/**
 * Created by levin on 28-11-2017.
 */

public class WebsiteFragment extends Fragment {
    WebView webView;
    String Url ="http://mysurudiocese.com/";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website, container, false);
        webView = (WebView)view.findViewById(R.id.webb);
        openWebView();
        return view;
    }
    public void openWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl("http://mysurudiocese.com/");
    }
}
