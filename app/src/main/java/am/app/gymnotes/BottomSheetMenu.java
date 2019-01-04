package am.app.gymnotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BottomSheetMenu extends BottomSheetDialogFragment {
    private BottomSheetClickListener mOnClickListener;
    private BottomSheetDismissListener mOnDismissListener;
    private String title;
    private BaseAdapter adapter;
    private boolean hasTitle;
    private TextView mTitleView;
    private View mDividerLine;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        mTitleView = v.findViewById(R.id.contact_name);
        mDividerLine = v.findViewById(R.id.divider_line);
        mTitleView.setText(title);
        setTitleVisibility(hasTitle);

        ListView lvMain = v.findViewById(R.id.call_type_list);

        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BottomSheetMenu.this.dismiss();
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(position);
                }
            }
        });
        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mOnDismissListener != null) {
            mOnDismissListener.onBottomSheetDismiss();
        }
        super.onDismiss(dialog);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
        setTitleVisibility(hasTitle);
    }

    private void setTitleVisibility(boolean _isVisible) {
        if (mTitleView != null) {
            if (_isVisible) {
                mTitleView.setVisibility(View.VISIBLE);
                mDividerLine.setVisibility(View.VISIBLE);
            } else {
                mTitleView.setVisibility(View.GONE);
                mDividerLine.setVisibility(View.GONE);
            }
        }
    }

    public void setOnDismissListener(BottomSheetDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    public void setOnItemClickListener(BottomSheetClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface BottomSheetDismissListener {
        void onBottomSheetDismiss();
    }
}
