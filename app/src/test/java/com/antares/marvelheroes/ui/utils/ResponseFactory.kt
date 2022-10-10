package com.antares.marvelheroes.ui.utils

import com.antares.marvelheroes.data.characters.*

object ResponseFactory {

    fun characterResponse() = CharacterResponse(
        "test",
        "test",
        1,
        "test",
        data(),
        "test",
        "test"
    )

    fun data() = Data(
        1,
        1,
        1,
        listOf(characters()),
        1
    )

    fun characters() = Result(
        comics(),
        "Test",
        events(),
        1,
        "Test",
        "test",
        "test",
        series(),
        stories(),
        thumbnail(),
        listOf(url())
    )

    fun comics() = Comics(
        1,
        "test",
        listOf(item()),
        1,
    )

    fun events() = Events(
        1,
        "test",
        listOf(item()),
        1
    )

    fun series() = Series(
        1,
        "test",
        listOf(item()),
        1
    )

    fun stories() = Stories(
        1,
        "test",
        listOf(itemxxx()),
        1
    )

    fun thumbnail() = Thumbnail(
        "test",
        ".jpg"
    )

    fun item() = Item(
        "test",
        "test"
    )

    fun itemxxx() = ItemXXX(
        "test",
        "test",
        "test"
    )

    fun url() = Url(
        "test",
        "test"
    )

}