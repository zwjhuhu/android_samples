package net.zwj.tools.dialog;

import net.zwj.tools.R;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialog {

	public static void alert(Context context, String msg) {
		AlertDialog.alert(context, null, msg, null);
	}

	public static void alert(Context context, String msg, DialogInterface.OnClickListener onclick) {
		AlertDialog.alert(context, null, msg, onclick);
	}

	public static void alert(Context context, String title, String showmsg, DialogInterface.OnClickListener onclick) {
		if (title == null)
			title = context.getString(R.string.alert_dialog_default_title);

		if (onclick == null) {
			onclick = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
		}

		String btnStr = context.getString(R.string.alert_dialog_default_btn_ok);
		
		android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context).create();
		dialog.setTitle(title);
		dialog.setMessage(showmsg);
		dialog.setButton(btnStr, onclick);
		dialog.show();
	}
}
