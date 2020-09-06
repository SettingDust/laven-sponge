@file:Suppress("UnstableApiUsage")

package me.settingdust.laven.sponge

import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Event
import org.spongepowered.api.event.Order
import org.spongepowered.api.plugin.PluginContainer
import org.spongepowered.api.scheduler.Task
import java.util.concurrent.TimeUnit

fun <T : Event> PluginContainer.registerListener(
    eventType: Class<T>,
    order: Order = Order.DEFAULT,
    beforeModifications: Boolean = false,
    listener: T.() -> Unit
) = Sponge.getEventManager().registerListener(this, eventType, order, beforeModifications, listener)

inline fun <reified T : Event> PluginContainer.registerListener(
    order: Order = Order.DEFAULT,
    beforeModifications: Boolean = false,
    noinline listener: T.() -> Unit
) = registerListener(T::class.java, order, beforeModifications, listener)

fun PluginContainer.registerListeners(receiver: Any) = Sponge.getEventManager().registerListeners(this, receiver)

fun PluginContainer.task(
    name: String? = null,
    async: Boolean = false,
    delay: Long? = null,
    interval: Long? = null,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    consumer: Task.() -> Unit
) = task(this, name, async, delay, interval, timeUnit, consumer)