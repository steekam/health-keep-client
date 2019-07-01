package com.steekam.custom_stylings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
	private Toast toast;
	private TextView toastContent;
	private Context activityContext;

	public CustomToast(Context applicationContext, Context activityContext) {
		this.activityContext = activityContext;
		toast = new Toast(applicationContext);
		LayoutInflater inflater = LayoutInflater.from(applicationContext);
		View layout = inflater.inflate(R.layout.custom_toast, ((Activity) activityContext).findViewById(R.id.custom_toast_container));
		toastContent = layout.findViewById(R.id.toast_content);
		toast.setView(layout);
	}

	public void show(String type, String message, int gravity) {
		toast.setGravity(gravity, 0, 20);
		toastContent.setText(message);
		int drawableId = 0;
		switch (type) {
			case "error": {
				drawableId = R.drawable.toast_error;
				break;
			}
			case "success": {
				drawableId = R.drawable.toast_success;
				break;
			}
			default:
				break;
		}
		toastContent.setBackground(activityContext.getResources().getDrawable(drawableId));
		toast.show();
	}
}
