package com.herbfamily;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewMemberFormActivity extends Activity {
	
	private MemberDatabase memberDatabase = null;
	private Contact contact = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_member_form);
		
		int contactId = getIntent().getIntExtra("contactId", 0);
		String name = getIntent().getStringExtra("contactName");
		String phoneNumber = getIntent().getStringExtra("contactPhoneNumber");
		
		contact = new Contact(contactId, name, phoneNumber);
		
		if (contactId == 0) {
			finish();
		}
		
		setupWidgets();
	}

	private void setupWidgets() {
		
		EditText editTextName = (EditText)findViewById(R.id.editTextName);
		editTextName.setText(contact.getName());
		
		EditText editTextPhoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
		editTextPhoneNumber.setText(contact.getPhoneNumber());
		
		Button buttonSave = (Button)findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText name = (EditText)findViewById(R.id.editTextName);
				EditText nickname = (EditText)findViewById(R.id.editTextNickname);
				EditText phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
				
				if(name.getText().toString()=="" || nickname.getText().toString()=="" || phoneNumber.getText().toString()=="") {
					return;
				}
				
				memberDatabase = new MemberDatabase(NewMemberFormActivity.this);
				if ( ! memberDatabase.addMember(name.getText().toString(), 
						nickname.getText().toString(), 
						phoneNumber.getText().toString())) {
					new AlertDialog.Builder(NewMemberFormActivity.this)
					.setTitle("오류")
					.setMessage("구성원을 추가 할 수 없습니다.")
					.setPositiveButton("확인", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					}).show();
				}
				memberDatabase.close();
				finish();
			}
		});
		
		Button buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
