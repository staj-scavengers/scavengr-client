package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.scavengrclient.R;


public class JoinHuntFragment extends Fragment {

  public JoinHuntFragment() {
    // Required empty public constructor
  }

  // TODO Get a Hunt from the viewModel MutableLiveData Hunt field
  // TODO display Hunt details
  // TODO Join button onClick -> save hunt locally

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_join_hunt, container, false);
  }
  
}
