package me.settingdust.laven.sponge

import me.settingdust.laven.present
import org.spongepowered.api.Sponge

val String.isPlayerExist: Boolean
    get() = Sponge.getServer().getPlayer(this).present