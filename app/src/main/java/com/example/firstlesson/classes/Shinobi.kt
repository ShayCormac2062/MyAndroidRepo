package com.example.firstlesson.classes

import com.example.firstlesson.interfaces.Anbu

class Shinobi(
    private var nameOfShinobi: String, private var ageOfShinobi: Int,
    private var villageOfShinobi: String,
    var amountOfTechnics: Int,
    var favoriteTechnic: String
) : NarutoCharacter(nameOfShinobi, ageOfShinobi, villageOfShinobi), Anbu {

    fun useTechnic() {
        println("$favoriteTechnic!!!!")
        if (amountOfTechnics > 0) {
            amountOfTechnics--
        }
        println("Проверка 1-го метода класса \"Шиноби\" прошла успешно!")
    }

    fun leaveVillage() {
        println("Я покидаю деревню...")
        amountOfTechnics = 0
        favoriteTechnic = "Отсутствует"
        println("Проверка 2-го метода класса \"Шиноби\" прошла успешно!")
    }

    override fun goToMission() {
        println("Проверка 1-го метода интерфейса \"Анбу\" прошёл успешно!")
    }

    override fun becomeALeader() {
        println("Проверка 2-го метода интерфейса \"Анбу\" прошёл успешно!")
    }
}