package br.com.dhungria.lealappsworkout.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "training_table")
@Parcelize
data class Training(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: Int = 0,
    val description: String = "",
    val date: Long,
    val image: String? = ""
): Parcelable
