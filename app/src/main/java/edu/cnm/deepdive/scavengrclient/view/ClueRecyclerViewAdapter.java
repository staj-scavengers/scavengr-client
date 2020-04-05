package edu.cnm.deepdive.scavengrclient.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.scavengrclient.R;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import java.util.List;
import javax.annotation.Nonnull;

public class ClueRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final Context context;
  private final Hunt hunt;
  private final List<Clue> clues;

  public ClueRecyclerViewAdapter(Context context, Hunt hunt) {
    this.hunt = hunt;
    this.context = context;
    this.clues = this.hunt.getClues();
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_clue, null, true);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ViewHolder) {
      ((ViewHolder) holder).bind(clues.get(position));
    }
  }

  @Override
  public int getItemCount() {
    return clues.size();
  }

  @Override
  public int getItemViewType(int position) {
    return 1;
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView ordered;

    public ViewHolder(View itemView) {
      super(itemView);
      ordered = itemView.findViewById(R.id.clue_number);
    }

    public void bind(Clue clue) {
      ordered.setText(clue.getHuntOrder());
    }

  }

  @FunctionalInterface
  public interface OnClickListener {

    void onClick(int position);
  }

}