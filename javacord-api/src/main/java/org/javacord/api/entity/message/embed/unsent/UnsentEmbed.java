package org.javacord.api.entity.message.embed.unsent;

import org.javacord.api.entity.message.embed.base.BaseEmbed;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents an embed.
 */
public interface UnsentEmbed extends BaseEmbed {

    /**
     * Gets the footer of the embed.
     *
     * @return The footer of the embed.
     */
    Optional<UnsentEmbedFooter> getFooter();

    /**
     * Gets the image of the embed.
     *
     * @return The image of the embed.
     */
    Optional<UnsentEmbedImage> getImage();

    /**
     * Gets the thumbnail of the embed.
     *
     * @return The thumbnail of the embed.
     */
    Optional<UnsentEmbedThumbnail> getThumbnail();

    /**
     * Gets the video of the embed.
     *
     * @return The video of the embed.
     */
    Optional<UnsentEmbedVideo> getVideo();

    /**
     * Gets the provider of the embed.
     *
     * @return The provider of the embed.
     */
    Optional<UnsentEmbedProvider> getProvider();

    /**
     * Gets the author of the embed.
     *
     * @return The author of the embed.
     */
    Optional<UnsentEmbedAuthor> getAuthor();

    /**
     * Gets the fields of the embed.
     *
     * @return The fields of the embed.
     */
    List<UnsentEmbedField> getFields();

}