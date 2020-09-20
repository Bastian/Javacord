package org.javacord.core.entity.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.Javacord;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.user.User2;
import org.javacord.core.DiscordApiImpl;
import org.javacord.core.entity.IconImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Maps a member object.
 *
 * @see <a href="https://discord.com/developers/docs/resources/user#user-object">Discord Docs</a>
 */
public class User2Impl implements User2 {

    private final DiscordApiImpl api;
    private final Long id;
    private final String name;
    private final String discriminator;
    private final String avatarHash;
    private final boolean bot;

    public User2Impl(DiscordApiImpl api, JsonNode data) {
        this.api = api;
        id = data.get("id").asLong();
        name = data.get("username").asText();
        discriminator = data.get("discriminator").asText();
        if (data.hasNonNull("avatar")) {
            avatarHash = data.get("avatar").asText();
        } else {
            avatarHash = null;
        }
        bot = data.hasNonNull("bot") && data.get("bot").asBoolean();
    }

    @Override
    public DiscordApi getApi() {
        return api;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * Gets the avatar for the given details.
     *
     * @param api The discord api instance.
     * @param avatarHash The avatar hash or {@code null} for default avatar.
     * @param discriminator The discriminator if default avatar is wanted.
     * @param userId The user id.
     * @return The avatar for the given details.
     */
    public static Icon getAvatar(DiscordApi api, String avatarHash, String discriminator, long userId) {
        StringBuilder url = new StringBuilder("https://" + Javacord.DISCORD_CDN_DOMAIN + "/");
        if (avatarHash == null) {
            url.append("embed/avatars/")
                    .append(Integer.parseInt(discriminator) % 5)
                    .append(".png");
        } else {
            url.append("avatars/")
                    .append(userId).append('/').append(avatarHash)
                    .append(avatarHash.startsWith("a_") ? ".gif" : ".png");
        }
        try {
            return new IconImpl(api, new URL(url.toString()));
        } catch (MalformedURLException e) {
            throw new AssertionError("Found a malformed avatar url. Please update to the latest Javacord " +
                    "version or create an issue on GitHub if you are already using the latest one.");
        }
    }

    @Override
    public Icon getAvatar() {
        return getAvatar(api, avatarHash, discriminator, getId());
    }

    @Override
    public boolean hasDefaultAvatar() {
        return avatarHash == null;
    }

    @Override
    public boolean isBot() {
        return bot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("User (id: %s, name: %s)", getIdAsString(), getName());
    }
}
