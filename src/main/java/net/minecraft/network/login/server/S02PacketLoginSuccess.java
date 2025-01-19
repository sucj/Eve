package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S02PacketLoginSuccess implements Packet<INetHandlerLoginClient> {
    private GameProfile profile;

    public S02PacketLoginSuccess() {}

    public S02PacketLoginSuccess(GameProfile profileIn) {
        this.profile = profileIn;
    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        String uuid = buf.readStringFromBuffer(36);
        String name = buf.readStringFromBuffer(16);
        this.profile = new GameProfile(UUID.fromString(uuid), name);
    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        UUID uuid = this.profile.getId();
        buf.writeString(uuid == null ? "" : uuid.toString());
        buf.writeString(this.profile.getName());
    }

    public void processPacket(INetHandlerLoginClient handler) {
        handler.handleLoginSuccess(this);
    }

    public GameProfile getProfile() {
        return this.profile;
    }
}
