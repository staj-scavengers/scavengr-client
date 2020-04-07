package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;
import java.util.List;

public class ClueListFragment extends Fragment {

  private ListView clueList;
  private MainViewModel viewModel;
  private TextView clueNumber;
  private TextView clueName;
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int mColumnCount = 1;

  public ClueListFragment() {
  }

  public static ClueListFragment newInstance(int columnCount) {
    ClueListFragment fragment = new ClueListFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//
//    if (getArguments() != null) {
//      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//    }
//  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_clue, container, false);
//    // Set the adapter
//    if (view instanceof RecyclerView) {
//      Context context = view.getContext();
//      RecyclerView recyclerView = (RecyclerView) view;
//      if (mColumnCount <= 1) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//      } else {
//        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//      }
//      recyclerView.setAdapter(new ClueRecyclerViewAdapter(context, mListener));
//    }
//    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getClues().observe(getViewLifecycleOwner(), (clues) -> {
          ArrayAdapter<Clue> adapter =
              new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, clues);
          clueList.setAdapter(adapter);
    });
  }

  //  @Override
//  public void onAttach(Context context) {
//    super.onAttach(context);
//    if (context instanceof OnListFragmentInteractionListener) {
//      mListener = (OnListFragmentInteractionListener) context;
//    } else {
//      throw new RuntimeException(context.toString()
//          + " must implement OnListFragmentInteractionListener");
//    }
//  }
//
//  @Override
//  public void onDetach() {
//    super.onDetach();
//    mListener = null;
//  }

  public interface OnListFragmentInteractionListener {

    void onListFragmentInteraction(Object item);

  }
}
