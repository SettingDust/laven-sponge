@file:Suppress("UnstableApiUsage")

package me.settingdust.laven

import com.google.common.reflect.TypeToken
import com.google.inject.Injector

@Suppress("UNCHECKED_CAST")
operator fun <T> Injector.get(typeToken: TypeToken<T>): T = getInstance(typeToken.rawType as Class<T>)