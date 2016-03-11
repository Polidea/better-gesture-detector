package com.github.pwittchen.gesture.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import com.github.pwittchen.gesture.library.Gesture;
import com.github.pwittchen.gesture.library.GestureListener;

public class GestureActivity extends AppCompatActivity {
  private Gesture gesture;
  private TextView textView;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    textView = (TextView) findViewById(R.id.textView);
    gesture = new Gesture();
    gesture.addListener(new GestureListener() {
      @Override public void onPress(MotionEvent motionEvent) {
        setEventText("press", 0xffff0000);
      }

      @Override public void onTap(MotionEvent motionEvent) {
        setEventText("tap", 0xff00ff00);
      }

      @Override public void onDrag(MotionEvent motionEvent) {
        setEventText("drag", 0xff0000ff);
      }

      @Override public void onMove(MotionEvent motionEvent) {
        setEventText("move", 0xffff00ff);
      }

      @Override public void onRelease(MotionEvent motionEvent) {
        setEventText("release", 0xffffff00);
      }

      @Override public void onLongPress(MotionEvent motionEvent) {
        setEventText("longpress", 0xff00ffff);
      }

      @Override public void onMultiTap(MotionEvent motionEvent, int clicks) {
        setEventText("multitap [" + clicks + "]", 0xff7f7f7f);
      }
    });
  }

  private void setEventText(String message, int color) {
    textView.setText(message);
    textView.setBackgroundColor(color);
    textView.invalidate();
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    gesture.dispatchTouchEvent(event);
    return super.dispatchTouchEvent(event);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.listener:
        break;
      case R.id.rx:
        final Intent intent = new Intent(this, GestureRxActivity.class);
        startActivity(intent);
        break;
    }
    return true;
  }
}