package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.Detector.Processor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CurrentClueFragment extends Fragment {

  private SurfaceView cameraFrame;
  private BarcodeDetector qrDetector;
  private TextView clueDescription;
  private CameraSource cameraSource;
  private Hunt hunt;
  private HuntActivity huntActivity;
  private List<Clue> clues;
  private Clue activeClue;
  private static final int RC_HANDLE_CAMERA_PERM = 2;
  private boolean scanned;
  private MainViewModel viewModel;

  public CurrentClueFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    hunt = viewModel.getHunt().getValue();
    clues = hunt.getClues();

    viewModel.beginOrResume(hunt.getLocalId())
        .doOnSuccess((huntActivity) -> this.huntActivity = huntActivity)
        .doOnError((throwable) -> newHuntActivity())
        .subscribe();
    return inflater.inflate(R.layout.fragment_current_clue, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    scanned = false;
    cameraFrame = view.findViewById(R.id.camera_frame);
    clueDescription = view.findViewById(R.id.clue_description);
    confirmCameraPermissions(view);
    Button clueButton = view.findViewById(R.id.clue_button);
    clueButton.setOnClickListener(v -> {
      if (clueDescription.getVisibility() == View.VISIBLE) {
        clueDescription.setVisibility(View.GONE);
        clueButton.setText(R.string.show_clue);
      } else {
        clueDescription.setVisibility(View.VISIBLE);
        clueButton.setText(R.string.hide_clue);
      }
    });
  }

  private void newHuntActivity() {
    huntActivity = new HuntActivity();
    huntActivity.setHuntId(hunt.getId().toString());
    huntActivity.setLocalId(hunt.getLocalId());
    huntActivity.setUserId("user UUID");
    huntActivity.setLocalId(0);
    huntActivity.setStarted(new Date());
    huntActivity.setCluesCompleted(0);
  }

  private void setActiveClue() {
    activeClue = clues.get(0);
  }

  private void updateActivity() {
    if (clues.size() > 1) {
      clues.remove(0);
      setActiveClue();
      huntActivity.setCluesCompleted(huntActivity.getCluesCompleted() + 1);
      viewModel.saveHuntProgress(huntActivity);
    } else {
      finishHunt();
    }
  }

  private void finishHunt() {
    // TODO write this method.
    huntActivity.setCompleted(new Date());
    viewModel.saveHuntProgress(huntActivity);
  }

  //region Camera Setup & Permissions
  private void confirmCameraPermissions(View view) {
    int rc = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
    if (rc == PackageManager.PERMISSION_GRANTED) {
      setupCamera();
    } else {
      requestCameraPermission(view);
    }
  }

  private void setupCamera() {
    Log.d("setupCamera", "called");
    createBarcodeDetector();
    cameraSource = new CameraSource
        .Builder(getContext(), qrDetector)
        .build();
    cameraFrame.getHolder().addCallback(new Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        try {
          cameraSource.start(cameraFrame.getHolder());
        } catch (IOException ie) {
          Log.e("CAMERA SOURCE", ie.getMessage());
        }
      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
      }
    });
  }

  private void requestCameraPermission(View view) {
    final String[] permissions = new String[]{Manifest.permission.CAMERA};

    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
        Manifest.permission.CAMERA)) {
      ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
      return;
    }

    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ActivityCompat.requestPermissions(getActivity(), permissions,
            RC_HANDLE_CAMERA_PERM);
      }
    };

    view.findViewById(R.id.current_clue_layout).setOnClickListener(listener);
    Snackbar.make(view, R.string.permission_camera_rationale,
        Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.ok, listener)
        .show();
  }
  //endregion

  private void createBarcodeDetector() {
    Log.d("createBarcodeDetector", "called");
    qrDetector = new BarcodeDetector.Builder(getContext())
        .setBarcodeFormats(Barcode.QR_CODE)
        .build();

    if (!qrDetector.isOperational()) {
      ((MainActivity) getActivity()).makeToast(getString(R.string.qr_dependencies_downloading));
    }

    // TODO switch Processor to qrLiveProcessor to process a live Clue.
    qrDetector.setProcessor(qrTestProcessor);
  }

  private void createWebView(String url) {
    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
    alert.setTitle("Success!");
    WebView wv = new WebView(getContext());
    wv.loadUrl(url);
    wv.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
      }
    });

    alert.setView(wv);
    alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
//        try {
//          cameraSource.start(cameraFrame.getHolder());
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
        dialog.dismiss();
        updateActivity();
        scanned = false;
      }
    });
    alert.show();
  }

  Processor<Barcode> qrLiveProcessor = new Processor<Barcode>() {

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detections<Barcode> detections) {

      final SparseArray<Barcode> barcodes = detections.getDetectedItems();

      if (barcodes.size() != 0 && !scanned) {
        String url = "";

        if (barcodes.valueAt(0).rawValue == activeClue.getMediaTag()) {
          url = activeClue.getMedia();
        } else {
          ((MainActivity) getActivity())
              .makeToast("Invalid code:" + (barcodes.valueAt(0).displayValue));
        }

        if (!url.isEmpty()) {
          scanned = true;
          String finalUrl = url;
          getActivity().runOnUiThread(() -> createWebView(finalUrl));

        }

      }
    }
  };

  Processor<Barcode> qrTestProcessor = new Processor<Barcode>() {
    @Override
    public void release() {
    }

    @Override
    public void receiveDetections(Detections<Barcode> detections) {

      final SparseArray<Barcode> barcodes = detections.getDetectedItems();

      if (barcodes.size() != 0 && !scanned) {
//          cameraSource.stop();
        String url = "";
        switch (barcodes.valueAt(0).rawValue) {
          case "scavengr-clue-1":
            url = "http://3.bp.blogspot.com/_pM1ifV7g5qs/SaIDBfjlvXI/AAAAAAAAAg8/IAx6u3tsg6w/s1600-h/turnip_murder.jpg";
            break;
          case "scavengr-clue-2":
            url = "https://youtu.be/rEUxlwb2uFI";
            break;
          case "scavengr-clue-3":
            url = "https://youtu.be/nqNqE0QiMPE";
            break;
          case "scavengr-clue-4":
            url = "https://soundcloud.com/overwerk/afterhours-medley";
            break;
          case "scavengr-clue-5":
            url = "https://en.wikipedia.org/wiki/Wikipedia:On_this_day/Today";
            break;
          default:
            ((MainActivity) getActivity())
                .makeToast("Invalid code:" + (barcodes.valueAt(0).displayValue));
            break;
        }

        if (!url.isEmpty()) {
          scanned = true;
          String finalUrl = url;
          getActivity().runOnUiThread(() -> createWebView(finalUrl));

        }

      }
    }
  };
}
