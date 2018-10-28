package org.javacord.api.audio;

import java.util.concurrent.TimeUnit;

public class SilentAudioSource implements AudioSource {

    public static final byte[] SILENCE_FRAME = {(byte) 0xF8, (byte) 0xFF, (byte) 0xFE};

    private final long initialDuration;
    private long duration;

    /**
     * Creates a new silent audio source.
     *
     * @param duration How long it should be silent.
     * @param unit A {@code TimeUnit} determining how to interpret the {@code duration} parameter.
     */
    public SilentAudioSource(long duration, TimeUnit unit) {
        initialDuration = unit.toMillis(duration) / 20;
        this.duration = initialDuration;
    }

    @Override
    public byte[] getNextFrame() {
        return null;
    }

    @Override
    public boolean hasNextFrame() {
        duration--;
        return false;
    }

    @Override
    public boolean hasFinished() {
        return duration <= 0;
    }

    @Override
    public boolean isMuted() {
        return true;
    }

    @Override
    public void setMuted(boolean muted) {
        // NOP
    }

    @Override
    public AudioSource copy() {
        return new SilentAudioSource(initialDuration * 20, TimeUnit.MILLISECONDS);
    }
}
