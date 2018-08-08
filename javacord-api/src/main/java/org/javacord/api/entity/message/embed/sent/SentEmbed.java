package org.javacord.api.entity.message.embed.sent;

import org.javacord.api.entity.message.embed.base.BaseEmbed;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents an embed.
 */
public interface SentEmbed extends BaseEmbed {

    /**
     * Gets the type of the embed.
     * (always "rich" for webhook embeds)
     *
     * @return The type of the embed.
     */
    String getType();

    /**
     * Gets the footer of the embed.
     *
     * @return The footer of the embed.
     */
    Optional<SentEmbedFooter> getFooter();

    /**
     * Gets the image of the embed.
     *
     * @return The image of the embed.
     */
    Optional<SentEmbedImage> getImage();

    /**
     * Gets the thumbnail of the embed.
     *
     * @return The thumbnail of the embed.
     */
    Optional<SentEmbedThumbnail> getThumbnail();

    /**
     * Gets the video of the embed.
     *
     * @return The video of the embed.
     */
    Optional<SentEmbedVideo> getVideo();

    /**
     * Gets the provider of the embed.
     *
     * @return The provider of the embed.
     */
    Optional<SentEmbedProvider> getProvider();

    /**
     * Gets the author of the embed.
     *
     * @return The author of the embed.
     */
    Optional<SentEmbedAuthor> getAuthor();

    /**
     * Gets the fields of the embed.
     *
     * @return The fields of the embed.
     */
    List<SentEmbedField> getFields();

}