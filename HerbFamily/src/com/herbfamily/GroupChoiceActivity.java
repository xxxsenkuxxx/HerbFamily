package com.herbfamily;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroupChoiceActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_group);
		
		MemberDatabase database = new MemberDatabase(this);
		ArrayList<String> groups = database.getGroups();
		if (groups.size() == 0) {
			//TODO
			//그룹이 없습니다.
		}
		
		Button buttonClose = (Button)findViewById(R.id.buttonCloseChoiceGroup);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
		
		Button buttonChoiceGroup = (Button)findViewById(R.id.buttonChoiceGroup);
		if (groups.size() == 0) {
			// 선택하면 바로 적용하면 되지 않을까?
			buttonChoiceGroup.setEnabled(false);
		}
		buttonChoiceGroup.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//TODO 
				//choice group action
			}
		});
	}
}
