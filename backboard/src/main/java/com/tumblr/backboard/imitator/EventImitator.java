package com.tumblr.backboard.imitator;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.rebound.Spring;

/**
 * Maps a {@link android.view.MotionEvent} to a {@link com.facebook.rebound.Spring},
.
 */
public abstract class EventImitator extends Imitator {

	/**
	 * Constructor.
	 *
	 * @param spring
	 * 		the spring to use.
	 * @param restValue
	 * 		the rest value for the spring.
	 * @param trackStrategy
	 * 		the tracking strategy.
	 * @param followStrategy
	 * 		the follow strategy.
	 */
	public EventImitator(@NonNull final Spring spring, final double restValue, final int trackStrategy, final int followStrategy) {
		super(spring, restValue, trackStrategy, followStrategy);
	}

	/**
	 * Constructor. Note that the spring must be set with {@link #setSpring(Spring)}.
	 *
	 * @param restValue
	 * 		the rest value for the spring.
	 * @param trackStrategy
	 * 		the tracking strategy.
	 * @param followStrategy
	 * 		the follow strategy.
	 */
	protected EventImitator(final double restValue, final int trackStrategy, final int followStrategy) {
		super(restValue, trackStrategy, followStrategy);
	}

	/**
	 * Called when the user touches ({@link android.view.MotionEvent#ACTION_DOWN}).
	 *
	 * @param event
	 * 		the motion event
	 */
	public void constrain(final MotionEvent event) {
		if (mSpring != null && mFollowStrategy == FOLLOW_EXACT) {
			mSpring.setVelocity(0);
		}
	}

	/**
	 * Called when the user moves their finger ({@link android.view.MotionEvent#ACTION_MOVE}).
	 *
	 * @param offset
	 * 		the value offset
	 * @param value
	 * 		the current value
	 * @param delta
	 * 		the change in the value
	 * @param dt
	 * 		the change in time
	 * @param event
	 * 		the motion event
	 */
	public void mime(final float offset, final float value, final float delta, final float dt, final MotionEvent event) {
		if (mSpring != null) {
			mSpring.setEndValue(mapToSpring(offset + value));

			if (mFollowStrategy == FOLLOW_EXACT) {
				mSpring.setCurrentValue(mSpring.getEndValue());

				if (dt > 0) {
					mSpring.setVelocity(delta / dt);
				}
			}
		}
	}

	/**
	 * Called when the user releases their finger ({@link android.view.MotionEvent#ACTION_UP}).
	 *
	 * @param event
	 * 		the motion event
	 */
	public void release(final MotionEvent event) {
		if (mSpring != null) {
			mSpring.setEndValue(mRestValue);
		}
	}

	/**
	 * Called by a {@link com.tumblr.backboard.imitator.MotionImitator} (or another {@link
	 * android.view.View.OnTouchListener}) when a {@link android.view.MotionEvent} occurs.
	 *
	 * @param offset
	 * 		the value offset
	 * @param value
	 * 		the current value
	 * @param delta
	 * 		the change in the value
	 * @param event
	 * 		the motion event
	 */
	protected void imitate(final float offset, final float value, final float delta, @Nullable final MotionEvent event) {
		if (event != null) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				constrain(event);

			case MotionEvent.ACTION_MOVE:
				if (event.getHistorySize() > 0) {
					mime(offset, value, delta,
							event.getEventTime() - event.getHistoricalEventTime(0), event);
				} else {
					mime(offset, value, delta, 0, event);
				}

				break;
			default:
			case MotionEvent.ACTION_UP:
				release(event);

				break;
			}
		}
	}

	/**
	 * Maps a user's motion to {@link android.view.View} via a {@link com.facebook.rebound.Spring}.
	 *
	 * @param view
	 * 		the view to perturb.
	 * @param event
	 * 		the motion to imitate.
	 */
	public abstract void imitate(final View view, @NonNull final MotionEvent event);
}
