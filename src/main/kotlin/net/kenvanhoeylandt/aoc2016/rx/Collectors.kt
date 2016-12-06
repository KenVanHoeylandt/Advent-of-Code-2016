package net.kenvanhoeylandt.aoc2016.rx

import io.reactivex.functions.BiConsumer

fun characterToStringBuilderCollector(): BiConsumer<StringBuilder, Char> {
    return BiConsumer { builder, character -> builder.append(character) }
}