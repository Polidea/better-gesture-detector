package pl.polidea.gesturedetector;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import pl.polidea.gesturedetector.library.Gesture;
import pl.polidea.gesturedetector.library.GestureCallbacks;
import pl.polidea.test.R;

public class GestureActivity extends Activity implements GestureCallbacks {
    private Gesture gesture;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.textView);
        gesture = new Gesture(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gesture.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onPress(MotionEvent motionEvent) {
        textView.setText("press");
        textView.setBackgroundColor(0xffff0000);
        textView.invalidate();
    }

    @Override
    public void onTap(MotionEvent motionEvent) {
        textView.setText("tap");
        textView.setBackgroundColor(0xff00ff00);
        textView.invalidate();
    }

    @Override
    public void onDrag(MotionEvent motionEvent) {
        textView.setText("drag");
        textView.setBackgroundColor(0xff0000ff);
        textView.invalidate();
    }

    @Override
    public void onMove(MotionEvent motionEvent) {
        textView.setText("move");
        textView.setBackgroundColor(0xffff00ff);
        textView.invalidate();
    }

    @Override
    public void onRelease(MotionEvent motionEvent) {
        textView.setText("release");
        textView.setBackgroundColor(0xffffff00);
        textView.invalidate();
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        textView.setText("longpress");
        textView.setBackgroundColor(0xff00ffff);
        textView.invalidate();
    }

    @Override
    public void onMultiTap(MotionEvent motionEvent, int clicks) {
        textView.setText("multitap [" + clicks + "]");
        textView.setBackgroundColor(0xff7f7f7f);
        textView.invalidate();
    }
}