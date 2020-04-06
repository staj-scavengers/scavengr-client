package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.graphics.Paint.Join;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;


public class FindAHuntFragment extends Fragment implements OnClickListener {

  private RadioGroup filterMethod;
  private RadioButton methodSearch;
  private RadioButton methodPopular;
  private RadioButton methodAll;
  private SearchView searchFilter;
  private ListView huntList;
  private Button createHunt;
  private MainViewModel viewModel;
  private Boolean isOrganizer;

  public FindAHuntFragment() {
    this.isOrganizer = isOrganizer;
    // Required empty public constructor
  }

  // TODO Get HuntDetails instead of Hunts from MVM.


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_find_ahunt, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupView(view);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getHunts().observe(getViewLifecycleOwner(), (hunts) -> {
      ArrayAdapter<Hunt> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, hunts);
      huntList.setAdapter(adapter);
    });
  }

  private void setupView(@NonNull View view) {
    searchFilter = view.findViewById(R.id.search_filter);
    huntList = view.findViewById(R.id.hunt_list);
    createHunt = view.findViewById(R.id.create_hunt);
    createHunt.setOnClickListener(this);
    filterMethod = view.findViewById(R.id.filter_method);
    methodSearch = view.findViewById(R.id.method_search);
    methodPopular = view.findViewById(R.id.method_popular);
    methodAll = view.findViewById(R.id.method_all);
    filterMethod.setOnCheckedChangeListener((group, checkedId) -> {
      searchFilter.setVisibility(View.GONE);
      switch (checkedId) {
        case R.id.method_search:
          searchFilter.setVisibility(View.VISIBLE);
          break;
        case R.id.method_popular:
          viewModel.searchHunts(null, true, true);
          break;
        case R.id.method_all:
          viewModel.searchHunts(null, true, null);
          break;
      }
    });
    searchFilter.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        viewModel.searchHunts(query.trim(), true, null);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

    huntList.setOnItemClickListener((parent, v, position, id) -> {
      Hunt hunt = (Hunt) parent.getItemAtPosition(position);
if (hunt.getLocalId() != 0) {
  viewModel.loadLocalHunt(hunt.getLocalId());
} else{
  viewModel.downloadHunt(hunt.getId());
}
      viewModel.getHunt().observe(getViewLifecycleOwner(), new Observer<Hunt>() {
        @Override
        public void onChanged(Hunt hunt) {
          if (hunt == null) {
          } else {
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.nav_join_hunt);
          }
        }
      });

//
//      Bundle args = new Bundle();
//      args.putSerializable("remoteId", hunt.getId());
//      NavHostFragment.findNavController(this).navigate(R.id.nav_join_hunt, args);
    });
  }

  @Override
  public void onClick(View view) {
    Navigation.findNavController(view).navigate(R.id.nav_create_new_hunt);
  }
}
