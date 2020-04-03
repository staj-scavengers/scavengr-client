package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;


public class FindAHuntFragment extends Fragment {

  private Button next;

  public FindAHuntFragment() {
    // Required empty public constructor
  }

  // TODO complete search: get text from EditText field, call viewModel.SearchHunts(string).
  // TODO next, get the contents of "hunts" mutable live data (List<Hunt>).
  // TODO then send the list of hunts to a recycler adapter.
  // TODO tap on a recycler item should call viewModel.downloadHunt with that id, and load that Hunt in the JoinHuntFragment.

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_find_ahunt, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    next = view.findViewById(R.id.find_a_hunt_next_button);
    next.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(view).navigate(R.id.nav_hunt);
      }
    });

  }
}