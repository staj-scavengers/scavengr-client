package edu.cnm.deepdive.scavengrclient.controller.ui;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

  private MainViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.main_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);    // TODO: Use the ViewModel
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Navigation.findNavController(view).navigate(R.id.nav_current_clue);
  }
}
