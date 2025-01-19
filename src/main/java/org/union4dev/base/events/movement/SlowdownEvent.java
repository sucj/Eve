package org.union4dev.base.events.movement;

import org.union4dev.base.events.base.Event;

public class SlowdownEvent extends Event.EventCancellable {

    private final float strafe, forward;

    public SlowdownEvent(float strafeFactor, float forwardFactor) {
        this.strafe = strafeFactor;
        this.forward = forwardFactor;
    }

    public float getStrafe() {
        return this.strafe;
    }
    public float getForward() {
        return this.forward;
    }
}
