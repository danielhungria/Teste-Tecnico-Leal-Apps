package br.com.dhungria.lealappsworkout.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "exercise_table")
@Parcelize
data class Exercise(
    @PrimaryKey
    val id: String,
    val name: Int = 0,
    val observation: String = "",
    val image: String? = "",
    val idTraining: String?
) : Parcelable
