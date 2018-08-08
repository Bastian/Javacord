package org.javacord.api.entity.message.embed.sent;

import org.javacord.api.entity.message.embed.base.BaseEmbedFooter;

import java.net.URL;
import java.util.Optional;

/**
 * This interface represents an embed footer.
 */
public interface SentEmbedFooter extends BaseEmbedFooter {

    /**
     * Gets the proxy url of the footer icon.
     *
     * @return The proxy url of the footer icon.
     */
    Optional<URL> getProxyIconUrl();

}