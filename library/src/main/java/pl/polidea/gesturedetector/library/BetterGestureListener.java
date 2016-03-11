package pl.polidea.gesturedetector.library;

import android.view.MotionEvent;

/**
 * The listener interface for receiving betterGesture events. The class that
 * is interested in processing a betterGesture event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addBetterGestureListener<code> method. When
 * the betterGesture event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BetterGestureEvent
 */
public interface BetterGestureListener {

  /**
   * On show press.
   *
   * @param motionEvent
   *            the motion event
   */
  void onPress(MotionEvent motionEvent);

  /**
   * On single tap up.
   *
   * @param motionEvent
   *            the motion event
   */
  void onTap(MotionEvent motionEvent);

  /**
   * On drag.
   *
   * @param motionEvent
   *            the motion event
   */
  void onDrag(MotionEvent motionEvent);

  /**
   * On move.
   *
   * @param motionEvent
   *            the motion event
   */
  void onMove(MotionEvent motionEvent);

  /**
   * On release.
   *
   * @param motionEvent
   *            the motion event
   */
  void onRelease(MotionEvent motionEvent);

  /**
   * On long press.
   *
   * @param motionEvent
   *            the motion event
   */
  void onLongPress(MotionEvent motionEvent);

  /**
   * On double tap.
   *
   * @param motionEvent
   *            the motion event
   * @param clicks
   */
  void onMultiTap(MotionEvent motionEvent, int clicks);
}
