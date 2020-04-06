package edu.cnm.deepdive.scavengrclient.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.service.GoogleSignInService;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  private NavController navController;
  private GoogleSignInService signInService;
  private MainViewModel viewModel;
  static ActionBar actionBar;
  private boolean registered;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    setupViewModel();
    checkSignIn();
    setupNavigation();
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    // TODO remove this:
//    navController.navigate(R.id.nav_current_clue);
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
    if (!registered) {
      navController.navigate(R.id.nav_new_user);
    } else {
      navController.navigate(R.id.nav_find_ahunt);
    }

  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    getLifecycle().addObserver(viewModel);
  }

  private void checkSignIn() {
    signInService = GoogleSignInService.getInstance();
    signInService.refresh()
        .addOnSuccessListener(
            account -> viewModel.checkUser(account.getIdToken())
//                .doOnSuccess((user) -> navController.navigate(R.id.nav_find_ahunt))
//                .doOnError(error -> navController.navigate(R.id.nav_new_user))
                .subscribe(
                    (user) -> navController.navigate(R.id.nav_find_ahunt),
                    error -> navController.navigate(R.id.nav_new_user)
                )
        )
        .addOnFailureListener(
            account -> makeToast(getString(R.string.google_account_problem))
        );
  }

  private void signOut() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((task) -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

  /**
   * Useful for displaying error messages.
   *
   * @param message will be displayed in the Toast.
   */
  public void makeToast(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();


  }

  private void preloaderOfShame() {


  }

}


