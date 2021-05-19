package net.yasin.tagview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CustomTagView extends LinearLayout implements View.OnClickListener, RecycleView_Select.Click,
    RecycleView_Unselect.Click{

    private RecyclerView rv_select;
    private RecyclerView rv_unselect;
    private LinearLayout ll_drop, ll_tagview_unselect;
    private ConstraintLayout cl_drop;
    private ImageView iv_drop, iv_icon_info;
    private TextView tv_info, tv_title;
    private List<tagModel> tagModelList_unselect= new ArrayList<>();
    private List<tagModel> tagModelList_select= new ArrayList<>();
    private RecycleView_Select recycleView_select;
    private RecycleView_Unselect recycleView_unselect;
    private View underLine;
    private setOnAddListener setOnAddListener;
    private int text_title_color;
    private int text_title_size;
    private int underLine_select_color;
    private int underLine_unselect_color;
    private int textAlert_color;
    private int icon_alert;
    private int icon_drop;
    private int itemSelect_text_color;
    private int itemSelect_text_size;
    private int itemSelect_drawable;
    private int itemUnSelect_text_color;
    private int itemUnSelect_text_size;
    private int itemUnSelect_drawable;
    private String text_alert;

    public CustomTagView(Context context) {
        super(context);
        initView();
    }

    @SuppressLint("NonConstantResourceId")
    public CustomTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // retrieved values correspond to the positions of the attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomTagView);
        int count = typedArray.getIndexCount();
        try{
            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.CustomTagView_itemSelect_text_color) {
                    itemSelect_text_color = typedArray.getColor(attr, getResources().getColor(R.color.white));
                } else if (attr == R.styleable.CustomTagView_itemSelect_text_size) {
                    itemSelect_text_size = typedArray.getDimensionPixelSize(attr, 12);
                } else if (attr == R.styleable.CustomTagView_itemSelect_drawable) {
                    itemSelect_drawable = typedArray.getResourceId(attr, R.drawable.button);
                } else if (attr == R.styleable.CustomTagView_itemUnSelect_text_color) {
                    itemUnSelect_text_color = typedArray.getColor(attr, getResources().getColor(R.color.white));
                } else if (attr == R.styleable.CustomTagView_itemUnSelect_text_size) {
                    itemUnSelect_text_size = typedArray.getDimensionPixelSize(attr, 12);
                } else if (attr == R.styleable.CustomTagView_itemUnSelect_drawable) {
                    itemUnSelect_drawable = typedArray.getResourceId(attr, R.drawable.button);
                } else if (attr == R.styleable.CustomTagView_text_title_color) {
                    text_title_color = typedArray.getColor(attr, Color.parseColor("#FF455060"));
                } else if (attr == R.styleable.CustomTagView_text_title_size) {
                    text_title_size = typedArray.getDimensionPixelSize(attr, 12);
                } else if (attr == R.styleable.CustomTagView_underline_select_color) {
                    underLine_select_color = typedArray.getColor(attr, getResources().getColor(R.color.item));
                } else if (attr == R.styleable.CustomTagView_underline_unselect_color) {
                    underLine_unselect_color = typedArray.getColor(attr, getResources().getColor(R.color.item));
                } else if (attr == R.styleable.CustomTagView_textAlert_color) {
                    textAlert_color = typedArray.getColor(attr, getResources().getColor(R.color.yellow_500));
                } else if (attr == R.styleable.CustomTagView_icon_alert) {
                    icon_alert = typedArray.getResourceId(attr, R.drawable.ic_alert_icon);
                } else if (attr == R.styleable.CustomTagView_icon_drop) {
                    icon_drop = typedArray.getResourceId(attr, R.drawable.ic_arrow_up);
                }
            }
        }
        finally {
            typedArray.recycle();
            initView();
        }
    }

    public CustomTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }



    private void initView(){
        //inflate(getContext(), R.layout.tagview, null);
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.tagview, this);
        rv_select = view.findViewById(R.id.rv_tagview_select);
        rv_unselect = view.findViewById(R.id.rv_tagview_unselect);
        ll_tagview_unselect = view.findViewById(R.id.ll_tagview_unselect);
        ll_tagview_unselect.setVisibility(GONE);
        iv_icon_info = view.findViewById(R.id.icon_info);
        iv_icon_info.setVisibility(INVISIBLE);
        tv_info = view.findViewById(R.id.tv_info);
        tv_info.setVisibility(GONE);
        tv_title = view.findViewById(R.id.tv_title);
        underLine = view.findViewById(R.id.underLine);

        ll_drop = view.findViewById(R.id.ll_drop);
        cl_drop = view.findViewById(R.id.cl_drop);
        iv_drop = view.findViewById(R.id.iv_drop);
        iv_drop.setImageResource(icon_drop);


        FlexboxLayoutManager layoutManager_select = new FlexboxLayoutManager(getContext());
        layoutManager_select.setFlexDirection(FlexDirection.ROW);
        layoutManager_select.setFlexWrap(FlexWrap.WRAP);
        layoutManager_select.setJustifyContent(JustifyContent.FLEX_START);

        rv_select.setLayoutManager(layoutManager_select);
        recycleView_select = new RecycleView_Select(tagModelList_select,this);
        recycleView_select.setItemTextColor(itemSelect_text_color);
        recycleView_select.setItemDrawable(itemSelect_drawable);
        try {
            recycleView_select.setItemTextSize(itemSelect_text_size/getResources().getDisplayMetrics().scaledDensity);
        }catch (Exception ignored){}
        rv_select.setAdapter(recycleView_select);

        FlexboxLayoutManager layoutManager_unselect = new FlexboxLayoutManager(getContext());
        layoutManager_unselect.setFlexDirection(FlexDirection.ROW);
        layoutManager_unselect.setFlexWrap(FlexWrap.WRAP);
        layoutManager_unselect.setJustifyContent(JustifyContent.FLEX_START);

        rv_unselect.setLayoutManager(layoutManager_unselect);
        recycleView_unselect = new RecycleView_Unselect(tagModelList_unselect,this);
        recycleView_unselect.setItemTextColor(itemUnSelect_text_color);
        recycleView_unselect.setItemDrawable(itemUnSelect_drawable);
        try {
            recycleView_unselect.setItemTextSize(itemUnSelect_text_size/getResources().getDisplayMetrics().scaledDensity);
        }catch (Exception ignored){}
        rv_unselect.setAdapter(recycleView_unselect);

        //--------------------------------------------
        if (icon_drop != 0)
            this.iv_drop.setImageResource(icon_drop);
        if (textAlert_color != 0)
            this.tv_info.setTextColor(textAlert_color);
        if (underLine_unselect_color != 0)
            this.underLine.setBackgroundColor(underLine_unselect_color);
        if (text_title_color != 0)
            this.tv_title.setTextColor(text_title_color);
        if (text_title_size != 0) {
            try {
                this.tv_title.setTextSize(text_title_size/getResources().getDisplayMetrics().scaledDensity);
            }catch (Exception ignored){}
        }

        //--------------------------------------------
        rv_select.setOnClickListener(this);
        rv_unselect.setOnClickListener(this);
        ll_drop.setOnClickListener(this);
        cl_drop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rv_tagview_select || id == R.id.cl_drop || id == R.id.ll_drop) {
            if (ll_tagview_unselect.getVisibility() == VISIBLE) {
                iv_drop.setRotation(180);
                ll_tagview_unselect.setVisibility(GONE);
            } else {
                iv_drop.setRotation(0);
                ll_tagview_unselect.setVisibility(VISIBLE);
            }
        }
    }

    public List<tagModel> getTagModelList_select() {
        return tagModelList_select;
    }

    public List<tagModel> getTagModelList_unselect() {
        return tagModelList_unselect;
    }

    public void setTagModelList_unselect(List<tagModel> tagModelList_unselect) {
        this.tagModelList_unselect = tagModelList_unselect;
        recycleView_unselect = new RecycleView_Unselect(tagModelList_unselect,this);
        try {
            recycleView_unselect.setItemTextSize(itemUnSelect_text_size/getResources().getDisplayMetrics().scaledDensity);
        }catch (Exception ignored){}

        recycleView_unselect.setItemTextColor(itemUnSelect_text_color);
        recycleView_unselect.setItemDrawable(itemUnSelect_drawable);
        rv_unselect.setAdapter(recycleView_unselect);
    }

    public setOnAddListener getSetOnAddListener() {
        return setOnAddListener;
    }

    public void setSetOnAddListener(setOnAddListener setOnAddListener) {
        this.setOnAddListener = setOnAddListener;
    }

    @Override
    public void click_item_select(tagModel tagModel) {
        for (int i = 0; i < tagModelList_select.size(); i++) {
            if (tagModelList_select.get(i)==tagModel){
                tagModelList_select.remove(tagModel);
                recycleView_select.notifyDataSetChanged();
            }
        }
        tagModelList_unselect.add(tagModel);
        recycleView_unselect.notifyDataSetChanged();


        if (tagModelList_select.size() > 0){
            if(underLine_select_color != 0)
                underLine.setBackgroundColor(underLine_select_color);

            if (null!= text_alert && !text_alert.equals("")){
                tv_info.setVisibility(GONE);
                iv_icon_info.setVisibility(INVISIBLE);
            }
        }
        else {
            if (underLine_unselect_color != 0)
                underLine.setBackgroundColor(underLine_unselect_color);

            if (null!= text_alert && !text_alert.equals("")){
                tv_info.setVisibility(VISIBLE);
                iv_icon_info.setVisibility(VISIBLE);
            }
        }


        if (setOnAddListener!=null)setOnAddListener.getListSelected(tagModelList_select);
    }

    @Override
    public void click_item_unselect(tagModel tagModel) {
        for (int i = 0; i < tagModelList_unselect.size(); i++) {
            if (tagModelList_unselect.get(i)==tagModel){
                tagModelList_unselect.remove(tagModel);
                recycleView_unselect.notifyDataSetChanged();
            }
        }
        tagModelList_select.add(tagModel);
        recycleView_select.notifyDataSetChanged();

        if (tagModelList_select.size() > 0){
            if (underLine_select_color != 0)
                underLine.setBackgroundColor(underLine_select_color);

            if (null!= text_alert && !text_alert.equals("")){
                tv_info.setVisibility(GONE);
                iv_icon_info.setVisibility(INVISIBLE);
            }
        }
        else {
            if (underLine_unselect_color != 0)
                underLine.setBackgroundColor(underLine_unselect_color);

            if (null!= text_alert && !text_alert.equals("")){
                tv_info.setVisibility(VISIBLE);
                iv_icon_info.setVisibility(VISIBLE);
            }
        }

        if (setOnAddListener!=null)setOnAddListener.getListSelected(tagModelList_select);
    }

    //--------------------------------------------------

    public void showAlert(String alert){
        this.text_alert = alert;
        if (null!= alert && !alert.equals("")){
            tv_info.setText(alert);
        }
        tv_info.setVisibility(View.VISIBLE);
        iv_icon_info.setVisibility(VISIBLE);
        if (icon_alert != 0)
            iv_icon_info.setImageResource(icon_alert);

    }

    public void hiddenAlert(){
        tv_info.setText("");
        tv_info.setVisibility(View.GONE);
        iv_icon_info.setVisibility(INVISIBLE);
    }

    //-------------------------------------------------


    public void setText_title_color(@ColorInt int text_title_color) {
        this.text_title_color = text_title_color;
        this.tv_title.setTextColor(text_title_color);
    }

    public void setText_title_size(int text_title_size) {
        this.text_title_size = text_title_size;
        this.tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,text_title_size);
    }

    public void setUnderLine_select_color(@ColorInt int underLine_select_color) {
        this.underLine_select_color = underLine_select_color;
        this.underLine.setBackgroundColor(underLine_select_color);
    }

    public void setUnderLine_unselect_color(@ColorInt int underLine_unselect_color) {
        this.underLine_unselect_color = underLine_unselect_color;
        this.underLine.setBackgroundColor(underLine_unselect_color);
    }

    public void setTextAlert_color(@ColorInt int textAlert_color) {
        this.textAlert_color = textAlert_color;
        this.tv_info.setTextColor(textAlert_color);
    }

    public void setIcon_alert(@DrawableRes int icon_alert) {
        this.icon_alert = icon_alert;
        this.iv_icon_info.setImageResource(icon_alert);
    }

    public void setIcon_drop(@DrawableRes int icon_drop) {
        this.icon_drop = icon_drop;
        this.iv_drop.setImageResource(icon_drop);
    }

    public void setItemSelect_text_color(@ColorInt int itemSelect_text_color) {
        this.itemSelect_text_color = itemSelect_text_color;
        recycleView_select.setItemTextColor(itemSelect_text_color);
        recycleView_select.notifyDataSetChanged();
    }

    public void setItemSelect_text_size(int itemSelect_text_size) {
        this.itemSelect_text_size = itemSelect_text_size;
        recycleView_select.setItemTextSize(itemSelect_text_size);
        recycleView_select.notifyDataSetChanged();
    }

    public void setItemSelect_drawable(@DrawableRes int itemSelect_drawable) {
        this.itemSelect_drawable = itemSelect_drawable;
        recycleView_select.setItemDrawable(itemSelect_drawable);
        recycleView_select.notifyDataSetChanged();
    }

    public void setItemUnSelect_text_color(@ColorInt int itemUnSelect_text_color) {
        this.itemUnSelect_text_color = itemUnSelect_text_color;
        recycleView_unselect.setItemTextColor(itemUnSelect_text_color);
    }

    public void setItemUnSelect_text_size(int itemUnSelect_text_size) {
        this.itemUnSelect_text_size = itemUnSelect_text_size;
        recycleView_unselect.setItemTextSize(itemUnSelect_text_size);
    }

    public void setItemUnSelect_drawable(@DrawableRes int itemUnSelect_drawable) {
        this.itemUnSelect_drawable = itemUnSelect_drawable;
        recycleView_unselect.setItemDrawable(itemUnSelect_drawable);
    }
}
