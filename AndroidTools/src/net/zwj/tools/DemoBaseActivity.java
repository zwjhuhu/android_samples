package net.zwj.tools;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class DemoBaseActivity extends Activity implements OnItemClickListener{

	private ListView listView;

	protected abstract List<String> createDemoList();
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);
		listView = (ListView) findViewById(R.id.demolist);
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, createDemoList());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

}
