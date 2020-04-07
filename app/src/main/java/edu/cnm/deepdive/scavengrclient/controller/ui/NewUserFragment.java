package edu.cnm.deepdive.scavengrclient.controller.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.controller.MainActivity;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;

public class NewUserFragment extends Fragment implements OnClickListener {

  private EditText inputUserName;
  private Switch organizerSwitch;
  private Boolean isOrganizer;
  static ActionBar actionBar;

  public NewUserFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
    View view = inflater.inflate(R.layout.fragment_new_user, container, false);
    ImageView imageView = view.findViewById(R.id.welcome_app_icon);
    imageView.setImageResource(R.mipmap.ic_launcher_foreground);
    inputUserName = view.findViewById(R.id.input_username);
    organizerSwitch = view.findViewById(R.id.is_organizer_switch);
    Button register = view.findViewById(R.id.register_button);
    register.setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View view) {
    MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    // TODO correctly implement registration downstream in this method:
//    Bundle bundle = new Bundle();
    isOrganizer = organizerSwitch.isChecked();
//    bundle.putBoolean("isOrganizer", organizerSwitch.isChecked());

    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putBoolean("isOrganizer", isOrganizer);
    editor.apply();

//    FindAHuntFragment newFragment = new FindAHuntFragment();
//    newFragment.setArguments(bundle);

    viewModel.register(inputUserName.getText().toString());
    Navigation.findNavController(view).navigate(R.id.nav_find_ahunt);
  }
}
