package net.zwj.shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ShuduView extends View {

	private float width;
	private float height;

	private int selectedX;
	private int selectedY;
	private Game game;

	private KeyDialog keyDialog;

	private Paint paint = new Paint();

	public ShuduView(Context context, Game game) {
		super(context);
		this.game = game;
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.keyDialog = new KeyDialog(this.getContext(), this);
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		this.keyDialog.dismiss();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.width = w / 9f;
		this.height = h / 9f;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.reset();
		Paint backgroudPaint = paint;
		backgroudPaint.setColor(getResources()
				.getColor(R.color.shudu_backgroud));
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroudPaint);

		paint.reset();
		Paint drakPaint = paint;
		drakPaint.setColor(getResources().getColor(R.color.shudu_dark));

		paint.reset();
		Paint hilitePaint = paint;
		hilitePaint.setColor(getResources().getColor(R.color.shudu_hilite));

		paint.reset();
		Paint lightPaint = paint;
		lightPaint.setColor(getResources().getColor(R.color.shudu_light));

		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilitePaint);
			canvas.drawLine(i * width, 0, i * width, getHeight(), lightPaint);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilitePaint);
		}

		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0) {
				canvas.drawLine(0, i * height, getWidth(), i * height,
						drakPaint);
				canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
						hilitePaint);
				canvas.drawLine(i * width, 0, i * width, getHeight(), drakPaint);
				canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
						hilitePaint);
			}
		}

		paint.reset();
		Paint numberPaint = paint;
		numberPaint.setColor(Color.BLACK);
		numberPaint.setStyle(Paint.Style.STROKE);
		numberPaint.setTextSize(height * 0.75f);
		numberPaint.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = numberPaint.getFontMetrics();
		float x = width / 2;
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(game.getTileString(i, j), i * width + x, j
						* height + y, numberPaint);
				;
			}
		}
		super.onDraw(canvas);
	}

	@Override
	public boolean performClick() {
		super.performClick();
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		}else{
			performClick();
			selectedX = (int) (event.getX() / width);
			selectedY = (int) (event.getY() / height);
			int used[] = game.getUsedTilesByCoor(selectedX, selectedY);
			if (used.length < 9) {
				
				keyDialog.show();
				keyDialog.resetBtns(used);
			} else {
				Toast.makeText(getContext(), R.string.full_number,
						Toast.LENGTH_SHORT).show();
			}
		}
		return true;
	}

	public void setSelectedTile(int tile) {
		if (game.setTileIfValid(selectedX, selectedY, tile)) {
			invalidate();
		}
	}

}
