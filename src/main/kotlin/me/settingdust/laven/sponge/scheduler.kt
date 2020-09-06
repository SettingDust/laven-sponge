package me.settingdust.laven.sponge

import org.spongepowered.api.plugin.PluginContainer
import org.spongepowered.api.scheduler.Task
import java.util.concurrent.TimeUnit

fun task(
    pluginContainer: PluginContainer,
    name: String? = null,
    async: Boolean = false,
    delay: Long? = null,
    interval: Long? = null,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    consumer: Task.() -> Unit
): Task {
    val builder = Task.builder()
    name?.apply(builder::name)
    async.takeIf { it }?.apply { builder.async() }
    delay?.apply { builder.delay(this, timeUnit) }
    interval?.apply { builder.interval(this, timeUnit) }
    return builder.execute(consumer).submit(pluginContainer)
}