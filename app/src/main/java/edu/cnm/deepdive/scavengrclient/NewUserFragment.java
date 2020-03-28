package edu.cnm.deepdive.scavengrclient;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewUserFragment extends Fragment {

  private ImageView imageView;
  private View view;

  public NewUserFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    imageView = view.findViewById(R.id.welcome_app_icon);
    imageView.setImageResource(R.drawable.ic_launcher_foreground);
    return inflater.inflate(R.layout.fragment_new_user, container, false);
  }

}
