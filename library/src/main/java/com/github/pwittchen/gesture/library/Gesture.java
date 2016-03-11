package com.github.pwittchen.gesture.library;

import android.os.Handler;
import android.view.MotionEvent;

public class Gesture {
  private static final int DEFAULT_PRESS_TIMEOUT = 100;
  private static final int DEFAULT_TAP_TIMEOUT = 300;
  private static final int DEFAULT_MOVE_EPSILON = 4;
  private static final long DEFAULT_LONG_PRESS_TIMEOUT = 500;

  private int pressTimeout = DEFAULT_PRESS_TIMEOUT;
  private int tapTimeout = DEFAULT_TAP_TIMEOUT;
  private int moveEpsilon = DEFAULT_MOVE_EPSILON;
  private long longPressTimeout = DEFAULT_LONG_PRESS_TIMEOUT;

  private Runnable tapHandler;
  private Runnable pressHandler;
  private Runnable longPressHandler;

  private long prevTouchTime;
  private float prevTouchY;
  private float prevTouchX;

  private boolean dragging;
  private boolean moving;
  private int clicks = 0;

  private Handler handler;
  private final GestureCallbacks listener;

  public Gesture(GestureCallbacks listener) {
    if (listener == null) {
      throw new NullPointerException("listener == null");
    }

    this.listener = listener;
    this.handler = new Handler();
  }

  public void dispatchTouchEvent(final MotionEvent motionEvent) {
    float dx = motionEvent.getX() - prevTouchX;
    float dy = motionEvent.getY() - prevTouchY;
    long dt = System.currentTimeMillis() - prevTouchTime;
    handler.removeCallbacks(longPressHandler);
    longPressHandler = null;

    switch (motionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:
        onActionDown(motionEvent, dt);
        break;
      case MotionEvent.ACTION_UP:
        onActionUp(motionEvent, dt);
        break;
      case MotionEvent.ACTION_MOVE:
        onActionMove(motionEvent, dx, dy);
        break;
      case MotionEvent.ACTION_CANCEL:
        onActionCancel(motionEvent);
        break;
    }

    prevTouchX = motionEvent.getX();
    prevTouchY = motionEvent.getY();
  }

  private void onActionCancel(MotionEvent motionEvent) {
    handler.removeCallbacks(pressHandler);
    pressHandler = null;
    handler.removeCallbacks(tapHandler);
    tapHandler = null;
    handler.removeCallbacks(longPressHandler);
    longPressHandler = null;
    onRelease(motionEvent);
  }

  private void onActionMove(MotionEvent motionEvent, float dx, float dy) {
    if (Math.abs(dx) > moveEpsilon || Math.abs(dy) > moveEpsilon || dragging || moving) {
      if (pressHandler == null && !moving) {
        dragging = true;
      }
      handler.removeCallbacks(tapHandler);
      tapHandler = null;
      handler.removeCallbacks(pressHandler);
      pressHandler = null;
      handler.removeCallbacks(longPressHandler);
      longPressHandler = null;
      moving = true;
      if (dragging) {
        onDrag(motionEvent);
      } else {
        onMove(motionEvent);
      }
    }
  }

  private void onActionUp(final MotionEvent motionEvent, long dt) {
    if (dt > tapTimeout || moving || dragging) {
      onRelease(motionEvent);
      return;
    }

    tapHandler = new Runnable() {

      @Override public void run() {
        handler.removeCallbacks(longPressHandler);
        longPressHandler = null;
        onTap(motionEvent, clicks);
      }
    };
    handler.postDelayed(tapHandler, tapTimeout - dt);
    prevTouchTime += dt;
  }

  private void onActionDown(final MotionEvent motionEvent, long dt) {
    dragging = false;
    moving = false;
    if (tapHandler != null) {
      clicks++;
      handler.removeCallbacks(tapHandler);
      tapHandler = null;
    }

    handler.removeCallbacks(pressHandler);
    pressHandler = new Runnable() {
      @Override public void run() {
        onPress(motionEvent);
      }
    };
    handler.postDelayed(pressHandler, pressTimeout);

    longPressHandler = new Runnable() {
      @Override public void run() {
        onLongPress(motionEvent);
      }
    };
    handler.postDelayed(longPressHandler, longPressTimeout);
    prevTouchTime += dt;
  }

  private void onRelease(MotionEvent motionEvent) {
    clicks = 0;
    listener.onRelease(motionEvent);
  }

  private void onDrag(MotionEvent motionEvent) {
    clicks = 0;
    listener.onDrag(motionEvent);
  }

  private void onMove(MotionEvent motionEvent) {
    clicks = 0;
    listener.onMove(motionEvent);
  }

  protected void onTap(MotionEvent motionEvent, int clicks) {
    tapHandler = null;
    if (clicks == 0) {
      listener.onTap(motionEvent);
    } else {
      listener.onMultiTap(motionEvent, clicks + 1);
    }
    this.clicks = 0;
  }

  private void onLongPress(MotionEvent motionEvent) {
    clicks = 0;
    longPressHandler = null;
    listener.onLongPress(motionEvent);
  }

  private void onPress(MotionEvent motionEvent) {
    pressHandler = null;
    listener.onPress(motionEvent);
  }

  public int getPressTimeout() {
    return pressTimeout;
  }

  public void setPressTimeout(int pressTimeout) {
    this.pressTimeout = pressTimeout;
  }

  public int getTapTimeout() {
    return tapTimeout;
  }

  public void setTapTimeout(int tapTimeout) {
    this.tapTimeout = tapTimeout;
  }

  public int getMoveEpsilon() {
    return moveEpsilon;
  }

  public void setMoveEpsilon(int moveEpsilon) {
    this.moveEpsilon = moveEpsilon;
  }

  public long getLongPressTimeout() {
    return longPressTimeout;
  }

  public void setLongPressTimeout(long longPressTimeout) {
    this.longPressTimeout = longPressTimeout;
  }
}
