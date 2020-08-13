package me.settingdust.laven.sponge

import me.settingdust.laven.FileEvent
import me.settingdust.laven.FileEventChannel
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.SimpleConfigurationNode
import ninja.leaping.configurate.hocon.HoconConfigurationLoader

class HoconFileEventChannel : FileEventChannel<ConfigurationNode>() {
    init {
        converter { path, kind ->
            var node: ConfigurationNode = SimpleConfigurationNode.root()
            if (kind != FileEvent.Kind.Delete)
                node = HoconConfigurationLoader.builder().setPath(path).build().load()
            node
        }
    }
}