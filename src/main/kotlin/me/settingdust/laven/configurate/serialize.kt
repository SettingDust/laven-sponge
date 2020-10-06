@file:Suppress("UnstableApiUsage")

package me.settingdust.laven.configurate

import me.settingdust.laven.typeTokenOf
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection

inline fun <reified T> TypeSerializerCollection.register(serializer: TypeSerializer<in T>): TypeSerializerCollection =
    register(typeTokenOf<T>(), serializer)