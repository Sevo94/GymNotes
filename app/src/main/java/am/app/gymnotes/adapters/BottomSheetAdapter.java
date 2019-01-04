package am.app.gymnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import am.app.gymnotes.R;

public class BottomSheetAdapter extends BaseAdapter {
    public static class BottomSheetAdapterItem {
        public BottomSheetAdapterItem(int iconResID, int textResID) {
            this.iconResID = iconResID;
            this.textResID = textResID;
        }

        int getIconResID() {
            return iconResID;
        }

        public void setIconResID(int _iconResID) {
            this.iconResID = _iconResID;
        }

        int getTextResID() {
            return textResID;
        }

        public void setTextResID(int _textResID) {
            this.textResID = _textResID;
        }

        private int iconResID;
        private int textResID;
    }

    private List<BottomSheetAdapterItem> objectList = new ArrayList<>();
    private final LayoutInflater inflater;

    public BottomSheetAdapter(Context context, List<BottomSheetAdapterItem> _items) {
        this.inflater = LayoutInflater.from(context);
        this.objectList = _items;
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public Object getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BottomSheetHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bottom_sheet_list_item, parent, false);
            holder = new BottomSheetHolder();
            holder.sheetItemText = convertView.findViewById(R.id.sheet_item_text);
            holder.icon = convertView.findViewById(R.id.sheet_item_icon);
            convertView.setTag(holder);
        } else {
            holder = (BottomSheetHolder) convertView.getTag();
        }
        BottomSheetAdapterItem item = objectList.get(position);
        holder.sheetItemText.setText(item.getTextResID());
        if (item.getIconResID() == 0) {
            holder.icon.setVisibility(View.GONE);
        } else {
            holder.icon.setImageResource(item.getIconResID());
        }


        return convertView;
    }

    private class BottomSheetHolder {
        TextView sheetItemText;
        ImageView icon;

    }
}
