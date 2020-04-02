package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import edu.cnm.deepdive.scavengrclient.ui.main.MainFragment;


public class FindAHuntFragment extends Fragment implements OnClickListener {


  public FindAHuntFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_find_ahunt, container, false);
    Button newHunt = view.findViewById(R.id.create_hunt);
//    newHunt.setOnClickListener(this);
    Button nextButton = view.findViewById(R.id.find_a_hunt_next_button);
    nextButton.setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View v) {

    Intent intent = new Intent(this.getContext(), MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    fragmentManager.beginTransaction()
//        .add(R.id.fragment_container, new MainFragment(), null)
//        .commit();
  }
}