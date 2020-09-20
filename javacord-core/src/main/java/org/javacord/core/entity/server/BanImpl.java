package org.javacord.core.entity.server;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.entity.server.Server;
import org.javacord.core.DiscordApiImpl;

import java.util.Optional;

/**
 * The implementation of {@link Ban}.
 */
public class BanImpl implements Ban {

    /**
     * The server of the ban.
     */
    private final Server server;

    /**
     * The banned user.
     */
    private final User user;

    /**
     * The reason for the ban.
     */
    private final String reason;

    /**
     * Creates a new ban.
     *
     * @param server The server of the ban.
     * @param data The json data of the ban.
     */
    public BanImpl(Server server, JsonNode data) {
        this.server = server;
        this.user = ((DiscordApiImpl) server.getApi()).getOrCreateUser(data.get("user"));
        this.reason = data.has("reason") ? data.get("reason").asText() : null;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.ofNullable(reason);
    }
}
