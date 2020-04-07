package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewHuntFragment extends Fragment implements OnClickListener {


  public CreateNewHuntFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_create_new_hunt, container, false);
    Button button = view.findViewById(R.id.complete_create_hunt_button);
    button.setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View view) {
    Navigation.findNavController(view).navigate(R.id.nav_home);
  }
}
