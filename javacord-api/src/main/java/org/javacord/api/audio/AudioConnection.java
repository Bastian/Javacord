package org.javacord.api.audio;

import org.javacord.api.audio.source.AudioSource;
import org.javacord.api.entity.channel.VoiceChannel;

import java.util.Optional;

/**
 * The class represents an audi connection to a {@link VoiceChannel}.
 */
public interface AudioConnection extends AudioConnectionAttachableListenerManager {

    /**
     * Queues the given audio source.
     */
    void queue(AudioSource source);

    /**
     * Gets the audio source that is currently at the head of the queue.
     *
     * @return The cuurent audio source.
     */
    Optional<AudioSource> getCurrentSource();

    /**
     * Gets the voice channel this audio connection belongs to.
     *
     * @return The voice channel.
     */
    VoiceChannel getChannel();

    /**
     * Disconnects from the current channel.
     */
    void disconnect();

}
