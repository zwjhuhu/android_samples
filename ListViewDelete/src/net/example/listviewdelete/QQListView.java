package net.example.listviewdelete;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class QQListView extends ListView {
	
	private static final String TAG = "QQListView";
	
	/**
	 * 用户滑动的最小距离
	 */
	private int touchSlop;

	/**
	 * 是否响应滑动
	 */
	private boolean isSliding;
	
	private boolean slidingIn;
	
	private boolean isDrag = false;

	/**
	 * 手指按下时的x坐标
	 */
	private int xDown;
	/**
	 * 手指按下时的y坐标
	 */
	private int yDown;
	/**
	 * 手指移动时的x坐标
	 */
	private int xMove;
	/**
	 * 手指移动时的y坐标
	 */
	private int yMove;

	private LayoutInflater mInflater;

	private PopupWindow mPopupWindow;
	private int mPopupWindowHeight;
	private int mPopupWindowWidth;

	private Button mDelBtn;
	/**
	 * 为删除按钮提供一个回调接口
	 */
	private DelButtonClickListener mListener;

	/**
	 * 当前手指触摸的View
	 */
	private View mCurrentView;

	private int mCurrentViewPos = -1;
	
	private int touchViwPos = -1;
	
	/**
	 * 必要的一些初始化
	 * 
	 * @param context
	 * @param attrs
	 */
	public QQListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mInflater = LayoutInflater.from(context);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();


		View view = mInflater.inflate(R.layout.delete_btn, null);
		mDelBtn = (Button) view.findViewById(R.id.id_item_btn);
		mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		/**
		 * 先调用下measure,否则拿不到宽和高
		 */
		mPopupWindow.getContentView().measure(0, 0);
		mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
		mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
		
		// 设置删除按钮的回调
		mDelBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.clickHappend(mCurrentViewPos);
					mPopupWindow.dismiss();
				}
			}
		});
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		int x = (int) ev.getX();
		int y = (int) ev.getY();

		switch (action)
		{

		case MotionEvent.ACTION_DOWN:
			xDown = x;
			yDown = y;
			touchViwPos = pointToPosition(xDown, yDown);
//			/**
//			 * 如果当前popupWindow显示，则直接隐藏，然后屏蔽ListView的touch事件的下传
//			 */
			if (!mPopupWindow.isShowing())
			{
				//dismissPopWindow();
				//return false;
				// 获得当前手指按下时的item的位置
				mCurrentViewPos = touchViwPos;
				// 获得当前手指按下时的item
				View view = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
				mCurrentView = view;
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			xMove = x;
			yMove = y;
			int dx = xMove - xDown;
			int dy = yMove - yDown;
			/**
			 * 判断是否是从右到左的滑动
			 */
			if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop)
			{
				if(!mPopupWindow.isShowing()){
					slidingIn = true;
					isSliding = true;
				}
			}else if(xMove > xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop){
				if(touchViwPos==mCurrentViewPos&&mPopupWindow.isShowing()){
					slidingIn = false;
					isSliding = true;
				}
			}
			if(!isSliding&&Math.abs(dy) > touchSlop){
				isDrag = true;
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();

		if (isSliding)
		{
			switch (action)
			{
			case MotionEvent.ACTION_MOVE:

				if(slidingIn){
					
					int[] location = new int[2];
					// 获得当前item的位置x与y
					mCurrentView.getLocationOnScreen(location);
					// 设置popupWindow的动画
					mPopupWindow.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
					mPopupWindow.update();
					mPopupWindow.showAtLocation(mCurrentView, Gravity.START | Gravity.TOP,
							location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight() / 2
							- mPopupWindowHeight / 2);
				}else{
					dismissPopWindow();
					mCurrentViewPos = -1;
				}
				// Log.e(TAG, "mPopupWindow.getHeight()=" + mPopupWindowHeight);
				isSliding = false;
				break;
			}
			// 相应滑动期间屏幕itemClick事件，避免发生冲突
			return true;
		}else{
			if(isDrag){
				switch (action)
				{
				case MotionEvent.ACTION_MOVE:
					if(mPopupWindow.isShowing()){
						int[] location = new int[2];
						// 获得当前item的位置x与y
						mCurrentView.getLocationOnScreen(location);
						mPopupWindow.update(location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight() / 2
								- mPopupWindowHeight / 2, mPopupWindow.getWidth(), mPopupWindow.getHeight());
					}
					break;
				}
				isDrag = false;
			}
			
			
		}

		return super.onTouchEvent(ev);
	}
	
	/**
	 * 隐藏popupWindow
	 */
	private void dismissPopWindow()
	{
		if (mPopupWindow != null && mPopupWindow.isShowing())
		{
			mPopupWindow.dismiss();
		}
	}

	public void setDelButtonClickListener(DelButtonClickListener listener)
	{
		mListener = listener;
	}

	interface DelButtonClickListener
	{
		public void clickHappend(int position);
	}

}
