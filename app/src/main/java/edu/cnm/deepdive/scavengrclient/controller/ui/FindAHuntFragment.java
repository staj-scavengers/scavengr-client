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
import edu.cnm.deepdive.scavengrclient.R;


public class FindAHuntFragment extends Fragment {

  private Button next;

  public FindAHuntFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_find_ahunt, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    next = view.findViewById(R.id.find_a_hunt_next_button);
    next.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        HuntFragment huntFragment = new HuntFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main,huntFragment);
        transaction.commit();
      }
    });

  }
}