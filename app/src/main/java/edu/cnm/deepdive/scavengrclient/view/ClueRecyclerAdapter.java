package edu.cnm.deepdive.scavengrclient.view;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import io.reactivex.functions.Consumer;
import java.util.List;
import javax.annotation.Nonnull;

public class ClueRecyclerAdapter extends RecyclerView.Adapter<ClueRecyclerAdapter.ViewHolder> {

  private final Context context;
  private final Hunt hunt;
  private final List<Clue> clues;
  private OnClickListener onClickListener;

  public ClueRecyclerAdapter(Context context, Hunt hunt, OnClickListener onClickListener) {
    this.hunt = hunt;
    this.context = context;
    this.clues = this.hunt.getClues();
    this.onClickListener = onClickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_clue, parent, false);
    return new ViewHolder(view, onClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.clueNumber.setText(clues.get(position).getHuntOrder());
    holder.clueName.setText(clues.get(position).getClueName());
  }

  @Override
  public int getItemCount() {
    return clues.size();
  }

  @Override
  public int getItemViewType(int position) {
    return 1;
  }


  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView clueNumber;
    private final TextView clueName;
    OnClickListener onClickListener;

    public ViewHolder(View itemView, OnClickListener onClickListener) {
      super(itemView);
      clueNumber = itemView.findViewById(R.id.clue_number);
      clueName = itemView.findViewById(R.id.clue_name);
      this.onClickListener = onClickListener;

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      onClickListener.onClick(getAdapterPosition());
    }

  }



    @FunctionalInterface
    public interface OnClickListener {

      void onClick(int position);

    }

}