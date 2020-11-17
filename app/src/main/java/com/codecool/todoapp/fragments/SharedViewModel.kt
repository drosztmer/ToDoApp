package com.codecool.todoapp.fragments

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.codecool.todoapp.R
import com.codecool.todoapp.data.models.Priority

class SharedViewModel(application: Application): AndroidViewModel(application) {

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else {
            !(title.isEmpty() || description.isEmpty())
        }
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            getApplication<Application>().resources.getString(R.string.high_priority) -> { Priority.HIGH }
            getApplication<Application>().resources.getString(R.string.medium_priority) -> { Priority.MEDIUM }
            getApplication<Application>().resources.getString(R.string.low_priority) -> { Priority.LOW }
            else -> Priority.LOW
        }
    }

}