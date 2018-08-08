package org.javacord.api.entity.message.embed.base;

import java.net.URL;
import java.util.Optional;

/**
 * This interface represents an embed footer.
 */
public interface BaseEmbedFooter {

    /**
     * Gets the footer text.
     *
     * @return The text of the footer.
     */
    Optional<String> getText();

    /**
     * Gets the url of the footer icon.
     *
     * @return The url of the footer icon.
     */
    Optional<URL> getIconUrl();

}