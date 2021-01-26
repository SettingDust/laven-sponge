package me.settingdust.laven.configurate.reference

import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.ConfigurationOptions
import ninja.leaping.configurate.reference.ConfigurationReference

fun <N : ConfigurationNode> ConfigurationReference<N>.createEmptyNode(options: ConfigurationOptions = loader.defaultOptions) =
    loader.createEmptyNode()