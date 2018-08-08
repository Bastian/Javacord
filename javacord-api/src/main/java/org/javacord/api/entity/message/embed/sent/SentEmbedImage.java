package org.javacord.api.entity.message.embed.sent;

import org.javacord.api.entity.message.embed.base.BaseEmbedImage;

import java.net.URL;

/**
 * This interface represents an embed image.
 */
public interface SentEmbedImage extends BaseEmbedImage {

    /**
     * Gets the proxy url of the image.
     *
     * @return The proxy url of the image.
     */
    URL getProxyUrl();

    /**
     * Gets the height of the image.
     *
     * @return The height of the image.
     */
    int getHeight();

    /**
     * Gets the width of the image.
     *
     * @return The width of the image.
     */
    int getWidth();

}