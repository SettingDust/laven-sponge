/*
 * Configurate
 * Copyright (C) zml and Configurate contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("UnstableApiUsage")

package me.settingdust.laven.sponge

import com.google.common.reflect.TypeToken
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.attributed.SimpleAttributedConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.objectmapping.ObjectMappingException

operator fun <T : ConfigurationNode> T.get(path: Any): T = getNode(path) as T

operator fun <T : ConfigurationNode> T.get(vararg path: Any): T = getNode(*path) as T

operator fun ConfigurationNode.set(vararg path: Any, value: Any?) {
    get(*path).value = value
}

/**
 * Multi level contains
 */
operator fun ConfigurationNode.contains(path: Array<Any>): Boolean = get(*path).isVirtual.not()

/**
 * Contains for a single level
 *
 * @param path a single path element
 */
operator fun ConfigurationNode.contains(path: Any): Boolean = get(path).isVirtual.not()

@Throws(ObjectMappingException::class)
inline fun <reified V> ConfigurationNode.get(): V? {
    return getValue(typeTokenOf<V>(), null as V?)
}

@Throws(ObjectMappingException::class)
inline operator fun <reified V> ConfigurationNode.get(default: V): V {
    return getValue(typeTokenOf(), default)
}

@Throws(ObjectMappingException::class)
inline fun <reified V> ConfigurationNode.set(value: V?) {
    setValue(typeTokenOf(), value)
}

fun CommentedConfigurationNode.offerComment(comment: String) {
    if (this.comment.isPresent.not()) {
        this.setComment(comment)
    }
}

inline fun <reified T> typeTokenOf(): TypeToken<T> = object : TypeToken<T>() {}