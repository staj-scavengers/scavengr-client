package edu.cnm.deepdive.scavengrclient;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class EditHuntFragment extends Fragment {


  public EditHuntFragment() {}


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.edit_hunt_fragment, container, false);
  }

}
