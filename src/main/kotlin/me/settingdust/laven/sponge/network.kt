package me.settingdust.laven.sponge

import com.flowpowered.math.vector.Vector3d
import me.settingdust.laven.longToVector3d
import me.settingdust.laven.toPacketLong
import org.spongepowered.api.network.ChannelBinding
import org.spongepowered.api.network.ChannelBuf
import org.spongepowered.api.network.Message
import org.spongepowered.api.network.MessageHandler

fun ChannelBuf.readVector3d() = longToVector3d(readLong())

fun ChannelBuf.writeVector3d(vector3d: Vector3d): ChannelBuf = writeLong(vector3d.toPacketLong())

inline fun <reified T : Message> ChannelBinding.IndexedMessageChannel.registerMessage(
    messageId: Int,
    handler: MessageHandler<T>
) = registerMessage(T::class.java, messageId, handler)