package me.settingdust.laven.sponge

import org.spongepowered.api.Sponge
import org.spongepowered.api.plugin.PluginContainer
import org.spongepowered.api.service.ServiceManager
import java.util.*

inline fun <reified T> ServiceManager.provideUnchecked(): T = provideUnchecked(T::class.java)

inline fun <reified T> ServiceManager.provide(): Optional<T> = provide(T::class.java)

inline fun <reified T> PluginContainer.setProvider(impl: T) = Sponge.getServiceManager().setProvider(this, T::class.java, impl)