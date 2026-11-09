package com.example.uts_catatanharian.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes_127",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity_127::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteEntity_127(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val user_id: Long,
    val judul: String,
    val isi: String,
    val tanggal: String
)
