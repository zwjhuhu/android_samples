package net.zwj.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AppStartActivity extends Activity {

	private void initToolButtons() {
		Button btn;
		btn = (Button) findViewById(R.id.btn_dialog);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AppStartActivity.this, DialogDemoActivity.class);
				AppStartActivity.this.startActivity(intent);
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		initToolButtons();
	}
}
