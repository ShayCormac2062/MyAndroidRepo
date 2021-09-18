package com.example.firstlesson.classes

open class NarutoCharacter(val name: String,
                           var age: Int,
                           var village: String) {

    fun wordParasite() {
        val words: Array<String> = arrayOf("Dattebayo!", "Dattebane!", "Dattebasa")
        println(words[(0 until words.size).random()])
        println("Проверка 1-го метода класса \"Персонаж Наруто\" прошёл успешно!")

    }

    fun smthAboutCharacter() {
        println("Привет! Меня зовут $name, и мне $age лет. Я живу в деревне под названием '$village'")
        println("Проверка 2-го метода класса \"Персонаж Наруто\" прошёл успешно!")
    }

}