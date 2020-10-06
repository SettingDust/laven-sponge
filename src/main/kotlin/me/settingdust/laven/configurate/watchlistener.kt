package me.settingdust.laven.configurate

import me.settingdust.laven.absolutePath
import me.settingdust.laven.isDirectory
import me.settingdust.laven.openInputStream
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.loader.ConfigurationLoader
import ninja.leaping.configurate.reactive.Disposable
import ninja.leaping.configurate.reactive.Subscriber
import ninja.leaping.configurate.reference.ConfigurationReference
import ninja.leaping.configurate.reference.WatchServiceListener
import java.nio.file.Path
import java.nio.file.WatchEvent
import java.util.*

class WatchServiceListenerCallback<T>(
    val path: Path
) {
    lateinit var converterFunc: (WatchEvent<*>) -> T
    lateinit var callbackFunc: Subscriber<T>

    fun converter(converter: (WatchEvent<*>) -> T) {
        this.converterFunc = converter
    }

    fun callback(callback: Subscriber<T>) {
        this.callbackFunc = callback
    }
}

fun <T> Path.subscribe(
    listener: WatchServiceListener,
    callback: WatchServiceListenerCallback<T>.() -> Unit
): Disposable {
    val path = absolutePath
    val listenerCallback = WatchServiceListenerCallback<T>(path)
    callback(listenerCallback)
    val eventCallback: (WatchEvent<*>) -> Unit = { watchEvent ->
        listenerCallback.callbackFunc.submit(listenerCallback.converterFunc(watchEvent))
    }
    return if (isDirectory) {
        listener.listenToDirectory(path, eventCallback)
    } else {
        listener.listenToFile(path, eventCallback)
    }
}

fun Path.subscribe(listener: WatchServiceListener): WatchingPropertyResourceBundle {
    val path = absolutePath
    val callback = WatchingPropertyResourceBundle(path)
    subscribe<PropertyResourceBundle>(listener) {
        converter {
            PropertyResourceBundle(path.openInputStream().bufferedReader())
        }
        callback(callback)
    }
    return callback
}

fun Path.subscribe(listener: WatchServiceListener, callback: Subscriber<WatchEvent<*>>): Disposable {
    val path = absolutePath
    if (isDirectory) {
        return listener.listenToDirectory(path, callback)
    } else {
        return listener.listenToFile(path, callback)
    }
}

fun <N : ConfigurationNode> Path.subscribe(
    listener: WatchServiceListener,
    loaderFunc: (Path) -> ConfigurationLoader<N>
): ConfigurationReference<N> = listener.listenToConfiguration(loaderFunc, this)

class WatchingPropertyResourceBundle constructor(
    path: Path
) : ResourceBundle(), Subscriber<PropertyResourceBundle> {

    private var propertyResourceBundle: PropertyResourceBundle

    override fun submit(item: PropertyResourceBundle) {
        propertyResourceBundle = item
    }

    public override fun setParent(parent: ResourceBundle?) = super.setParent(parent)

    public override fun handleGetObject(key: String): Any? = propertyResourceBundle.handleGetObject(key)

    override fun getKeys(): Enumeration<String> = propertyResourceBundle.keys

    override fun handleKeySet(): Set<String> = propertyResourceBundle.keySet()

    init {
        propertyResourceBundle = PropertyResourceBundle(path.openInputStream())
    }
}