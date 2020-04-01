package edu.cnm.deepdive.scavengrclient.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.service.GoogleSignInService;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    setupNavigation();
    setupViewModel();
  }



  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    if (item.getItemId() == R.id.sign_out) {
      signOut();
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setupNavigation() {
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
  }

  private void setupViewModel() {
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    getLifecycle().addObserver(viewModel);
  }

  private void signOut() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((task) -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

  // Google's sample code for dummies:
  public void setupDetector() {
    BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
        .setBarcodeFormats(Barcode.QR_CODE)
        .build();
    if (!detector.isOperational()) {
      showToast("Could not set up the detector!");
      return;
    }
  }

  /**
   * Useful for displaying error messages.
   *
   * @param message will be displayed in the Toast.
   */
  public void showToast(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();
  }

}


