package com.wenhuaijun.demo;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wenhuaijun.easytagdragview.DragDropListView;
import com.wenhuaijun.easytagdragview.IDragEntity;
import com.wenhuaijun.easytagdragview.R;
import com.wenhuaijun.easytagdragview.SimpleDragEntity;
import com.wenhuaijun.easytagdragview.TagItemView;
import com.wenhuaijun.easytagdragview.adapter.AbsTileAdapter;

import java.util.ArrayList;

/**
 * Created by Wenhuaijun on 2016/5/26 0026.
 */
public class TagAdapter extends AbsTileAdapter implements View.OnLongClickListener,TagItemView.OnDeleteClickListener{
    private boolean  isEditing =false;
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    private TagItemView.OnSelectedListener mListener;
    public TagAdapter(Context context, DragDropListener dragDropListener,TagItemView.OnSelectedListener mListener) {
        super(context, dragDropListener);
        this.mListener =mListener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TagItemView view =null;
        if(convertView!=null&&convertView instanceof TagItemView){
            view =(TagItemView)convertView;
        }else{
            view = (TagItemView)View.inflate(mContext, R.layout.view_tag_item, null);
        }
        if(isEditing){
            view.showDeleteImg();
        }
        //设置点击监听
        view.setItemListener(position, mListener);
        view.setOnLongClickListener(this);
        //设置删除监听
        view.setDeleteClickListener(position, this);
        //绑定数据
        view.renderData(getItem(position));
        return view;
    }

    @Override
    protected IDragEntity getDragEntity(View view) {
        return ((TagItemView)view).getDragEntity();
    }

    @Override
    public boolean onLongClick(View v) {
        if(!isEditing){
            isEditing =true;
            notifyDataSetChanged();
        }
        v.startDrag(EMPTY_CLIP_DATA, new View.DragShadowBuilder(),
                DragDropListView.DRAG_FAVORITE_TILE, 0);
        return true;
    }
    //删除按钮点击时
    @Override
    public void onDeleteClick(IDragEntity entity, int position, View view) {

        Log.i("heheda", "删除：entity--->" + ((SimpleDragEntity) entity).getTag() + "  position: " + position + " mDragEntries: " + mDragEntries.get(position));
        mDragEntries.remove(position);
        notifyDataSetChanged();
        mDragDropListener.onDataSetChangedForResult(mDragEntries);

    }
}
