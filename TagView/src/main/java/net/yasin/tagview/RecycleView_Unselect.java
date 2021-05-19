package net.yasin.tagview;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleView_Unselect extends RecyclerView.Adapter<RecycleView_Unselect.ViewHolder> {

    private List<tagModel> tagModelList;
    private Click click;
    private float itemTextSize;
    private int itemTextColor;
    private int itemDrawable;

    public RecycleView_Unselect(List<tagModel> tagModelList, Click click) {
        this.tagModelList = tagModelList;
        this.click = click;
    }

    public interface Click{
        void click_item_unselect(tagModel tagModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_tagview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        tagModel tagModel = tagModelList.get(position);


        holder.title.setText(tagModel.getTitle());
        holder.item_tagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null)click.click_item_unselect(tagModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ConstraintLayout item_tagview;
        ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_items_tagview);
            item_tagview = itemView.findViewById(R.id.item_tagview);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_delete.setVisibility(View.GONE);

            if (itemTextSize > 0)
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP,itemTextSize);
            if (itemTextColor != 0)
                title.setTextColor(itemTextColor);
            if (itemDrawable != 0)
                item_tagview.setBackgroundResource(itemDrawable);
        }

    }

    public void setItemTextSize(float itemTextSize) {
        this.itemTextSize = itemTextSize;
        notifyDataSetChanged();
    }

    public void setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
        notifyDataSetChanged();
    }

    public void setItemDrawable(int itemDrawable) {
        this.itemDrawable = itemDrawable;
        notifyDataSetChanged();
    }
}
