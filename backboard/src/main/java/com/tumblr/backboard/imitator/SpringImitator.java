package com.tumblr.backboard.imitator;

import androidx.annotation.NonNull;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;

/**
 * Allows a {@link com.facebook.rebound.Spring} to imitate another {@link com.facebook.rebound.Spring}.
 */
public class SpringImitator extends Imitator implements SpringListener {

	/**
	 * @param spring
	 * 		the spring to imitate
	 */
	public SpringImitator(@NonNull final Spring spring) {
		super(spring);
	}

	/**
	 * Constructor.
	 *
	 * @param spring
	 * 		the spring to imitate.
	 * @param trackStrategy
	 * 		the tracking strategy.
	 * @param followStrategy
	 * 		the follow strategy.
	 */
	protected SpringImitator(@NonNull final Spring spring, final int trackStrategy, final int followStrategy) {
		super(spring, trackStrategy, followStrategy);
	}

	/**
	 * Constructor.
	 *
	 * @param spring
	 * 		the spring to imitate.
	 * @param restValue
	 * 		the spring value when not being perturbed.
	 * @param trackStrategy
	 * 		the tracking strategy.
	 * @param followStrategy
	 * 		the follow strategy.
	 */
	protected SpringImitator(@NonNull final Spring spring, final double restValue, final int trackStrategy, final int followStrategy) {
		super(spring, restValue, trackStrategy, followStrategy);
	}

	@Override
	protected double mapToSpring(final float motionValue) {
		return motionValue;
	}

	@Override
	public void onSpringUpdate(@NonNull final Spring spring) {
		mSpring.setEndValue(spring.getCurrentValue());
	}

	@Override
	public void onSpringAtRest(final Spring spring) {

	}

	@Override
	public void onSpringActivate(final Spring spring) {

	}

	@Override
	public void onSpringEndStateChange(final Spring spring) {

	}
}
