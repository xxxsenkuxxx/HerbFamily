package com.herbfamily;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class MembersInGroupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_in_group);
		
		int groupId = 1;
		MemberDatabase database = new MemberDatabase(this);
		ArrayList<Member> members = database.getMembersInGroup(groupId);
	}
}
