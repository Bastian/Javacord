package org.javacord.api.entity.message.embed.base;

import java.awt.Color;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * This interface represents an embed.
 */
public interface BaseEmbed {

    /**
     * Gets the title of the embed.
     *
     * @return The title of the embed.
     */
    Optional<String> getTitle();

    /**
     * Gets the description of the embed.
     *
     * @return The description of the embed.
     */
    Optional<String> getDescription();

    /**
     * Gets the url of the embed.
     *
     * @return The url of the embed.
     */
    Optional<URL> getUrl();

    /**
     * Gets the timestamp of the embed.
     *
     * @return The timestamp of the embed.
     */
    Optional<Instant> getTimestamp();

    /**
     * Gets the color of the embed.
     *
     * @return The color of the embed.
     */
    Optional<Color> getColor();

    /**
     * Gets the footer of the embed.
     *
     * @return The footer of the embed.
     */
    Optional<? extends BaseEmbedFooter> getFooter();

    /**
     * Gets the image of the embed.
     *
     * @return The image of the embed.
     */
    Optional<? extends BaseEmbedImage> getImage();

    /**
     * Gets the thumbnail of the embed.
     *
     * @return The thumbnail of the embed.
     */
    Optional<? extends BaseEmbedThumbnail> getThumbnail();

    /**
     * Gets the video of the embed.
     *
     * @return The video of the embed.
     */
    Optional<? extends BaseEmbedVideo> getVideo();

    /**
     * Gets the provider of the embed.
     *
     * @return The provider of the embed.
     */
    Optional<? extends BaseEmbedProvider> getProvider();

    /**
     * Gets the author of the embed.
     *
     * @return The author of the embed.
     */
    Optional<? extends BaseEmbedAuthor> getAuthor();

    /**
     * Gets the fields of the embed.
     *
     * @return The fields of the embed.
     */
    List<? extends BaseEmbedField> getFields();

}