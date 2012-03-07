package pl.polidea.gesturedetector;

import android.os.Handler;
import android.view.MotionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class BetterGestureDetector.
 */
public class BetterGestureDetector {

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
         */
        void onShowPress(MotionEvent motionEvent);

        /**
         * On single tap up.
         */
        void onSingleTapUp(MotionEvent motionEvent);

        /**
         * On drag.
         */
        void onDrag(MotionEvent motionEvent);

        /**
         * On move.
         */
        void onMove(MotionEvent motionEvent);

        /**
         * On release.
         */
        void onRelease(MotionEvent motionEvent);
    }

    /**
     * The Class BetterGestureAdapter.
     */
    class BetterGestureAdapter implements BetterGestureListener {

        /*
         * (non-Javadoc)
         * 
         * @see
         * pl.polidea.test.BetterGestureDetector.BetterGestureListener#onShowPress
         * ()
         */
        @Override
        public void onShowPress(MotionEvent motionEvent) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * pl.polidea.test.BetterGestureDetector.BetterGestureListener#onSingleTapUp
         * ()
         */
        @Override
        public void onSingleTapUp(MotionEvent motionEvent) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * pl.polidea.test.BetterGestureDetector.BetterGestureListener#onDrag()
         */
        @Override
        public void onDrag(MotionEvent motionEvent) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * pl.polidea.test.BetterGestureDetector.BetterGestureListener#onMove()
         */
        @Override
        public void onMove(MotionEvent motionEvent) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * pl.polidea.test.BetterGestureDetector.BetterGestureListener#onRelease
         * ()
         */
        @Override
        public void onRelease(MotionEvent motionEvent) {
            // TODO Auto-generated method stub

        }

    }

    /** The Constant DEFAULT_PRESS_TIMEOUT. */
    private static final int DEFAULT_PRESS_TIMEOUT = 100;

    /** The Constant DEFAULT_TAP_TIMEOUT. */
    private static final int DEFAULT_TAP_TIMEOUT = 300;

    /** The Constant DEFAULT_MOVE_EPSILON. */
    private static final int DEFAULT_MOVE_EPSILON = 4;

    /** The press timeout. */
    private int pressTimeout = DEFAULT_PRESS_TIMEOUT;

    /** The tap timeout. */
    private int tapTimeout = DEFAULT_TAP_TIMEOUT;

    /** The move epsilon. */
    private int moveEpsilon = DEFAULT_MOVE_EPSILON;

    /** The handler. */
    private Handler handler;

    /** The tap handler. */
    Runnable tapHandler;

    /** The press handler. */
    Runnable pressHandler;

    /** The listener. */
    private final BetterGestureListener listener;

    /** The prev touch time. */
    private long prevTouchTime;

    /** The prev touch y. */
    private float prevTouchY;

    /** The prev touch x. */
    private float prevTouchX;

    /** The dragging. */
    private boolean dragging;

    /** The moving. */
    private boolean moving;

    /**
     * Instantiates a new better gesture detector.
     * 
     * @param listener
     *            the listener
     */
    public BetterGestureDetector(BetterGestureListener listener) {
        if (listener == null) {
            throw new NullPointerException("Listener cannot be null");
        }
        this.listener = listener;
        handler = new Handler();
    }

    /**
     * On touch event.
     * 
     * @param motionEvent
     *            the motion event
     */
    public void onTouchEvent(final MotionEvent motionEvent) {
        float dx = motionEvent.getX() - prevTouchX;
        float dy = motionEvent.getY() - prevTouchY;
        long dt = System.currentTimeMillis() - prevTouchTime;
        handler.removeCallbacks(tapHandler);
        tapHandler = null;

        switch (motionEvent.getAction()) {
        case MotionEvent.ACTION_DOWN:
            dragging = false;
            moving = false;
            pressHandler = new Runnable() {

                @Override
                public void run() {
                    onShowPress(motionEvent);
                }

            };
            handler.postDelayed(pressHandler, pressTimeout);
            prevTouchTime += dt;
            break;
        case MotionEvent.ACTION_UP:
            if (dt > tapTimeout || moving || dragging) {
                onRelease(motionEvent);
                break;
            }

            if (pressHandler != null) {
                tapHandler = new Runnable() {

                    @Override
                    public void run() {
                        onSingleTapUp(motionEvent);
                    }
                };
                handler.postDelayed(tapHandler, tapTimeout - dt);
            } else {
                onShowPress(motionEvent);
                tapHandler = new Runnable() {

                    @Override
                    public void run() {
                        onSingleTapUp(motionEvent);
                    }
                };
                handler.postDelayed(tapHandler, tapTimeout - pressTimeout);
            }
            prevTouchTime += dt;
            break;

        case MotionEvent.ACTION_MOVE:
            if (Math.abs(dx) > moveEpsilon || Math.abs(dy) > moveEpsilon || dragging || moving) {
                if (pressHandler == null && !moving) {
                    dragging = true;
                }
                handler.removeCallbacks(pressHandler);
                pressHandler = null;
                moving = true;
                if (dragging) {
                    onDrag(motionEvent);
                } else {
                    onMove(motionEvent);
                }
            }

        }
        prevTouchX = motionEvent.getX();
        prevTouchY = motionEvent.getY();
    }

    /**
     * On release.
     */
    private void onRelease(MotionEvent motionEvent) {
        listener.onRelease(motionEvent);
    }

    /**
     * On drag.
     */
    private void onDrag(MotionEvent motionEvent) {
        listener.onDrag(motionEvent);
    }

    /**
     * On move.
     */
    private void onMove(MotionEvent motionEvent) {
        listener.onMove(motionEvent);
    }

    /**
     * On single tap up.
     */
    protected void onSingleTapUp(MotionEvent motionEvent) {
        tapHandler = null;
        listener.onSingleTapUp(motionEvent);
    }

    /**
     * On show press.
     */
    protected void onShowPress(MotionEvent motionEvent) {
        pressHandler = null;
        listener.onShowPress(motionEvent);
    }

    /**
     * Gets the press timeout.
     * 
     * @return the press timeout
     */
    public int getPressTimeout() {
        return pressTimeout;
    }

    /**
     * Sets the press timeout.
     * 
     * @param pressTimeout
     *            the new press timeout
     */
    public void setPressTimeout(int pressTimeout) {
        this.pressTimeout = pressTimeout;
    }

    /**
     * Gets the tap timeout.
     * 
     * @return the tap timeout
     */
    public int getTapTimeout() {
        return tapTimeout;
    }

    /**
     * Sets the tap timeout.
     * 
     * @param tapTimeout
     *            the new tap timeout
     */
    public void setTapTimeout(int tapTimeout) {
        this.tapTimeout = tapTimeout;
    }

    /**
     * Gets the move epsilon.
     * 
     * @return the move epsilon
     */
    public int getMoveEpsilon() {
        return moveEpsilon;
    }

    /**
     * Sets the move epsilon.
     * 
     * @param moveEpsilon
     *            the new move epsilon
     */
    public void setMoveEpsilon(int moveEpsilon) {
        this.moveEpsilon = moveEpsilon;
    }
}
