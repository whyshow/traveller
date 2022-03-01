package club.ccit.widget.dialog.city.adapter;

import android.content.Context;

import java.util.List;

/**
 * FileName: ArrayWheelAdapter
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:49 上午
 * Description:
 * Version:
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private List<T> items;

    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public ArrayWheelAdapter(Context context, List<T> items) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            T item = items.get(index);
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}
