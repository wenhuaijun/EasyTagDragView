package com.wenhuaijun.easytagdragview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenhuaijun.easytagdragview.adapter.AddTipAdapter;
import com.wenhuaijun.easytagdragview.adapter.DragTipAdapter;
import com.wenhuaijun.easytagdragview.adapter.AbsTipAdapter;
import com.wenhuaijun.easytagdragview.bean.Tip;
import com.wenhuaijun.easytagdragview.widget.DragDropGirdView;
import com.wenhuaijun.easytagdragview.widget.TipItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wenhuaijun on 2016/5/27 0027.
 */
public class EasyTipDragView extends RelativeLayout implements AbsTipAdapter.DragDropListener, TipItemView.OnDeleteClickListener,View.OnClickListener{
    private DragDropGirdView dragDropGirdView;
    private GridView addGridView;
    private ImageView closeImg;
    private TextView completeTv;
    private AddTipAdapter addTipAdapter;
    private DragTipAdapter dragTipAdapter;
    private OnDataChangeResultCallback dataResultCallback;
    private OnCompleteCallback completeCallback;
    private ArrayList<Tip> lists;
    private boolean isOpen= false;
    public EasyTipDragView(Context context) {
        super(context);
        initView();
    }

    public EasyTipDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EasyTipDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyTipDragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    private void initView(){
        if(isInEditMode()){
            return ;
        }
        close();
        dragTipAdapter = new DragTipAdapter(getContext(),this,this);
        dragTipAdapter.setFirtDragStartCallback(new DragTipAdapter.OnFirstDragStartCallback() {
            @Override
            public void firstDragStartCallback() {
                //第一次开始拖动item触发回调
                closeImg.setVisibility(View.GONE);
                completeTv.setVisibility(View.VISIBLE);
            }
        });
        addTipAdapter = new AddTipAdapter();
        //加载view
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.view_easytagdrag,this);
        closeImg =(ImageView)view.findViewById(R.id.drag_close_img);
        completeTv =(TextView)view.findViewById(R.id.drag_finish_tv);
        dragDropGirdView =(DragDropGirdView)view.findViewById(R.id.tagdrag_view);
        dragDropGirdView.getDragDropController().addOnDragDropListener(dragTipAdapter);

        dragDropGirdView.setDragShadowOverlay((ImageView) view.findViewById(R.id.tile_drag_shadow_overlay));
        dragDropGirdView.setAdapter(dragTipAdapter);
        addGridView =(GridView)view.findViewById(R.id.add_gridview);
        addGridView.setAdapter(addTipAdapter);
        addGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dragTipAdapter.getData().add(addTipAdapter.getData().get(position));
                dragTipAdapter.refreshData();
                addTipAdapter.getData().remove(position);
                addTipAdapter.refreshData();
            }
        });
        closeImg.setOnClickListener(this);
        completeTv.setOnClickListener(this);
    }

    @Override
    public DragDropGirdView getDragDropGirdView() {
        return dragDropGirdView;
    }

    @Override
    public void onDataSetChangedForResult(ArrayList<Tip> lists) {
        this.lists =lists;
        if(dataResultCallback!=null){
            dataResultCallback.onDataChangeResult(lists);
        }
    }

    @Override
    public void onDeleteClick(Tip entity, int position, View view) {
        addTipAdapter.getData().add(entity);
        addTipAdapter.refreshData();
        dragTipAdapter.getData().remove(position);
        dragTipAdapter.refreshData();
    }
    public void setDragData(List<Tip> tips){
        dragTipAdapter.setData(tips);
    }
    public void setAddData(List<Tip> tips){
        lists = new ArrayList<>(tips);
        addTipAdapter.setData(tips);
    }
    public void setDataResultCallback(OnDataChangeResultCallback dataResultCallback) {
        this.dataResultCallback = dataResultCallback;
    }
    public void setOnCompleteCallback(OnCompleteCallback callback){
        this.completeCallback =callback;
    }

    public void setSelectedListener(TipItemView.OnSelectedListener selectedListener) {
        dragTipAdapter.setItemSelectedListener(selectedListener);
    }
    public void close(){
        setVisibility(View.GONE);
        isOpen =false;
    }
    public void open(){
        setVisibility(View.VISIBLE);
        isOpen =true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.drag_close_img:
                //关闭，不回调数据
                close();
                break;
            case R.id.drag_finish_tv:
                //完成关闭，回调数据
                dragTipAdapter.cancelEditingStatus();
                if(completeCallback!=null){
                    completeCallback.onComplete(lists);
                }
                close();
                break;
        }
    }
    //每次由于拖动排序,添加或者删除item时会回调
    public interface OnDataChangeResultCallback{
        void onDataChangeResult(ArrayList<Tip> tips);
    }
    //在最后点击"完成"关闭EasyTipDragView时回调
    public interface OnCompleteCallback{
        void onComplete(ArrayList<Tip> tips);
    }

    public boolean isOpen() {
        return isOpen;
    }
    //点击返回键监听
    public boolean onKeyBackDown(){
        //如果处于编辑模式，则取消编辑模式
        if(dragTipAdapter.isEditing()){
            dragTipAdapter.cancelEditingStatus();
            return true;
        }else{
            //关闭该view
            close();
            return false;
        }
    }
}
