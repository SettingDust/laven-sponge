package me.settingdust.laven.sponge

import org.spongepowered.api.network.ChannelBuf

interface Packet {
    fun write(channelBuf: ChannelBuf)
}

fun ChannelBuf.writePacket(packet: Packet) = packet.write(this)