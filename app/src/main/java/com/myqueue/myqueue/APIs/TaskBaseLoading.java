package com.myqueue.myqueue.APIs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class TaskBaseLoading {

        private ProgressDialog mProgressDialog;

        private String mMessage;

        public TaskBaseLoading(Context p_context) {
            mProgressDialog = new ProgressDialog(p_context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(Html.fromHtml(mMessage = getDefaultMessage()));
        }

        public final TaskBaseLoading setOnCancelListener(DialogInterface.OnCancelListener p_listener) {
            mProgressDialog.setOnCancelListener(p_listener);
            return this;
        }

        public final TaskBaseLoading setMessage(String p_message) {
            mProgressDialog.setMessage(Html.fromHtml(mMessage = p_message));
            return this;
        }

        public final String getMessage() {
            return mMessage;
        }

        public final String getDefaultMessage() {
            return "<b>Please wait . . . </b>";
        }

        public final TaskBaseLoading setCancelable(boolean p_cancelable) {
            mProgressDialog.setCancelable(p_cancelable);
            return this;
        }

        public final void show() {
            mProgressDialog.show();
        }

        public final void dismiss() {
            mProgressDialog.dismiss();
        }

        public final boolean isShowing() {
            return mProgressDialog.isShowing();
        }

}
