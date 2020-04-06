package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.scavengrclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewHuntFragment extends Fragment {


  public CreateNewHuntFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_create_new_hunt, container, false);
  }

}
