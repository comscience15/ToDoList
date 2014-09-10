package com.comscience15.todolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private String TAG = "ToDoList";
	private ArrayList<String> todoItem;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItem;
	private EditText etNewItem;
	private final int REQUEST_CODE = 1;
	int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		declareVariables();
		readFile();
		addNewItems();
		setListViewListener();
	}

	private void setListViewListener() {
		lvItem.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				todoItem.remove(position);
				todoAdapter.notifyDataSetChanged();
				writeFile();
				return true;
			}
		});
		
		lvItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, EditItem.class);
				intent.putExtra("Item name", todoItem.get(position));
				pos = position;
				Log.i(TAG, "Selected position is " + pos);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	private void declareVariables() {
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItem = (ListView) findViewById(R.id.lvItems);
	}

	private void addNewItems() {
		todoItem = new ArrayList<String>();
		todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItem);
		lvItem.setAdapter(todoAdapter);
	}

	public void onAddNewItems(View v){
		String newItem = etNewItem.getText().toString();
		todoAdapter.add(newItem);
		etNewItem.setText("");
		writeFile();
	}
	
	private void readFile() {
		File dir = getFilesDir();
		File file = new File(dir, "todo.txt");
		try {
			todoItem = new ArrayList<String>(FileUtils.readLines(file));
		} catch (IOException e) {
			todoItem = new ArrayList<String>();
		}
	}
	
	private void writeFile() {
		File dir = getFilesDir();
		File file = new File(dir, "todo.txt");
		try {
			FileUtils.writeLines(file, todoItem);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data) {
		if (result == RESULT_OK && request == REQUEST_CODE){
			String name = data.getExtras().getString("Item name");
			Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

			todoItem.set(pos, name);
			todoAdapter.notifyDataSetChanged();
			writeFile();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
