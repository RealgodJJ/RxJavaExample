package com.example.realgodjj.rxjavademo.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realgodjj.rxjavademo.R;

public class MaxInputTextWatcher implements TextWatcher {
    private Context context;
    private EditText editText = null;
    private int maxLength = 0;

    public MaxInputTextWatcher(Context context, EditText editText, int maxLength) {
        this.context = context;
        this.editText = editText;
        this.maxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Editable editable = editText.getText();
        int length = editable.length();
        if (length > maxLength) {
            int selectEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            String newStr = str.substring(0, maxLength);
            editText.setText(newStr);
            editable = editText.getText();
            int newLength = editable.length();
            if (selectEndIndex > newLength) {
                selectEndIndex = editable.length();
                Toast.makeText(context, R.string.word_limit, Toast.LENGTH_SHORT).show();
            }
            Selection.setSelection(editable, selectEndIndex);//设置光标的新位置
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
