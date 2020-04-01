package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
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
  private String cameraId;
  private CameraManager cameraManager;
  private Size imageDimension;
  private static final int REQUEST_CAMERA_PERMISSION = 200;
  protected CaptureRequest captureRequest;
  protected CaptureRequest.Builder captureRequestBuilder;
  protected CameraDevice cameraDevice;
  protected CameraCaptureSession cameraCaptureSessions;
  private Handler backgroundHandler;
  private HandlerThread backgroundThread;
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
    // Inflate the layout for this fragment

    return inflater.inflate(R.layout.fragment_current_clue, container, false);

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    cameraFrame = view.findViewById(R.id.camera_frame);
    clueDescription = view.findViewById(R.id.clue_description);
    confirmCameraPermissions(view);
//    cameraFrame.setSurfaceTextureListener(textureListener);
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

 /* //region CameraPreview methods
  SurfaceTextureListener textureListener = new SurfaceTextureListener() {
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
      openCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
      // transform image
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
      return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
      // handle event
    }
  };
  private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
    @Override
    public void onOpened(CameraDevice camera) {
      //This is called when the camera is open
      cameraDevice = camera;
      createCameraPreview();
    }

    @Override
    public void onDisconnected(CameraDevice camera) {
      cameraDevice.close();
    }

    @Override
    public void onError(CameraDevice camera, int error) {
      cameraDevice.close();
      cameraDevice = null;
    }
  };

  protected void startBackgroundThread() {
    backgroundThread = new HandlerThread("Camera Background");
    backgroundThread.start();
    backgroundHandler = new Handler(backgroundThread.getLooper());
  }

  protected void stopBackgroundThread() {
    backgroundThread.quitSafely();
    try {
      backgroundThread.join();
      backgroundThread = null;
      backgroundHandler = null;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  protected void createCameraPreview() {
    try {
      SurfaceTexture texture = cameraFrame.getSurfaceTexture();
      assert texture != null;
      texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
      Surface surface = new Surface(texture);

      //replace with QR capture?
      captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
      captureRequestBuilder.addTarget(surface);

      cameraDevice
          .createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
              //The camera is already closed
              if (null == cameraDevice) {
                return;
              }
              // When the session is ready, we start displaying the preview.
              cameraCaptureSessions = cameraCaptureSession;
              updatePreview();
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
              Toast.makeText(getActivity(), "Configuration change", Toast.LENGTH_SHORT).show();
            }
          }, null);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }


  private void openCamera() {
    cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
    try {
      cameraId = cameraManager.getCameraIdList()[0];
      CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
      StreamConfigurationMap map = characteristics
          .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
      assert map != null;
      imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
      // Add permission check for camera
      if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
          != PackageManager.PERMISSION_GRANTED && ActivityCompat
          .checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(getActivity(),
            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
            REQUEST_CAMERA_PERMISSION);
        return;
      }
      cameraManager.openCamera(cameraId, stateCallback, null);
      createBarcodeDetector();
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  protected void updatePreview() {
    captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    try {
      cameraCaptureSessions
          .setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  private void closeCamera() {
    if (cameraDevice != null) {
      cameraDevice.close();
      cameraDevice = null;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CAMERA_PERMISSION) {
      if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
        ((MainActivity) getActivity()).showToast(getString(R.string.camera_permission_denied));
        // go home
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.e("ClueCam", "onResume");
    startBackgroundThread();
    if (cameraFrame.isAvailable()) {
      openCamera();
    } else {
      cameraFrame.setSurfaceTextureListener(textureListener);
    }
  }

  @Override
  public void onPause() {
    Log.e("ClueCam", "onPause");
    closeCamera();
    stopBackgroundThread();
    super.onPause();
  }
  //endregion*/

  private void createBarcodeDetector() {
    Log.d("createBarcodeDetector", "called");
    qrDetector = new BarcodeDetector.Builder(getContext())
        .setBarcodeFormats(Barcode.QR_CODE)
        .build();

    if (!qrDetector.isOperational()) {
      ((MainActivity) getActivity()).showToast(getString(R.string.qr_dependencies_downloading));
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
