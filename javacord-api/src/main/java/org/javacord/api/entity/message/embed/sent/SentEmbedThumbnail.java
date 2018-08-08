package org.javacord.api.entity.message.embed.sent;

import org.javacord.api.entity.message.embed.base.BaseEmbedThumbnail;

import java.net.URL;

/**
 * This interface represents an embed thumbnail.
 */
public interface SentEmbedThumbnail extends BaseEmbedThumbnail {

    /**
     * Gets the proxy url of the thumbnail.
     *
     * @return The proxy url of the thumbnail.
     */
    URL getProxyUrl();

    /**
     * Gets the height of the thumbnail.
     *
     * @return The height of the thumbnail.
     */
    int getHeight();

    /**
     * Gets the width of the thumbnail.
     *
     * @return The width of the thumbnail.
     */
    int getWidth();

}