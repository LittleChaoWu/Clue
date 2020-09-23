package com.sfb.baselib.widget.time;

import android.view.View;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.view.TimePickerView;

public class TimePicker extends TimePickerView {

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public TimePicker(PickerOptions pickerOptions) {
        super(pickerOptions);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        } else if (tag.equals(TAG_CANCEL)) {
            onCancel();
        }
        dismiss();
    }


    void onCancel() {
        if (mPickerOptions.timeSelectListener instanceof OnTimePickListener) {
            OnTimePickListener listener = (OnTimePickListener) mPickerOptions.timeSelectListener;
            listener.onCancel();
        }
    }

}
