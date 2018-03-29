package org.javacord.core.util.handler.guild.role;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.event.server.role.RoleChangeColorEvent;
import org.javacord.api.event.server.role.RoleChangeHoistEvent;
import org.javacord.api.event.server.role.RoleChangeMentionableEvent;
import org.javacord.api.event.server.role.RoleChangeNameEvent;
import org.javacord.api.event.server.role.RoleChangePermissionsEvent;
import org.javacord.api.event.server.role.RoleChangePositionEvent;
import org.javacord.api.listener.server.role.RoleChangeColorListener;
import org.javacord.api.listener.server.role.RoleChangeHoistListener;
import org.javacord.api.listener.server.role.RoleChangeMentionableListener;
import org.javacord.api.listener.server.role.RoleChangeNameListener;
import org.javacord.api.listener.server.role.RoleChangePermissionsListener;
import org.javacord.api.listener.server.role.RoleChangePositionListener;
import org.javacord.core.entity.permission.PermissionsImpl;
import org.javacord.core.entity.permission.RoleImpl;
import org.javacord.core.event.server.role.RoleChangeColorEventImpl;
import org.javacord.core.event.server.role.RoleChangeHoistEventImpl;
import org.javacord.core.event.server.role.RoleChangeMentionableEventImpl;
import org.javacord.core.event.server.role.RoleChangeNameEventImpl;
import org.javacord.core.event.server.role.RoleChangePermissionsEventImpl;
import org.javacord.core.event.server.role.RoleChangePositionEventImpl;
import org.javacord.core.util.gateway.PacketHandler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the guild role create packet.
 */
public class GuildRoleUpdateHandler extends PacketHandler {

    /**
     * Creates a new instance of this class.
     *
     * @param api The api.
     */
    public GuildRoleUpdateHandler(DiscordApi api) {
        super(api, true, "GUILD_ROLE_UPDATE");
    }

    @Override
    public void handle(JsonNode packet) {
        JsonNode roleJson = packet.get("role");
        long roleId = roleJson.get("id").asLong();
        api.getRoleById(roleId).map(role -> (RoleImpl) role).ifPresent(role -> {
            Color oldColorObject = role.getColor().orElse(null);
            int oldColor = role.getColorAsInt();
            int newColor = roleJson.get("color").asInt(0);
            if (oldColor != newColor) {
                role.setColor(newColor);

                RoleChangeColorEvent event = new RoleChangeColorEventImpl(
                        role, role.getColor().orElse(null), oldColorObject);

                List<RoleChangeColorListener> listeners = new ArrayList<>();
                listeners.addAll(role.getRoleChangeColorListeners());
                listeners.addAll(role.getServer().getRoleChangeColorListeners());
                listeners.addAll(api.getRoleChangeColorListeners());

                api.getEventDispatcher().dispatchEvent(
                        role.getServer(), listeners, listener -> listener.onRoleChangeColor(event));
            }

            boolean oldHoist = role.isDisplayedSeparately();
            boolean newHoist = roleJson.get("hoist").asBoolean(false);
            if (oldHoist != newHoist) {
                role.setHoist(newHoist);

                RoleChangeHoistEvent event = new RoleChangeHoistEventImpl(role, oldHoist);

                List<RoleChangeHoistListener> listeners = new ArrayList<>();
                listeners.addAll(role.getRoleChangeHoistListeners());
                listeners.addAll(role.getServer().getRoleChangeHoistListeners());
                listeners.addAll(api.getRoleChangeHoistListeners());

                api.getEventDispatcher().dispatchEvent(
                        role.getServer(), listeners, listener -> listener.onRoleChangeHoist(event));
            }

            boolean oldMentionable = role.isMentionable();
            boolean newMentionable = roleJson.get("mentionable").asBoolean(false);
            if (oldMentionable != newMentionable) {
                role.setMentionable(newMentionable);

                RoleChangeMentionableEvent event = new RoleChangeMentionableEventImpl(role, oldMentionable);

                List<RoleChangeMentionableListener> listeners = new ArrayList<>();
                listeners.addAll(role.getRoleChangeMentionableListeners());
                listeners.addAll(role.getServer().getRoleChangeMentionableListeners());
                listeners.addAll(api.getRoleChangeMentionableListeners());

                api.getEventDispatcher().dispatchEvent(role.getServer(),
                        listeners, listener -> listener.onRoleChangeMentionable(event));
            }

            String oldName = role.getName();
            String newName = roleJson.get("name").asText();
            if (!oldName.equals(newName)) {
                role.setName(newName);

                RoleChangeNameEvent event = new RoleChangeNameEventImpl(role, newName, oldName);

                List<RoleChangeNameListener> listeners = new ArrayList<>();
                listeners.addAll(role.getRoleChangeNameListeners());
                listeners.addAll(role.getServer().getRoleChangeNameListeners());
                listeners.addAll(api.getRoleChangeNameListeners());

                api.getEventDispatcher().dispatchEvent(role.getServer(),
                        listeners, listener -> listener.onRoleChangeName(event));
            }

            Permissions oldPermissions = role.getPermissions();
            PermissionsImpl newPermissions = new PermissionsImpl(roleJson.get("permissions").asInt(), 0);
            if (!oldPermissions.equals(newPermissions)) {
                role.setPermissions(newPermissions);

                RoleChangePermissionsEvent event =
                        new RoleChangePermissionsEventImpl(role, newPermissions, oldPermissions);

                List<RoleChangePermissionsListener> listeners = new ArrayList<>();
                listeners.addAll(role.getRoleChangePermissionsListeners());
                listeners.addAll(role.getServer().getRoleChangePermissionsListeners());
                listeners.addAll(api.getRoleChangePermissionsListeners());

                api.getEventDispatcher().dispatchEvent(role.getServer(),
                        listeners, listener -> listener.onRoleChangePermissions(event));
            }

            int oldPosition = role.getPosition();
            int newPosition = roleJson.get("position").asInt();
            if (oldPosition != newPosition) {
                role.setPosition(newPosition);

                RoleChangePositionEvent event = new RoleChangePositionEventImpl(role, newPosition, oldPosition);

                List<RoleChangePositionListener> listeners = new ArrayList<>();
                listeners.addAll(role.getRoleChangePositionListeners());
                listeners.addAll(role.getServer().getRoleChangePositionListeners());
                listeners.addAll(api.getRoleChangePositionListeners());

                api.getEventDispatcher().dispatchEvent(role.getServer(),
                        listeners, listener -> listener.onRoleChangePosition(event));
            }
        });
    }

}