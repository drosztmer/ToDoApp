package com.codecool.todoapp.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codecool.todoapp.R
import com.codecool.todoapp.data.models.Priority
import com.codecool.todoapp.data.models.ToDoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>) {
        emptyDatabase.value = toDoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else {
            !(title.isEmpty() || description.isEmpty())
        }
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            getApplication<Application>().resources.getString(R.string.high_priority) -> {
                Priority.HIGH
            }
            getApplication<Application>().resources.getString(R.string.medium_priority) -> {
                Priority.MEDIUM
            }
            getApplication<Application>().resources.getString(R.string.low_priority) -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

}