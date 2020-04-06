package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;
import java.util.UUID;


public class JoinHuntFragment extends Fragment {

  private MainViewModel viewModel;
  private Hunt activeHunt;

  public JoinHuntFragment() {
    // Required empty public constructor
  }

  // TODO this won't work if the MainViewModel hasn't loaded a hunt yet.
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    if (getArguments() != null) {
//      viewModel.downloadHunt(UUID.fromString(getArguments().getString("remoteId")));
//    }

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

    View view =  inflater.inflate(R.layout.fragment_join_hunt, container, false);

    Button join = view.findViewById(R.id.join_hunt_button);
    join.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.nav_hunt);
      }
    });
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TextView huntName = view.findViewById(R.id.hunt_name);
    TextView creatorName = view.findViewById(R.id.creator_name);
    TextView clueCount = view.findViewById(R.id.clue_count);

    viewModel.getHunt().observe(getViewLifecycleOwner(), (value) -> {

      if (value != null){
      activeHunt = viewModel.getHunt().getValue();

        huntName.setText(activeHunt.getHuntName());
        creatorName.setText(activeHunt.getOrganizerName());
        clueCount.setText(Integer.toString(activeHunt.getClues().size()));
} else {
        ((MainActivity)getActivity()).makeToast("Error loading hunt");
      }
    });

  }


}
