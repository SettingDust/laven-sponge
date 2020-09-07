package me.settingdust.laven.sponge

import me.settingdust.laven.get
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.TextTemplate
import org.spongepowered.api.text.serializer.TextSerializers
import java.util.regex.Pattern
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun Text.isBlank() = isEmpty || toPlain().isBlank()

fun Text.isNotBlank() = !isBlank()

fun Text.isNotEmpty(): Boolean = !isEmpty

@ExperimentalContracts
fun Text?.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }

    return this == null || isEmpty
}

@ExperimentalContracts
fun Text?.isNullOrBlank(): Boolean {
    contract {
        returns(false) implies (this@isNullOrBlank != null)
    }

    return this == null || this.isBlank()
}

@ExperimentalStdlibApi
@ExperimentalContracts
fun Text.flatten(): List<Text> = buildList {
    if (!this@flatten.isNullOrEmpty()) {
        add(toBuilder().removeAll().build())
        children.map(Text::flatten).forEach(this::addAll)
    }
}

@ExperimentalContracts
@ExperimentalStdlibApi
fun Text.toTemplate(pattern: Pattern): TextTemplate {
    val template = TextTemplate.of()
    flatten().forEach {
        val plain = it.toPlain()
        val matcher = pattern.matcher(plain)
        template += TextTemplate.of(
            matcher[2],
            matcher[4],
            buildList {
                while (matcher.find()) {
                    add(matcher[1])
                    add(TextTemplate.arg(matcher[3]).format(it.format))
                }
                add(plain.substring(matcher.end()))
            }
        )

        if (template.isEmpty()) {
            template += TextTemplate.of(it)
        }
    }
    return template
}

@ExperimentalStdlibApi
@ExperimentalContracts
fun String.toTemplate(pattern: Pattern): TextTemplate =
    TextSerializers.FORMATTING_CODE.deserialize(this).toTemplate(pattern)

operator fun TextTemplate.plusAssign(template: TextTemplate) {
    concat(template)
}

fun TextTemplate.isEmpty() = arguments.isEmpty()