package com.wenhuaijun.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
    private GridView addGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragDropListView =(DragDropListView)findViewById(R.id.tagdrag_view);
        addGridView =(GridView)findViewById(R.id.add_gridview);
        final TagAdapter adapter = new TagAdapter(this, this,this);
        adapter.setData(obtainData());
        //添加拖拽监听，监听写在Adapter里
        dragDropListView.getDragDropController().addOnDragDropListener(adapter);
        dragDropListView.setDragShadowOverlay((ImageView) findViewById(R.id.tile_drag_shadow_overlay));
        dragDropListView.setAdapter(adapter);

        final AddGridAdapter addGridAdapter = new AddGridAdapter(new ArrayList<IDragEntity>(obtainAddData()));
        addGridView.setAdapter(addGridAdapter);
        addGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.mDragEntries.add(addGridAdapter.getiDragEntities().get(position));
                adapter.reFresh();
                addGridAdapter.getiDragEntities().remove(position);
                addGridAdapter.notifyDataSetChanged();

            }
        });
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

    private List<IDragEntity> obtainAddData() {
        List<IDragEntity> list = new ArrayList<>();
        for (int i = 25; i <= 35; i++) {
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
