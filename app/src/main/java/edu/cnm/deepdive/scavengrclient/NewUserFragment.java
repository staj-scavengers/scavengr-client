package edu.cnm.deepdive.scavengrclient;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;

public class NewUserFragment extends Fragment implements OnClickListener {

  public NewUserFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_new_user, container, false);
    ImageView imageView = view.findViewById(R.id.welcome_app_icon);
    imageView.setImageResource(R.drawable.ic_launcher_foreground);
    return view;
  }

  // TODO Implement click to go to find a hunt fragment
  @Override
  public void onClick(View view) {
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    fragmentManager.beginTransaction();
  }
}
