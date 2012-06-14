package com.herbfamily;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewMemberFormActivity extends Activity implements View.OnClickListener {
	
	private MemberDatabase memberDatabase = null;
	private Contact contact = null;
	
	private ImageButton imgBtn;
	private Button buttonOpenGroupChoice, cycleBtn, timeBtn, alarmTypeBtn, alarmToneBtn;
	private static final String TAG = NewMemberFormActivity.class.getSimpleName();
	private int groupId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_member_form);
		
		int contactId = getIntent().getIntExtra("contactId", 0);
		String name = getIntent().getStringExtra("contactName");
		String phoneNumber = getIntent().getStringExtra("contactPhoneNumber");
		contact = new Contact(contactId, name, phoneNumber);
		
		if (contactId == 0) {
			//TODO ??
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
		//TODO button listner 가 너무 길다.
		buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText name = (EditText)findViewById(R.id.editTextName);
				EditText nickname = (EditText)findViewById(R.id.editTextNickname);
				EditText phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
				
				if(name.getText().toString()=="" || nickname.getText().toString()=="" || phoneNumber.getText().toString()=="") {
					//TODO
					//오류처리.
					return;
				}
				
				memberDatabase = new MemberDatabase(NewMemberFormActivity.this);
				if ( ! memberDatabase.addMember(name.getText().toString(), 
						nickname.getText().toString(), 
						phoneNumber.getText().toString(),
						groupId)) {
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
		cycleBtn = (Button)findViewById(R.id.cycleBtn);
		cycleBtn.setOnClickListener(this);
		timeBtn = (Button)findViewById(R.id.timeBtn);
		timeBtn.setOnClickListener(this);
		alarmTypeBtn = (Button)findViewById(R.id.alarmTypeBtn);
		alarmTypeBtn.setOnClickListener(this);
		alarmToneBtn = (Button)findViewById(R.id.alarmToneBtn);
		alarmToneBtn.setOnClickListener(this);
		buttonOpenGroupChoice = (Button)findViewById(R.id.buttonOpenGroupChoice);
		buttonOpenGroupChoice.setOnClickListener(this);
		
		imgBtn = (ImageButton)findViewById(R.id.choice);
//		imgBtn.setBackgroundResource(R.drawable.ic_launcher);
        imgBtn.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.choice:
    		Intent herb = new Intent(NewMemberFormActivity.this, Dialog.class);
			startActivityForResult(herb, Consts.DIALOG_REQUEST_CODE);
			break;
			
		case R.id.buttonOpenGroupChoice:
			Intent intent = new Intent(NewMemberFormActivity.this, GroupChoiceActivity.class);
			startActivityForResult(intent, Consts.GROUP_REQUEST_CODE);
			break;
			
		case R.id.cycleBtn:
			final String[] cycle = {"3일", "5일", "7일", "10일"};
			
			new AlertDialog.Builder(NewMemberFormActivity.this)
			.setTitle("알람주기")
			.setSingleChoiceItems(cycle, 0, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog ,int which) {
					Toast.makeText(NewMemberFormActivity.this, "cycle : " + cycle[which],Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}).setNeutralButton("닫기", null).show();
	
			break;

		case R.id.timeBtn:

				TimePickerDialog.OnTimeSetListener mTimeSetListener = 
				new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Toast.makeText(NewMemberFormActivity.this,
						"Time is=" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT)
						.show();
					}
				};
				TimePickerDialog alert = new TimePickerDialog(this, 
			mTimeSetListener, 0, 0, false);
				alert.show();

			break;
			
		case R.id.alarmTypeBtn:
			final String[] type = {"벨소리", "진동", "진동 및 벨소리", "무음"};
			
			new AlertDialog.Builder(NewMemberFormActivity.this)
			.setTitle("알람방식")
			.setSingleChoiceItems(type, 0, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog ,int which) {
					Toast.makeText(NewMemberFormActivity.this, "type : " + type[which],Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}).setNeutralButton("닫기", null).show();
	
			break;
			
		case R.id.alarmToneBtn:
			final String[] tone = {"1번 벨소리", "2번 벨소리", "3번 벨소리", "4번 벨소리"};
			
			new AlertDialog.Builder(NewMemberFormActivity.this)
			.setTitle("알람음")
			.setSingleChoiceItems(tone, 0, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog ,int which) {
					Toast.makeText(NewMemberFormActivity.this, "tone : " + tone[which],Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}).setNeutralButton("닫기", null).show();
	
			break;
			
		}
	}
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK) {
        	if (requestCode == Consts.GROUP_REQUEST_CODE) {
        		groupId = data.getIntExtra("groupId", 0);
        		buttonOpenGroupChoice.setText(data.getStringExtra("groupName"));
        	} else if (requestCode == Consts.DIALOG_REQUEST_CODE) {
				int potKind = data.getIntExtra("potKind", 0);
		        imgBtn.setBackgroundResource(potKind);
			}
		}
    }
	
}
