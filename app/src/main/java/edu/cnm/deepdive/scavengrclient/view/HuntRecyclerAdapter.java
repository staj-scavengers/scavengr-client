package edu.cnm.deepdive.scavengrclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.pojo.HuntActivityWithStats;
import io.reactivex.functions.Consumer;
import java.util.List;

public class HuntRecyclerAdapter extends RecyclerView.Adapter<HuntRecyclerAdapter.Holder> {

  private final Context context;
  private final List<HuntActivityWithStats> hunts;
  private final OnClickListener listener;


  public HuntRecyclerAdapter(Context context, List<HuntActivityWithStats> hunts,
      OnClickListener listener, View view, ImageView thumbnail, TextView title,
      TextView date, TextView description) {
    this.context = context;
    this.hunts = hunts;
    this.listener = listener;

  }


  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_hunt, parent, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {

  }


  @Override
  public int getItemCount() {
    return hunts.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private final View view;
    private final ImageView thumbnail;
    private final TextView title;
    private final TextView date;
    private final TextView description;

    private Holder(@NonNull View view) {
      super(view);
      this.view = view;
      thumbnail = view.findViewById(R.id.thumbnail);
      title = view.findViewById(R.id.title);
      date = view.findViewById(R.id.date);
      description = view.findViewById(R.id.description);
    }

  }


  @FunctionalInterface
  public interface OnClickListener {

    void onClick(View view, Hunt hunt, int position);

  }

  @FunctionalInterface
  public interface ThumbnailResolver {

    void apply(Hunt hunt, Consumer<String> consumer);

  }
}
