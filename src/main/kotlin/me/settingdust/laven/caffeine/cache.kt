package me.settingdust.laven.caffeine

import com.github.benmanes.caffeine.cache.Cache

operator fun <K, V> Cache<K, V>.set(key: K, value: V) = put(key, value)