package com.example.sestefan.proyecto.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.webview.MyWebViewClient;

public class TermsAndCondsFragment extends Fragment {

    private static final String URL_TERMS_CONDS = "http://173.233.86.183:8080/CursoAndroidWebApp/condiciones.html";

    private WebView webView;

    public TermsAndCondsFragment() {
        // Required empty public constructor
    }

    public static TermsAndCondsFragment newInstance() {
        TermsAndCondsFragment fragment = new TermsAndCondsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_terms_and_conds, container, false);
        webView = v.findViewById(R.id.webview);
//                WebSettings webSettings = webView.getSettings();
//                webSettings.setJavaScriptEnabled(true);
//                webView.addJavascriptInterface(new WebViewInterface(this), "Android");
        webView.setWebViewClient(new MyWebViewClient(getContext()));
        webView.loadUrl(URL_TERMS_CONDS);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
