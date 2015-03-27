package net.zwj.tools;

import java.util.ArrayList;
import java.util.List;

import net.zwj.tools.dialog.AlertDialog;
import net.zwj.tools.dialog.ConfirmDialog;
import net.zwj.tools.dialog.SelectDateDialog;
import android.view.View;
import android.widget.AdapterView;

public class DialogDemoActivity extends DemoBaseActivity {

	@Override
	protected List<String> createDemoList() {
		List<String> list = new ArrayList<String>();
		list.add("alert dialog");
		list.add("confirm dialog");
		list.add("selectdatedialog");
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			AlertDialog.alert(DialogDemoActivity.this, "test");
			break;
		case 1:
			ConfirmDialog.confirm(DialogDemoActivity.this, "test", null);
			break;
		case 2:
			new SelectDateDialog(DialogDemoActivity.this).show();
			break;

		default:
			break;
		}

	}

}
