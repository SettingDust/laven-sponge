package me.settingdust.laven.sponge

import me.settingdust.laven.*
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.SimpleConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds

class HoconFileEventChannel(private val loader: HoconConfigurationLoader) : FileEventChannel<CommentedConfigurationNode>() {

    init {
        converter { path, kind ->
            var node: CommentedConfigurationNode = SimpleCommentedConfigurationNode.root()
            if (kind != FileEvent.Kind.Delete) {
                node = loader.load()
            }
            node
        }
    }
}