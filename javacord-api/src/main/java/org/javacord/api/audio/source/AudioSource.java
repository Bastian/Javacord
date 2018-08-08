package org.javacord.api.audio.source;

import org.javacord.api.audio.AudioConnection;

import java.util.Optional;

/**
 * The class represents an audio source that can be queued in an {@link AudioConnection}.
 */
public interface AudioSource {

    /**
     * Tries to cast the audio source to the given class.
     * <p>
     * This is just an other way then casting.
     *
     * @param clazz The clazz to cast to.
     * @param <T> The class type.
     * @return The cast audio source.
     */
    <T extends AudioSource> Optional<T> as(Class<T> clazz);

}
