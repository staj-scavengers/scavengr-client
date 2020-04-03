package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.scavengrclient.R;
import androidx.appcompat.app.AlertDialog.Builder;

public class ClueMediaFragment extends DialogFragment {

  private View dialogView;
  private WebView mediaView;
  private final String url;

  public ClueMediaFragment(String url) {
    this.url = url;
  }



  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cluemedia, null);
    AlertDialog alert = new Builder(getContext())
        .setView(dialogView)
        .setNeutralButton("OK", (dlg, which) -> {
        })
        .create();
    setupWebView(alert); // TODO is this stupid?
    return alert;
  }

  private void setupWebView(AlertDialog root) {
    mediaView = root.findViewById(R.id.clue_media_view);
    mediaView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
      }
    });

    WebSettings settings = mediaView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
    settings.setBlockNetworkImage(true);
    settings.setLoadsImagesAutomatically(false);
//    settings.setBlockNetworkLoads(true);  this setting is too restrictive and breaks the webview.
  }
}
