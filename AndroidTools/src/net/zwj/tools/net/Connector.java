package net.zwj.tools.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.zwj.tools.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 网络<br>
 * 需要权限：<uses-permission android:name="android.permission.INTERNET" />
 */
public class Connector {

	final private Context context;

	final public static int TIMEOUT_ERROR = -1;

	final private static int MSG_WHAT_RECEVIE_ERROR = 1;
	final private static int MSG_WHAT_RECEVIE_SUCCESS = 2;
	final private static int MSG_WHAT_RECEVING = 3;

	private Handler myHandler = null;

	private OnRecevieListener onRecevieListener;

	public void setOnRecevieListener(OnRecevieListener onRecevieListener) {
		this.onRecevieListener = onRecevieListener;
	}

	private String encoding = HTTP.UTF_8;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private Map<String, String> params = new HashMap<String, String>();

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		if (params != null)
			this.params.putAll(params);
	}

	public void putParam(String key, String value) {
		this.params.put(key, value);
	}

	private int bufferSize = 1024;

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	private String logTag = "";

	public void setLogTag(String logTag) {
		this.logTag = logTag;
	}

	private boolean showProgress = false;

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	private String progressMsg;

	public void setProgressMsg(String progressMsg) {
		this.progressMsg = progressMsg;
	}

	private ProgressDialog progress;

	public void setProgress(ProgressDialog progress) {
		this.progress = progress;
	}

	public Connector(Context context) {
		this.myHandler = new MyHandler(context.getMainLooper());
		this.context = context;
		this.progressMsg = context.getString(R.string.connector_progress_msg);
	}

	private class MyHandler extends Handler {
		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			Log.d(logTag, "In Connector,deal one message with what=" + msg.what);

			switch (msg.what) {
			case MSG_WHAT_RECEVIE_ERROR:
				onRecevieListener.onError(msg.arg1);
				break;
			case MSG_WHAT_RECEVIE_SUCCESS:
				onRecevieListener.onRecevie((byte[]) msg.obj);
				break;
			case MSG_WHAT_RECEVING:
				onRecevieListener.onReceiving(msg.arg1, msg.arg2);
				break;
			}
		}
	}

	public void send() {
		if (showProgress) {
			if (progress == null) {
				progress = new ProgressDialog(context);
				progress.setCancelable(false);
				progress.setMessage(progressMsg);
			}
			if (!progress.isShowing())
				progress.show();
		}

		Commutication comm = new Commutication();
		new Thread(comm).start();
	}

	public interface OnRecevieListener {

		/**
		 * 成功
		 */
		final public static int SUCCESS = 0X0000;

		/**
		 * 接收数据回调接口
		 * 
		 * @param receiveMsg
		 *            接收内容[只有在返回值是成功状态才有效]
		 */
		public void onRecevie(byte[] receiveMsg);

		/**
		 * 接收数据中
		 * 
		 * @param totalLength
		 *            总长度
		 * @param receiveLength
		 *            当前接收的长度
		 */
		public void onReceiving(int totalLength, int receiveLength);

		/**
		 * 接收失败
		 * 
		 * @param errorCode
		 */
		public void onError(int errorCode);
	}

	/**
	 * 连接超时
	 */
	private int connectionTimeout = 60000;

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * 读取数据超时
	 */
	private int readTimeout = 90000;

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	private class Commutication implements Runnable {

		public void run() {
			HttpClient httpClient = null;
			HttpPost post = null;
			BufferedReader br = null;
			try {
				httpClient = new DefaultHttpClient();
				post = new HttpPost(url);

				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
				httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : params.entrySet()) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				params.clear();

				post.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));

				HttpResponse response = httpClient.execute(post);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();

					long contentLength = entity.getContentLength();
					Log.d(logTag, "content length = " + contentLength);

					Message message = new Message();
//					message.what = MSG_WHAT_RECEVING;
//					message.arg1 = (int) contentLength;
//					message.arg2 = 0;
//					myHandler.sendMessage(message);

					InputStream is = entity.getContent();

					int totalRead = 0;
					
					byte[] buffer = new byte[bufferSize];
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int bytesRead = is.read(buffer);
					while (bytesRead != -1) {
						totalRead += bytesRead;
						
						message = new Message();
						message.what = MSG_WHAT_RECEVING;
						message.arg1 = totalRead;
						message.arg2 = (int) contentLength;
						myHandler.sendMessage(message);

						out.write(buffer, 0, bytesRead);
						bytesRead = is.read(buffer);
					}

//					message = new Message();
//					message.what = MSG_WHAT_RECEVING;
//					message.arg1 = (int) contentLength;
//					message.arg2 = (int) contentLength;
//					myHandler.sendMessage(message);

					message = new Message();
					message.what = MSG_WHAT_RECEVIE_SUCCESS;
					message.obj = out.toByteArray();
					myHandler.sendMessage(message);

//					Log.d(logTag, "http response :" + content);
				} else {
					Message message = new Message();
					message.what = MSG_WHAT_RECEVIE_ERROR;
					message.arg1 = response.getStatusLine().getStatusCode();
					myHandler.sendMessage(message);
				}
			} catch (ClientProtocolException e) {
				Message message = new Message();
				message.what = MSG_WHAT_RECEVIE_ERROR;
				message.arg1 = -1;
				message.obj = "通讯错误";
				myHandler.sendMessage(message);
				Log.e(logTag, "通讯错误", e);
			} catch (IOException e) {
				if (e instanceof ConnectTimeoutException) {
					Message message = new Message();
					message.what = MSG_WHAT_RECEVIE_ERROR;
					message.arg1 = TIMEOUT_ERROR;
					message.obj = "连接超时";
					myHandler.sendMessage(message);
				} else {
					Message message = new Message();
					message.what = MSG_WHAT_RECEVIE_ERROR;
					message.arg1 = TIMEOUT_ERROR;
					message.obj = "通讯错误";
					myHandler.sendMessage(message);
				}
				Log.e(logTag, "通讯错误", e);
			} finally {
				if (br != null)
					try {
						br.close();
					} catch (IOException e) {
						Log.w(logTag, "关闭HTTP流失败", e);
					}
			}

			if (showProgress && progress.isShowing())
				progress.dismiss();
		}
	}
}
