package me.settingdust.laven.sponge

import org.spongepowered.api.command.CommandCallable
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.args.CommandElement
import org.spongepowered.api.command.args.parsing.InputTokenizer
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.text.Text

fun commandSpec(
    description: Text? = null,
    extendedDescription: Text? = null,
    permission: String? = null,
    vararg arguments: CommandElement,
    childCommandFallback: Boolean = false,
    children: Map<List<String>, CommandCallable> = mapOf(),
    inputTokenizer: InputTokenizer = InputTokenizer.quotedStrings(false),
    executor: ((CommandSource, CommandContext) -> CommandResult)? = null,
): CommandSpec {
    val builder = CommandSpec.builder()
    description?.let(builder::description)
    extendedDescription?.let(builder::extendedDescription)
    permission?.let(builder::permission)
    builder.childArgumentParseExceptionFallback(childCommandFallback)
    builder.children(children)
    builder.inputTokenizer(inputTokenizer)
    builder.arguments(*arguments)
    executor?.let(builder::executor)
    return builder.build()
}