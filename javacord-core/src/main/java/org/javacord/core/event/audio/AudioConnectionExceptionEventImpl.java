package org.javacord.core.event.audio;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.audio.AudioConnectionExceptionEvent;

/**
 * The implementation of {@link AudioConnectionExceptionEvent}.
 */
public class AudioConnectionExceptionEventImpl implements AudioConnectionExceptionEvent {

    @Override
    public DiscordApi getApi() {
        return null;
    }

}
