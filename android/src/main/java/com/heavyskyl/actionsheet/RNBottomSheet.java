package com.heavyskyl.actionsheet;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.cocosw.bottomsheet.BottomSheet;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;

import java.util.ArrayList;
import java.util.List;

class RNBottomSheet extends ReactContextBaseJavaModule {
    public RNBottomSheet(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNBottomSheet";
    }

    @ReactMethod
    public void showActionSheetWithOptions(ReadableMap options, final Callback onSelect) {
        ReadableArray optionArray = options.getArray("options");
        final Integer cancelButtonIndex = options.getInt("cancelButtonIndex");

        final BottomSheet.Builder builder = new BottomSheet.Builder(this.getCurrentActivity());

        // create options
        Integer size = optionArray.size();
        for (int i = 0; i < size; i++) {
            builder.sheet(i, optionArray.getString(i));
        }

        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == cancelButtonIndex) {
                    dialog.dismiss();
                } else {
                    onSelect.invoke(which);
                }
            }
        });

        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builder.build().show();
            }
        });
    }

    @ReactMethod
    public void showShareActionSheetWithOptions(ReadableMap options, Callback failureCallback, Callback successCallback) {
        String url = options.getString("url");
        String message = options.getString("message");
        String subject = options.getString("subject");
        String title = options.getString("title");

        List<String> items = new ArrayList<>();
        if (message != null && !message.isEmpty()) {
            items.add(message);
        }

        final Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.parse(url);
        if (uri != null) {
            if (uri.getScheme() != null && "data".equals(uri.getScheme().toLowerCase())) {
                shareIntent.setType("*/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            } else {
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_EMAIL, url);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            }
        }

        if (shareIntent.resolveActivity(this.getCurrentActivity().getPackageManager()) != null) {
            this.getCurrentActivity().startActivity(Intent.createChooser(shareIntent, title));
        } else {
            failureCallback.invoke(new Exception("The app you want to share is not installed."));
        }
    }

}
