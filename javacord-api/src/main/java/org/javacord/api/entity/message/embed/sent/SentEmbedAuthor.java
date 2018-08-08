package org.javacord.api.entity.message.embed.sent;

import org.javacord.api.entity.message.embed.base.BaseEmbedAuthor;

import java.net.URL;
import java.util.Optional;

/**
 * This interface represents an embed author.
 */
public interface SentEmbedAuthor extends BaseEmbedAuthor {

    /**
     * Gets the proxy url of the author icon.
     *
     * @return The proxy url of the author icon.
     */
    Optional<URL> getProxyIconUrl();

}
