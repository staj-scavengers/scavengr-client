package edu.cnm.deepdive.scavengrclient;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import edu.cnm.deepdive.scavengrclient.controller.ui.HuntFragment;
import edu.cnm.deepdive.scavengrclient.controller.LoginActivity;
import edu.cnm.deepdive.scavengrclient.service.GoogleSignInService;
import edu.cnm.deepdive.scavengrclient.controller.ui.MainFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, new MainFragment())
          .commitNow();
    }
    HuntFragment huntFragment = new HuntFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().add(R.id.main, huntFragment).commit();

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

  private void signOut() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((task) -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

}


