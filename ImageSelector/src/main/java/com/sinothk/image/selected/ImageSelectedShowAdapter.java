package com.sinothk.image.selected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sinothk.image.selector.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  创建:  梁玉涛 2019/3/28 on 16:41
 *  项目:  ImageSelectorLib
 *  描述:
 *  更新:
 * <pre>
 */
public class ImageSelectedShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_ADD = 0;//添加图片
    private static int TYPE_COMMON = 1;//普通图片展示
    private Context context;
    private LayoutInflater mLayoutInflater;
    //data
    private int mMaxAlbum;//最大选择图片的数量
    private List<String> mStringList;//图片url集合

    public ImageSelectedShowAdapter(Context context, List<String> mStringList, int maxAlbum) {
        this.context = context;
        this.mMaxAlbum = maxAlbum;
        this.mLayoutInflater = LayoutInflater.from(context);

        this.mStringList = mStringList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            return new ItemViewHolderAdd(mLayoutInflater.inflate(R.layout.image_selected_view_add, parent, false));
        } else {
            return new ItemViewHolderCommon(mLayoutInflater.inflate(R.layout.image_selected_view_common, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        View itemView = null;
        if (holder instanceof ItemViewHolderAdd) {

            ItemViewHolderAdd itemViewHolderAdd = (ItemViewHolderAdd) holder;
            if (position >= mMaxAlbum) {
                itemViewHolderAdd.itemView.setVisibility(View.GONE);
            } else {
//                itemViewHolderAdd.tvNum.setText(position + "/" + mMaxAlbum);
                itemViewHolderAdd.itemView.setVisibility(View.VISIBLE);

                itemView = ((ItemViewHolderAdd) holder).itemView;
            }

        } else if (holder instanceof ItemViewHolderCommon) {

            String url = mStringList.get(position);
            Glide.with(context).load(new File(url)).placeholder(R.color.colorAccent).into(((ItemViewHolderCommon) holder).ivCommon);
            itemView = ((ItemViewHolderCommon) holder).itemView;

            ((ItemViewHolderCommon) holder).delImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemDelClick != null) {
                        int position = holder.getLayoutPosition();
                        itemDelClick.onItemDelClick(position);
                    }
                }
            });

        }
        if (mOnItemClickListener != null && null != itemView) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == mStringList.size() ? TYPE_ADD : TYPE_COMMON;
    }

    @Override
    public int getItemCount() {
        return mStringList.size() + 1;//加一代表最后一个添加图片按钮
    }

    public void setData(ArrayList<String> path) {
        if (path == null) {
            path = new ArrayList<>();
        }

        if (this.mStringList != null) {
            this.mStringList.clear();
        }

        this.mStringList = path;
        notifyDataSetChanged();
    }

    public static class ItemViewHolderAdd extends RecyclerView.ViewHolder {
//        private TextView tvNum;

        public ItemViewHolderAdd(View itemView) {
            super(itemView);
//            tvNum = itemView.findViewById(R.id.tv_album_selected_num);
        }
    }

    public static class ItemViewHolderCommon extends RecyclerView.ViewHolder {
        private ImageView ivCommon;
        private ImageView delImgBtn;

        public ItemViewHolderCommon(View itemView) {
            super(itemView);
            ivCommon = itemView.findViewById(R.id.iv_album_selected);
            delImgBtn = itemView.findViewById(R.id.delImgBtn);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    private OnItemDelClickListener itemDelClick;

    public void setItemDelClick(OnItemDelClickListener itemDelClick) {
        this.itemDelClick = itemDelClick;
    }

    public interface OnItemDelClickListener {
        void onItemDelClick(int position);
    }
}
