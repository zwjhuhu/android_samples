package net.zwj.shudu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class KeyDialog extends Dialog {
	private final View keys[] = new View[9];
	private final ShuduView shuduView;

	public KeyDialog(Context context, ShuduView shuduView) {
		super(context);
		this.shuduView = shuduView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.keydialog_title);
		setContentView(R.layout.keypad);
		findViews();
		setListeners();
	}

	public void resetBtns(int[] used) {
		int size = keys.length;
		for (int i = 0; i < size; i++) {
			keys[i].setVisibility(View.VISIBLE);
		}
		size = used.length;
		for (int i = 0; i < size; i++) {
			if (used[i] != 0) {
				keys[used[i] - 1].setVisibility(View.INVISIBLE);
			}
		}
	}

	private void setListeners() {
		int size = keys.length;
		for (int i = 0; i < size; i++) {
			final int t = i + 1;
			keys[i].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					returnResult(t);
				}
			});
		}

	}

	private void findViews() {
		keys[0] = findViewById(R.id.keypad_1);
		keys[1] = findViewById(R.id.keypad_2);
		keys[2] = findViewById(R.id.keypad_3);
		keys[3] = findViewById(R.id.keypad_4);
		keys[4] = findViewById(R.id.keypad_5);
		keys[5] = findViewById(R.id.keypad_6);
		keys[6] = findViewById(R.id.keypad_7);
		keys[7] = findViewById(R.id.keypad_8);
		keys[8] = findViewById(R.id.keypad_9);
	}

	private void returnResult(int tile) {
		shuduView.setSelectedTile(tile);
		hide();
	}

}
