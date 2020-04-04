package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;


public class JoinHuntFragment extends Fragment {

  private MainViewModel viewModel;
  private Hunt hunt;

  public JoinHuntFragment() {
    // Required empty public constructor
  }

  // TODO this won't work if the MainViewModel hasn't loaded a hunt yet.
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    hunt = viewModel.getHunt().getValue();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_join_hunt, container, false);
    TextView huntName = view.findViewById(R.id.hunt_name);
    TextView creatorName = view.findViewById(R.id.creator_name);
    TextView clueCount = view.findViewById(R.id.clue_count);

    huntName.setText(hunt.getHuntName());
    creatorName.setText(hunt.getOrganizerName());
    clueCount.setText(hunt.getClues().size());

    Button join = view.findViewById(R.id.join_hunt_button);
    join.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.nav_hunt);
      }
    });
    return view;
  }


}
