/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wenhuaijun.easytagdragview;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenhuaijun.easytagdragview.adapter.AbsTileAdapter;


/**
 * A TileView displays a picture and name
 */
public class TagItemView extends RelativeLayout {
    private final static String TAG = TagItemView.class.getSimpleName();
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    protected OnSelectedListener mListener;
    protected OnDeleteClickListener mDeleteListener;
    private IDragEntity mIDragEntity;
    private TextView title;
    private ImageView delete;
    private int position;

    public TagItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(createClickListener());
        title =(TextView)findViewById(R.id.tagview_title);
        delete =(ImageView)findViewById(R.id.tagview_delete);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteListener != null) {
                    mDeleteListener.onDeleteClick(mIDragEntity, position, TagItemView.this);
                }

            }
        });

    }

    protected View.OnClickListener createClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }

                mListener.onTileSelected(mIDragEntity,position,TagItemView.this);
            }
        };
    }

    public IDragEntity getDragEntity() {
        return mIDragEntity;
    }

    public void renderData(IDragEntity entity) {
        mIDragEntity = entity;

        if (entity != null && entity != AbsTileAdapter.BLANK_ENTRY) {

            if(entity instanceof SimpleDragEntity) {
                title.setText(((SimpleDragEntity) mIDragEntity).getTag());

            }
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.INVISIBLE);
        }
    }
    public void setDragDropListener(){
        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // NOTE The drag shadow is handled in the ListView.
                v.startDrag(EMPTY_CLIP_DATA, new View.DragShadowBuilder(),
                        DragDropListView.DRAG_FAVORITE_TILE, 0);
                return true;
            }
        });
    }
    public void setItemListener(int position, OnSelectedListener listener) {
        mListener = listener;
        this.position =position;
    }

    public void setDeleteClickListener(int position, OnDeleteClickListener listener){
        this.position =position;
        this.mDeleteListener =listener;
    }




    public interface OnSelectedListener {
        /**
         * Notification that the tile was selected; no specific action is dictated.
         */
        void onTileSelected(IDragEntity entity,int position,View view);

    }
    public interface OnDeleteClickListener{
        void onDeleteClick(IDragEntity entity,int position,View view);
    }
    public void showDeleteImg(){
        delete.setVisibility(View.VISIBLE);
    }
    public void hideDeleteImg(){
        delete.setVisibility(View.GONE);
    }
}
