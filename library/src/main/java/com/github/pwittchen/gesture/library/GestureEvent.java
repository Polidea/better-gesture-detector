package com.github.pwittchen.gesture.library;

public enum GestureEvent {
  ON_PRESS,
  ON_TAP,
  ON_DRAG,
  ON_MOVE,
  ON_RELEASE,
  ON_LONG_PRESS,
  ON_MULTITAP;

  private int clicks = 1;

  public int getClicks() {
    return clicks;
  }

  public void setClicks(int clicks) {
    this.clicks = clicks;
  }
}
