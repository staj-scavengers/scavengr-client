package edu.cnm.deepdive.scavengrclient;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.scavengrclient.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, MainFragment.newInstance())
          .commitNow();
    }
  }
}
