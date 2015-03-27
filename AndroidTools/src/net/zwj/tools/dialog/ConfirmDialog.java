package net.zwj.tools.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import net.zwj.tools.R;

public class ConfirmDialog {

	public static void confirm(Context context, String msg, DialogInterface.OnClickListener ok) {
		confirm(context, null, msg, ok, null, null, null);
	}

	public static void confirm(Context context, String title, String msg, DialogInterface.OnClickListener ok) {
		confirm(context, title, msg, ok, null, null, null);
	}

	public static void confirm(Context context, String title, String msg, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener cancle) {
		confirm(context, title, msg, ok, null, null, null);
	}

	public static void confirm(Context context, String title, String msg, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener cancle, String okMsg, String cancleMsg) {
		if (title == null)
			title = context.getString(R.string.confirm_dialog_default_title);

		if (ok == null) {
			ok = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
		}

		if (cancle == null) {
			cancle = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
		}

		if (okMsg == null || "".equals(okMsg))
			okMsg = context.getString(R.string.confirm_dialog_default_btn_ok);

		if (cancleMsg == null || "".equals(cancleMsg))
			cancleMsg = context.getString(R.string.confirm_dialog_default_btn_cancel);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(okMsg, ok);
		builder.setNegativeButton(cancleMsg, cancle);
		builder.setCancelable(false);
		builder.create().show();
	}

}
