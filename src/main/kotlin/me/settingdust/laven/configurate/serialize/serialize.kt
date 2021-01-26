@file:Suppress("UnstableApiUsage")

package me.settingdust.laven.configurate.serialize

import com.google.common.reflect.TypeToken
import com.google.inject.Inject
import com.google.inject.Singleton
import me.settingdust.laven.deserialize
import me.settingdust.laven.typeTokenOf
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.kotlin.set
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection
import org.spongepowered.api.data.DataManager
import org.spongepowered.api.data.persistence.DataTranslators.CONFIGURATION_NODE
import org.spongepowered.api.item.inventory.ItemStackSnapshot
import java.time.LocalDateTime

inline fun <reified T> TypeSerializerCollection.register(serializer: TypeSerializer<in T>): TypeSerializerCollection =
    register(typeTokenOf<T>(), serializer)

@Singleton
class ItemStackSnapshotSerializer @Inject constructor(
    private val dataManager: DataManager
) : TypeSerializer<ItemStackSnapshot> {
    override fun deserialize(type: TypeToken<*>, value: ConfigurationNode): ItemStackSnapshot? =
        dataManager.deserialize(CONFIGURATION_NODE.translate(value))

    override fun serialize(type: TypeToken<*>, obj: ItemStackSnapshot?, value: ConfigurationNode) {
        value.set(CONFIGURATION_NODE.translate(obj?.toContainer() ?: return))
    }
}

@Singleton
class DateTimeSerializer : TypeSerializer<LocalDateTime> {
    override fun deserialize(type: TypeToken<*>, value: ConfigurationNode): LocalDateTime? = value.string?.let { LocalDateTime.parse(it) }

    override fun serialize(type: TypeToken<*>, obj: LocalDateTime?, value: ConfigurationNode) {
        obj?.let {
            value.set(it.toString())
        }
    }
}