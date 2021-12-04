package com.example.firstlesson.entity

import com.example.firstlesson.R

object TracksRepository {

    val songList: List<Track> = arrayListOf(
        Track(0,
            "Astronaut in the Ocean",
            "Masked Wolf",
            R.drawable.astronaut_in_the_ocean,
            R.raw.astronaut_in_the_ocean
        ),
        Track(1,"Baby mama",
            "Скриптонит",
            R.drawable.baby_mama,
            R.raw.baby_mama
        ),
        Track(2,"Бадабум",
        "Miyagi & Эндшпиль",
            R.drawable.badabum,
            R.raw.badabum),
        Track(3,"Copines",
        "Aya Nakamura",
            R.drawable.copines,
            R.raw.copines),
        Track(4,"Fuck you",
        "Lil Glory",
            R.drawable.fuck_you,
            R.raw.fuck_you),
        Track(5,"Корабли",
        "Lizer",
            R.drawable.korabli,
            R.raw.korabli),
        Track(6,"Mask Off",
        "Future",
            R.drawable.mask_off,
            R.raw.mask_off),
        Track(7,"MONTERO",
        "Lil Nas X",
            R.drawable.montero,
            R.raw.montero)
    )
}