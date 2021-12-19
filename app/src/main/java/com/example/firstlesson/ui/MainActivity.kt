package com.example.firstlesson.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.R
import com.example.firstlesson.adapter.AllGoalsAdapter
import com.example.firstlesson.databinding.ActivityMainBinding
import com.example.firstlesson.databinding.FragmentCreateGoalBinding
import com.example.firstlesson.db.DataBaseCreator
import com.example.firstlesson.db.GoalsDatabase
import com.example.firstlesson.entity.Goal
import com.example.firstlesson.ui.fragment.AllGoalsFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var goalsDatabase: GoalsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goalsDatabase = DataBaseCreator().initDB(this)
        begin()
    }

    private fun begin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AllGoalsFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_goals_btn) {
            if (goalsDatabase.goalsDao().getAll().isNotEmpty()) {
                AlertDialog.Builder(this)
                    .setMessage(R.string.delete_all_tasks_notification)
                    .setPositiveButton("Да") { dialog, _ ->
                        goalsDatabase.goalsDao().deleteAll()
                        begin()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Нет") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else Snackbar.make(binding.root, "У вас нет задач", 2000).show()
        }
        return true
    }
}
