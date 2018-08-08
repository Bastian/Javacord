package org.javacord.api.audio.source;

import java.util.concurrent.TimeUnit;

/**
 * This class represents a seekable audio source.
 * Seekable audio sources allow you to jump at any position.
 * <p>
 * Examples for seekable audio sources are:
 * <ul>
 * <li>Files (e.g. a mp3 file)
 * <li>YouTube videos
 * </ul>
 * <p>
 * Examples for non-seekable audio sources are:
 * <ul>
 * <li>Streams (e.g. radio stations)
 * <li>Direct input (e.g. a microphone)
 * </ul>
 */
public interface SeekableAudioSource extends AudioSource {

    /**
     * Gets the current position of the audio source.
     *
     * @param timeUnit The time unit.
     * @return The current position.
     */
    long getCurrentPosition(TimeUnit timeUnit);

    /**
     * Jumps to the given position.
     *
     * @param position The position to jump to.
     * @param timeUnit The time unit of the given position.
     */
    void jumpTo(long position, TimeUnit timeUnit);

    /**
     * Fast forwards for the given amount of time.
     *
     * @param time The time to fast forward.
     * @param timeUnit The time unit of the given time.
     */
    void fastForward(long time, TimeUnit timeUnit);

    /**
     * Rewinds for the given amount of time.
     * If the final position would be negative, it will just start from the beginning.
     *
     * @param time The time to rewind.
     * @param timeUnit The time unit of the given time.
     */
    void rewind(long time, TimeUnit timeUnit);

}
