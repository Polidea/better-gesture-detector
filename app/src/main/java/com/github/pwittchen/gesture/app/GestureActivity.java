package com.github.pwittchen.gesture.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import com.github.pwittchen.gesture.library.Gesture;
import com.github.pwittchen.gesture.library.GestureListener;

public class GestureActivity extends Activity {
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

  private void setEventText(String press, int color) {
    textView.setText(press);
    textView.setBackgroundColor(color);
    textView.invalidate();
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    gesture.dispatchTouchEvent(ev);
    return super.dispatchTouchEvent(ev);
  }
}