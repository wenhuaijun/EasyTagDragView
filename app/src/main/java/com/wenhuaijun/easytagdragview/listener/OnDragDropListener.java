package com.wenhuaijun.easytagdragview.listener;


import android.view.View;

/**
 * Classes that want to receive callbacks in response to drag events should implement this
 * interface.
 */
public interface OnDragDropListener {
    /**
     * Called when a drag is started.
     * @param x X-coordinate of the drag event
     * @param y Y-coordinate of the drag event
     * @param view The tile view which the drag was started on
     */
    void onDragStarted(int x, int y, View view);

    /**
     * Called when a drag is in progress and the user moves the dragged contact to a
     * location.
     *
     * @param x X-coordinate of the drag event
     * @param y Y-coordinate of the drag event
     * @param view Tile view in the ListView which is currently being displaced
     * by the dragged tile
     */
    void onDragHovered(int x, int y, View view);

    /**
     * Called when a drag is completed (whether by dropping it somewhere or simply by dragging
     * the contact off the screen)
     * @param x X-coordinate of the drag event
     * @param y Y-coordinate of the drag event
     */
    void onDragFinished(int x, int y);

    /**
     * Called when a tile view has been dropped on the remove view, indicating that the user
     * wants to remove this contact.
     */
    void onDroppedOnRemove();
}