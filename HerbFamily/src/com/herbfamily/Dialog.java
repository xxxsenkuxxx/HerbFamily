package com.herbfamily;

import android.R.style;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Dialog extends Activity {

	@Override
	protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
		super.onApplyThemeResource(theme,resid,first);
		setContentView(R.layout.hub);
		
		theme.applyStyle(style.Theme_Panel,true);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//�׵θ���ֱ�

		Button potButton = (Button) findViewById(R.id.back);
		potButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		
		ImageButton pot1 = (ImageButton) findViewById(R.id.pot1);
		pot1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int potKind = R.drawable.hc_1;
				Intent intent = new Intent();
				intent.putExtra("potKind", potKind);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		ImageButton pot2 = (ImageButton) findViewById(R.id.pot2);
		pot2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int potKind = R.drawable.hc_2;
				Intent intent = new Intent();
				intent.putExtra("potKind", potKind);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		ImageButton pot3 = (ImageButton) findViewById(R.id.pot3);
		pot3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int potKind = R.drawable.hc_3;
				Intent intent = new Intent();
				intent.putExtra("potKind", potKind);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		
		
		
//		Button potButton = (Button) findViewById(R.id.back);
//		potButton.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				int potKind = 1;
//				Intent intent = new Intent();
//				intent.putExtra("potKind", potKind);
//				setResult(RESULT_CANCELED, intent);
//				finish();
//			}
//		});

	}

}
