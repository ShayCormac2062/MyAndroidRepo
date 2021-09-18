package com.example.firstlesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstlesson.classes.AkatsukiMember
import com.example.firstlesson.classes.Shinobi
import com.example.firstlesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val shinobi = Shinobi("Наруто", 35,
            "Деревня скрытого листа", 89, "Рассенган")
        val hidan = AkatsukiMember("Хидан", 25,
            "Деревня скрытых горячих источников", null, 200)
        checkShinobi(shinobi)
        checkAkatsuki(hidan)
        Toast.makeText(this, "Были запущены 2 класса, " +
                "наследуемых от одного общего класса, " +
                "и каждый наследуется от своего интерфейса. Для " +
                "просмотра деталей загляните в логи)", Toast.LENGTH_LONG).show()
        homework1(shinobi, hidan)
    }

    private fun homework1(shinobi: Shinobi, akatsuki: AkatsukiMember) {
        binding?.checkShinobi?.setOnClickListener {
            checkShinobi(shinobi)
            Toast.makeText(this,
                "Был создан экземпляр класса \"Шиноби\", посмотрите его поведение в логах",
                Toast.LENGTH_SHORT).show()
        }
        binding?.checkAkatsuki?.setOnClickListener {
            checkAkatsuki(akatsuki)
            Toast.makeText(this,
                "Был создан экземпляр класса \"Член Акатсуки\", посмотрите его поведение в логах",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkShinobi(shinobi: Shinobi) {
        shinobi.useTechnic()
        shinobi.leaveVillage()
        shinobi.wordParasite()
        shinobi.goToMission()
        shinobi.becomeALeader()
    }

    private fun checkAkatsuki(akatsuki: AkatsukiMember) {
        akatsuki.killOneHuman()
        akatsuki.smthAboutCharacter()
        akatsuki.catchJinchuriki()
        akatsuki.talkWithPain()
    }
}
