package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import edu.cnm.deepdive.scavengrclient.view.HuntRecyclerAdapter;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;

public class HuntFragment extends Fragment {

  private RecyclerView huntList;
  private MainViewModel viewModel;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.fragment_hunt, container, false);
    huntList = layout.findViewById(R.id.hunt_list);
    return layout;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getHunt()
    .observe(getViewLifecycleOwner(), new Observer<Hunt>() {
      @Override
      public void onChanged(Hunt hunt) {


        Navigation.findNavController(view).navigate(R.id.nav_current_clue);
      }
    });

  }

}
