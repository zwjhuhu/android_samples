package net.zwj.tools.dialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.zwj.tools.R;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 选择日期对话框
 */
public class SelectDateDialog extends Dialog implements
		DatePickerDialog.OnDateSetListener {

	private DatePickerDialog datePicker;

	private Button okBtn;
	private Button cancelBtn;
	private Button resetBtn;
	private EditText startDateET;
	private EditText endDateET;

	private Date startDate;
	private Date endDate;

	private enum DATETYPE {
		STARTDATE, ENDDATE
	}

	private DATETYPE datetype;

	public SelectDateDialog(Context context) {
		super(context, R.style.select_date_dialog);
		setTitle(R.string.select_date_dialog_default_title);
	}

	private OnButtonClickListener listener;

	public void removeListener() {
		this.listener = null;
	}

	public void setListener(OnButtonClickListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.select_date_dialog);

		// 初始化日期选择框
		Calendar calendar = Calendar.getInstance();
		datePicker = new DatePickerDialog(getContext(), this,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE));

		startDateET = (EditText) findViewById(R.id.startDate);
		startDateET.setCursorVisible(false);
		startDateET.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					datetype = DATETYPE.STARTDATE;
					datePicker.show();
					return true;
				}
				return false;
			}
		});

		endDateET = (EditText) findViewById(R.id.endDate);
		endDateET.setCursorVisible(false);
		endDateET.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					datetype = DATETYPE.ENDDATE;
					datePicker.show();
					return true;
				}
				return false;
			}
		});

		if (listener == null) {
			listener = new OnButtonClickListener() {

				@Override
				public void onOkClick(SelectDateDialog dialog, Date startDate,
						Date endDate) {
					StringBuilder sb = new StringBuilder();
					sb.append("startDate: ").append(startDate).append('\n')
							.append("endDate: ").append(endDate);
					Toast.makeText(getContext(), sb.toString(),
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onCancelClick(SelectDateDialog dialog) {
					dismiss();
				}

			};
		}

		okBtn = (Button) findViewById(R.id.ok);
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onOkClick(SelectDateDialog.this, startDate,
							endDate);
				}
			}
		});

		resetBtn = (Button) findViewById(R.id.reset);
		resetBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startDate = null;
				startDateET.setText("");

				endDate = null;
				endDateET.setText("");
			}
		});

		cancelBtn = (Button) findViewById(R.id.cancel);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onCancelClick(SelectDateDialog.this);
				}
			}
		});
	}

	public interface OnButtonClickListener {
		void onOkClick(SelectDateDialog dialog, Date startDate, Date endDate);

		void onCancelClick(SelectDateDialog dialog);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {

		Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth)
				.getTime();
		String dateStr = DateFormat.format("yyyy-MM-dd", date).toString();
		switch (datetype) {
		case STARTDATE:
			startDateET.setText(dateStr);
			startDate = date;
			break;
		case ENDDATE:
			endDateET.setText(dateStr);
			endDate = date;
			break;
		}
	}

	// private void hideIM(View edt) {
	// try {
	// InputMethodManager im = (InputMethodManager) getContext()
	// .getSystemService(Activity.INPUT_METHOD_SERVICE);
	// IBinder windowToken = edt.getWindowToken();
	// if (windowToken != null) {
	// im.hideSoftInputFromWindow(windowToken, 0);
	// }
	// } catch (Exception e) {
	//
	// }
	// }
}
