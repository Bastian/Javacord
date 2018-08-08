package org.javacord.api.audio.source;

/**
 * This class represents a pauseable audio source.
 * <p>
 * Examples for pauseable audio sources are:
 * <ul>
 * <li>Files (e.g. a mp3 file)
 * <li>YouTube videos
 * </ul>
 * <p>
 * Examples for non-pauseable audio sources are:
 * <ul>
 * <li>Streams (e.g. radio stations)
 * <li>Direct input (e.g. a microphone)
 * </ul>
 */
public interface PausableAudioSource extends AudioSource {

    /**
     * Pauses this audio source.
     *
     * @return If the state of the audio source changed ({@code true} = not paused).
     */
    boolean pause();

    /**
     * Resumes this audio source.
     *
     * @return If the state of the audio source changed ({@code true} = paused).
     */
    boolean resume();

}
