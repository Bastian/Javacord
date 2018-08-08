package org.javacord.api.entity.message.embed.base;

import org.javacord.api.entity.Nameable;

import java.net.URL;
import java.util.Optional;

/**
 * This interface represents an embed author.
 */
public interface BaseEmbedAuthor extends Nameable {

    /**
     * Gets the url of the author.
     *
     * @return The url of the author.
     */
    Optional<URL> getUrl();

    /**
     * Gets the url of the author icon.
     *
     * @return The url of the author icon.
     */
    Optional<URL> getIconUrl();

}
