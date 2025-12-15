package com.example.noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val color: String = NoteColor.DEFAULT.colorCode
)

enum class NoteColor(
    val colorName: String,
    val colorCode: String,
    val description: String
) {
    DEFAULT("По умолчанию", "#FFF9C4", "Не важно"),
    LOW("Низкая", "#C8E6C9", "Можно подождать"),
    MEDIUM("Средняя", "#FFECB3", "Важно"),
    HIGH("Высокая", "#FFCDD2", "Срочно"),
    CRITICAL("Критическая", "#F8BBD0", "Очень срочно");

    companion object {
        fun fromCode(code: String): NoteColor {
            return values().find { it.colorCode == code } ?: DEFAULT
        }
    }
}