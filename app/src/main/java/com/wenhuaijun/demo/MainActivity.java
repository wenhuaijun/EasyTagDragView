package com.wenhuaijun.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wenhuaijun.easytagdragview.DragDropListView;
import com.wenhuaijun.easytagdragview.IDragEntity;
import com.wenhuaijun.easytagdragview.R;
import com.wenhuaijun.easytagdragview.SimpleDragEntity;
import com.wenhuaijun.easytagdragview.TagItemView;
import com.wenhuaijun.easytagdragview.adapter.AbsTileAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsTileAdapter.DragDropListener,TagItemView.OnSelectedListener {
    private DragDropListView dragDropListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragDropListView =(DragDropListView)findViewById(R.id.tagdrag_view);
        TagAdapter adapter = new TagAdapter(this, this,this);
        adapter.setData(obtainData());
        //添加拖拽监听，监听写在Adapter里
        dragDropListView.getDragDropController().addOnDragDropListener(adapter);
        dragDropListView.setDragShadowOverlay((ImageView) findViewById(R.id.tile_drag_shadow_overlay));
        dragDropListView.setAdapter(adapter);
    }

    private List<IDragEntity> obtainData() {
        List<IDragEntity> list = new ArrayList<>();
        for (int i = 0; i <= 24; i++) {
            SimpleDragEntity entry = new SimpleDragEntity();
            entry.setId(i);
            entry.setTag(i + " Item");
            list.add(entry);
        }
        return list;
    }

    @Override
    public DragDropListView getDragDropListView() {
        return dragDropListView;
    }

    @Override
    public void onDataSetChangedForResult(ArrayList<IDragEntity> list) {
        Log.i("heheda",list.toString());
    }

    //item被点击时
    @Override
    public void onTileSelected(IDragEntity entity, int position, View view) {
        Toast.makeText(MainActivity.this, ((SimpleDragEntity) entity).getTag(), Toast.LENGTH_SHORT).show();
    }
}
