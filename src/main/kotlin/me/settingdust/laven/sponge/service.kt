package me.settingdust.laven.sponge

import me.settingdust.laven.unwrap
import org.spongepowered.api.Sponge
import org.spongepowered.api.plugin.PluginContainer
import org.spongepowered.api.service.ServiceManager
import java.util.*

inline fun <reified T> ServiceManager.provide(): T? = provide(T::class.java).unwrap()

inline fun <reified T> ServiceManager.provideUnchecked(): T = provideUnchecked(T::class.java)

inline fun <reified T> PluginContainer.setProvider(impl: T) = Sponge.getServiceManager().setProvider(this, T::class.java, impl)