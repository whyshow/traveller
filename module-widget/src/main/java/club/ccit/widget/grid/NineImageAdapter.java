package club.ccit.widget.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * FileName: NineImageAdapter
 *
 * @author: mosaic
 * Date: 2023/2/15 14:57
 * Description:
 * Version:
 */
public abstract class NineImageAdapter {
    protected abstract int getItemCount();

    protected abstract View createView(LayoutInflater inflater, ViewGroup parent, int position);

    protected   abstract void bindView(View view, int position);

    public void OnItemClick(int position, View view) {

    }
}
