package me.settingdust.laven.sponge

import me.settingdust.laven.unwrap
import org.spongepowered.api.CatalogType
import org.spongepowered.api.GameRegistry

inline fun <reified T : CatalogType> GameRegistry.getType(id: String) = getType(T::class.java, id).unwrap()