package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

  private WebView mediaView;
  private final String url;

  public ClueMediaFragment(String url) {
    this.url = url;
  }


  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cluemedia, null);
    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
    alert.setTitle("Success");
    mediaView = new WebView(getContext());
    mediaView.loadUrl(url);
    mediaView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
      }
    });
    alert.setView(mediaView)
        .setNegativeButton("Close", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        });
    return alert.show();
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
