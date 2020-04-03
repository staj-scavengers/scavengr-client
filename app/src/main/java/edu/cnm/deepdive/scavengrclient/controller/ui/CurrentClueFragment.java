package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.Manifest;
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
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import java.io.IOException;

public class CurrentClueFragment extends Fragment {

  private SurfaceView cameraFrame;
  private BarcodeDetector qrDetector;
  private TextView clueDescription;
  private CameraSource cameraSource;
  private static final int RC_HANDLE_CAMERA_PERM = 2;

  public CurrentClueFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_current_clue, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
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

    qrDetector.setProcessor(new Detector.Processor<Barcode>() {
      @Override
      public void release() {
      }

      @Override
      public void receiveDetections(Detector.Detections<Barcode> detections) {

        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() != 0) {
          // TODO send value to MainViewModel
        }
      }
    });
  }
}