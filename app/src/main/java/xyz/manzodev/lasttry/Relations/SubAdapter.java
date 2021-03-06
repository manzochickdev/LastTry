package xyz.manzodev.lasttry.Relations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.RelationsLayoutSubBinding;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder>{
    RelationsModel relationsModel;
    Context context;

    public SubAdapter(RelationsModel relationsModel, Context context) {
        this.relationsModel = relationsModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.relations_layout_sub,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.relationsLayoutSubBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return relationsModel.models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        RelationsLayoutSubBinding relationsLayoutSubBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relationsLayoutSubBinding = DataBindingUtil.bind(itemView);
        }
    }
}
