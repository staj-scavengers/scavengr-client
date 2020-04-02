package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import edu.cnm.deepdive.scavengrclient.R;

public class NewUserFragment extends Fragment implements OnClickListener{

  private Button button;
  private int viewCount;

  public NewUserFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_new_user, container, false);
    ImageView imageView = view.findViewById(R.id.welcome_app_icon);
    imageView.setImageResource(R.mipmap.ic_welcome_icon);
    button = view.findViewById(R.id.new_user_done_button);
    button.setOnClickListener(this);
    viewCount++;
    return view;
  }

  @Override
  public void onClick(View v) {
    button.setVisibility(View.INVISIBLE);
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .add(R.id.fragment_container, new FindAHuntFragment(), null)
        .addToBackStack(NewUserFragment.class.getName())
        .commit();
  }
}
