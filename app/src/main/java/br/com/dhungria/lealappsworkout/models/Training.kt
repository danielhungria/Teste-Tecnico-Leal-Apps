package br.com.dhungria.lealappsworkout.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import kotlinx.parcelize.Parcelize

@Entity(tableName = "training_table")
@Parcelize
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: Int = 0,
    val description: String = ""
//    val data: Timestamp? = null
): Parcelable
