package org.javacord.api.entity.message.embed.sent;

import org.javacord.api.entity.message.embed.base.BaseEmbedVideo;

/**
 * This interface represents an embed video.
 */
public interface SentEmbedVideo extends BaseEmbedVideo {

    /**
     * Gets the height of the video.
     *
     * @return The height of the video.
     */
    int getHeight();

    /**
     * Gets the width of the video.
     *
     * @return The width of the video.
     */
    int getWidth();

}