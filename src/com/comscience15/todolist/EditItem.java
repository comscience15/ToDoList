package com.comscience15.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItem extends Activity{
	private EditText etItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edititem);
		etItem = (EditText) findViewById(R.id.etEditItem);
		String getItem = getIntent().getStringExtra("Item name");
		etItem.setText(getItem);
	}
	
	public void btnEdit(View v){
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("Item name", etItem.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
