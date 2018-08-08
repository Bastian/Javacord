package org.javacord.api.listener.audio;

import org.javacord.api.event.audio.AudioConnectionExceptionEvent;
import org.javacord.api.listener.GloballyAttachableListener;

/**
 * This listener listens to audio connection exceptions.
 */
@FunctionalInterface
public interface AudioConnectionExceptionListener extends AudioConnectionAttachableListener, GloballyAttachableListener {

    /**
     * This method is called every time an exceptions occurs in an audio connection.
     *
     * @param event The event.
     */
    void onAudioConnectionException(AudioConnectionExceptionEvent event);
}
