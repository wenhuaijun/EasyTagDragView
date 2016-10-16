package com.wenhuaijun.easytagdragview.widget;

import android.view.View;

import com.wenhuaijun.easytagdragview.listener.OnDragDropListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles and combines drag events generated from multiple views, and then fires
 * off events to any OnDragDropListeners that have registered for callbacks.
 */
public class DragDropController {

    private final List<OnDragDropListener> mOnDragDropListeners =
            new ArrayList<OnDragDropListener>();
    private final DragItemContainer mDragItemContainer;
    private final int[] mLocationOnScreen = new int[2];

    /**
     * Callback interface used to retrieve（取回） views based on the current touch coordinates（坐标） of the
     * drag event. The {@link DragItemContainer} houses the draggable views that this
     * {@link DragDropController} controls.
     */
    public interface DragItemContainer {
        View getViewForLocation(int x, int y);
    }

    public DragDropController(DragItemContainer dragItemContainer) {
        mDragItemContainer = dragItemContainer;
    }

    /**
     * @return True if the drag is started, false if the drag is cancelled for some reason.
     */
    boolean handleDragStarted(int x, int y) {
        final View tileView = mDragItemContainer.getViewForLocation(x, y);
        if (tileView == null) {
            return false;
        }
        for (int i = 0; i < mOnDragDropListeners.size(); i++) {
            mOnDragDropListeners.get(i).onDragStarted(x, y, tileView);
        }

        return true;
    }

    public void handleDragHovered(View v, int x, int y) {
        v.getLocationOnScreen(mLocationOnScreen);
        final int screenX = x + mLocationOnScreen[0];
        final int screenY = y + mLocationOnScreen[1];
        final View view = mDragItemContainer.getViewForLocation(
                x, y);
        if(view == null){
            return;
        }
        for (int i = 0; i < mOnDragDropListeners.size(); i++) {
            mOnDragDropListeners.get(i).onDragHovered(x, y, view);
        }
    }

    public void handleDragFinished(int x, int y, boolean isRemoveView) {
        if (isRemoveView) {
            for (int i = 0; i < mOnDragDropListeners.size(); i++) {
                mOnDragDropListeners.get(i).onDroppedOnRemove();
            }
        }

        for (int i = 0; i < mOnDragDropListeners.size(); i++) {
            mOnDragDropListeners.get(i).onDragFinished(x, y);
        }
    }

    public void addOnDragDropListener(OnDragDropListener listener) {
        if (!mOnDragDropListeners.contains(listener)) {
            mOnDragDropListeners.add(listener);
        }
    }

    public void removeOnDragDropListener(OnDragDropListener listener) {
        if (mOnDragDropListeners.contains(listener)) {
            mOnDragDropListeners.remove(listener);
        }
    }

}