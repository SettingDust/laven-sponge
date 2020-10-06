package me.settingdust.laven.sponge.event

import me.settingdust.laven.unwrap
import org.spongepowered.api.event.cause.Cause

inline fun <reified T> Cause.containsType() = containsType(T::class.java)

inline fun <reified T> Cause.allOf() = allOf(T::class.java)