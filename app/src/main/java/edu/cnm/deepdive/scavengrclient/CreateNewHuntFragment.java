package edu.cnm.deepdive.scavengrclient;


import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import edu.cnm.deepdive.scavengrclient.ui.main.MainFragment;


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
    View view = inflater.inflate(R.layout.fragment_create_new_hunt, container, false);
    return view;
  }

}
