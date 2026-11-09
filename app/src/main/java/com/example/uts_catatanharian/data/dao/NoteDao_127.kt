package com.example.uts_catatanharian.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.uts_catatanharian.data.entity.NoteEntity_127
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao_127 {
    @Insert
    suspend fun insertNote_127(note: NoteEntity_127): Long

    @Update
    suspend fun updateNote_127(note: NoteEntity_127): Int

    @Query("DELETE FROM notes_127 WHERE id = :noteId AND user_id = :userId")
    suspend fun deleteNoteById_127(noteId: Long, userId: Long): Int

    @Query("SELECT * FROM notes_127 WHERE user_id = :userId ORDER BY tanggal DESC")
    fun getAllNotesByUser_127(userId: Long): Flow<List<NoteEntity_127>>

    @Query("SELECT * FROM notes_127 WHERE id = :noteId AND user_id = :userId LIMIT 1")
    suspend fun getNoteByIdAndUser_127(noteId: Long, userId: Long): NoteEntity_127?

    @Query("SELECT COUNT(*) FROM notes_127 WHERE user_id = :userId")
    suspend fun getNoteCountByUser_127(userId: Long): Int
}
