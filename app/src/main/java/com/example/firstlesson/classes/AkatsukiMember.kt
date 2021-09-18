package com.example.firstlesson.classes

import com.example.firstlesson.interfaces.JinchurikiHunter

class AkatsukiMember(private var nameOfMember: String,
                     private var ageOfMember: Int,
                     private var villageOfMember: String,
                     private var partner: AkatsukiMember?,
                     private var amountOfDeaths: Int
                     ) : NarutoCharacter(nameOfMember, ageOfMember, villageOfMember), JinchurikiHunter {

    fun killOneHuman() {
        when(nameOfMember) {
            "Пейн" -> println("Познай боль")
            "Конан" -> println("У тебя был шанс...")
            "Хидан" -> println("Джашин будет доволен)")
            "Какузу" -> println("За твою голову мне дорого отвалят")
            "Итачи" -> println("Прости, это не твоя вина")
            "Тоби" -> println("Аааа!!! Я не хотел, правда. Ты живой? Эх, жаль")
            "Дейдара" -> println("Исскуство - это ВЗРЫВ!!!")
            "Сасори" -> println("Ты прекрасно подойдёшь к моей коллекции")
            "Кисаме" -> println("Ты жалок")
            "Орочимару" -> println("Твоя душа пренадлежит мне!!!")
            else -> println()
        }
        amountOfDeaths++
        println("Проверка 1-го метода класса \"Акатсуки\" прошёл успешно!")
    }

    override fun catchJinchuriki() {
        amountOfDeaths++
        println("Проверка 1-го метода интерфейса \"Охотник на Джинчурики\" прошёл успешно!")
    }

    override fun talkWithPain() {
        println("Проверка 2-го метода интерфейса \"Охотник на Джинчурики\" прошёл успешно!")
    }
}
