package com.example.firstlesson.ui.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.adapter.AllGoalsAdapter
import com.example.firstlesson.databinding.FragmentAllGoalsBinding
import com.example.firstlesson.databinding.FragmentCreateGoalBinding
import com.example.firstlesson.db.GoalsDatabase
import com.example.firstlesson.entity.Goal
import com.example.firstlesson.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AllGoalsFragment : Fragment() {

    private lateinit var binding: FragmentAllGoalsBinding
    private lateinit var goalsDatabase: GoalsDatabase
    private lateinit var locationClient: FusedLocationProviderClient
    private var calendar: Calendar? = null
    private var changingLongitude: Double? = null
    private var changingLatitude: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllGoalsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        goalsDatabase = (requireActivity() as MainActivity).goalsDatabase
        initRecyclerView()
        binding.addGoalBtn.setOnClickListener {
            showEditOrAddAlertDialog(null, 0)
        }
    }

    private fun initRecyclerView() {
        val goals = goalsDatabase.goalsDao().getAll() as ArrayList<Goal>
        with(binding.allGoals) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
            }
            adapter = AllGoalsAdapter(goals).apply {
                infoClickListener = {
                    showGoalAlertDialog(it)
                }
                deleteClickListener = {
                    goalsDatabase.goalsDao().deleteGoal(it.id)
                    initRecyclerView()
                }
                submitList(goals)
            }
        }
        setupNoGoalsNotification()
    }

    private fun showDatePicker(bindingOfEditScreen: FragmentCreateGoalBinding) {
        calendar = Calendar.getInstance()
        calendar?.let { calendar ->
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        showTimePicker(bindingOfEditScreen)
    }

    private fun showTimePicker(bindingOfEditScreen: FragmentCreateGoalBinding) {
        val datePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Выберите время для задачи")
            .build()
        with(datePicker) {
            show(this@AllGoalsFragment.childFragmentManager, "FUCK")
            addOnPositiveButtonClickListener {
                calendar?.let {
                    it[Calendar.HOUR_OF_DAY] = datePicker.hour
                    it[Calendar.MINUTE] = datePicker.minute
                    it[Calendar.SECOND] = 0
                    it[Calendar.MILLISECOND] = 0
                    bindingOfEditScreen.setupTime.text = convertToTime(calendar?.time?.time)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLocation()
                } else {
                    Toast.makeText(context, "Доступ к локации запрещён", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun convertToTime(time: Long?): String {
        time?.let {
            val date = Date(it)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("dd.MM.yyyy HH:mm").format(date)
            } else ""
        }
        return ""
    }


    private fun setupNoGoalsNotification() {
        with(binding) {
            noGoalsIcon.visibility =
                if (goalsDatabase.goalsDao().getAll().isNotEmpty()) View.GONE else View.VISIBLE
            noGoalsText.visibility =
                if (goalsDatabase.goalsDao().getAll().isNotEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun showGoalAlertDialog(goal: Goal) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(goal.title)
                .setMessage(getMessage(goal))
                .setPositiveButton("Понятно") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNeutralButton("Редактировать") { dialog, _ ->
                    showEditOrAddAlertDialog(goal, 1)
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun getMessage(goal: Goal): String =
        StringBuilder()
            .append("${goal.description}\nДата исполнения: ")
            .append(convertToTime(goal.date?.time))
            .append("\nДолгота: ")
            .append(goal.longitude ?: "не указано")
            .append("\nШирота: ")
            .append(goal.latitude ?: "не указано")
            .toString()

    private fun showEditOrAddAlertDialog(goal: Goal?, editOrAdd: Int) {
        val bindingOfEditScreen = FragmentCreateGoalBinding.inflate(LayoutInflater.from(context))
        var needToChangeDate = false
        val alert = context?.let {
            AlertDialog.Builder(it).apply {
                setTitle(if (editOrAdd == 1) "Редактируйте задачу" else "Создайте задачу")
                setView(bindingOfEditScreen.root)
            }.show()
        }
        when (editOrAdd) {
            1 -> {
                with(bindingOfEditScreen) {
                    enterTitleText.text = SpannableStringBuilder(goal?.title)
                    enterDescription.text = SpannableStringBuilder(goal?.description)
                    changingLongitude = goal?.longitude
                    changingLatitude = goal?.latitude
                }
            }
        }
        bindingOfEditScreen.setupTime.setOnClickListener {
            showDatePicker(bindingOfEditScreen)
            needToChangeDate = true
        }
        bindingOfEditScreen.setupLocation.setOnClickListener {
            setupLocation()
        }
        bindingOfEditScreen.okBtn.setOnClickListener {
            when (editOrAdd) {
                1 -> {
                    with(goalsDatabase.goalsDao()) {
                        goal?.id?.let { it1 ->
                            updateTitle(it1, bindingOfEditScreen.enterTitleText.text.toString())
                            updateDescription(
                                it1,
                                bindingOfEditScreen.enterDescription.text.toString()
                            )
                            updateDate(it1, calendar?.time)
                            updateLongitude(it1, changingLongitude)
                            updateLatitude(it1, changingLatitude)
                        }
                    }
                }
                else -> {
                    val newGoal = Goal(
                        0,
                        bindingOfEditScreen.enterTitleText.text.toString(),
                        bindingOfEditScreen.enterDescription.text.toString(),
                        if (needToChangeDate) calendar?.time else null,
                        changingLongitude,
                        changingLatitude
                    )
                    goalsDatabase.goalsDao().add(newGoal)
                    Snackbar.make(
                        binding.root,
                        "Задача \"${newGoal.title}\" была успешно добавлена",
                        2000
                    ).show()
                }
            }
            needToChangeDate = false
            changingLatitude = null
            changingLongitude = null
            initRecyclerView()
            alert?.dismiss()
        }
        bindingOfEditScreen.noBtn.setOnClickListener {
            alert?.dismiss()
        }
    }

    private fun setupLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            requestPermissions(permissions, 100)
        } else {
            locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                changingLatitude = location?.latitude
                changingLongitude = location?.longitude
                Toast.makeText(context,
                    if (location != null) "Локация обнаружена" else "Локация не обнаружена. Включите геолокацию и перезайдите",
                    if (location != null) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

}