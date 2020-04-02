package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import edu.cnm.deepdive.scavengrclient.R;

public class SingleHuntFragment extends Fragment implements OnClickListener {


  public SingleHuntFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_single_hunt, container, false);
    Button button = view.findViewById(R.id.leave_hunt_button);
    button.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View v) {
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .add(R.id.nav_join_hunt, new JoinHuntFragment(), null)
        .commit();
  }
}
