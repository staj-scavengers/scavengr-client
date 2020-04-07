package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import edu.cnm.deepdive.scavengrclient.databinding.FragmentClueBinding;
import edu.cnm.deepdive.scavengrclient.databinding.FragmentClueBindingImpl;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.view.ClueRecyclerAdapter;
import edu.cnm.deepdive.scavengrclient.view.ClueRecyclerAdapter.OnClickListener;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

public class ClueListFragment extends Fragment implements OnClickListener {

  private RecyclerView clueList;
  ClueRecyclerAdapter clueRecyclerAdapter;
  private MainActivity mainActivity;
  public FragmentClueBinding binding;
  private final List<Clue> clues = new ArrayList<>();
  private Hunt hunt;
  private MainViewModel viewModel;
  private TextView clueOrder;
  private TextView clueNumber;
  private TextView clueName;
  private ImageView clickClue;


  public ClueListFragment() {
  }

  private void setupRecyclerView() {
    RecyclerView recyclerView = clueList;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    clueRecyclerAdapter = new ClueRecyclerAdapter(getContext(), hunt, this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(clueRecyclerAdapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentClueBinding.inflate(inflater);
    binding.setBindViewModel(viewModel);
    binding.setUiFragment(this);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel.getClues().observe(getViewLifecycleOwner(), huntObserver);
  }

  @Override
  public void onClick(int position) {
    viewModel.loadClue(clues.get(position).getLocalId());
    viewModel.getClue().observe(getViewLifecycleOwner(), clueObserver);
  }

  final Observer<List<Clue>> huntObserver = new Observer<List<Clue>>() {
    @Override
    public void onChanged(List<Clue> setClues) {
      if (setClues != null) {
        setupRecyclerView();
      }
    }
  };

      final Observer<Clue> clueObserver = new Observer<Clue>() {
        @Override
        public void onChanged(Clue setClues) {
          if (setClues == null) {
            Toast.makeText(mainActivity, "No clues yet!", Toast.LENGTH_LONG).show();
          } else {
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.nav_current_clue);
          }
        }
    };



}
