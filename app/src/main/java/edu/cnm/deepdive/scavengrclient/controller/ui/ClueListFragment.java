package edu.cnm.deepdive.scavengrclient.controller.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ClueListFragment extends Fragment {

  private RecyclerView clueList;
  private MainViewModel viewModel;
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int mColumnCount = 1;
  private OnListFragmentInteractionListener mListener;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
   * screen orientation changes).
   */
  public ClueListFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static ClueListFragment newInstance(int columnCount) {
    ClueListFragment fragment = new ClueListFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
//      Bundle savedInstanceState) {
//    View view = inflater.inflate(R.layout.fragment_clue, container, false);
//    // Set the adapter
//    if (view instanceof RecyclerView) {
//      Context context = view.getContext();
//      RecyclerView recyclerView = (RecyclerView) view;
//      if (mColumnCount <= 1) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//      } else {
//        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//      }
//      recyclerView.setAdapter(new ClueRecyclerViewAdapter(DummyContent.ITEMS, mListener));
//    }
//    return view;
//  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnListFragmentInteractionListener) {
      mListener = (OnListFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnListFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   * <p/>
   * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnListFragmentInteractionListener {

    // TODO: Update argument type and name

  }
}