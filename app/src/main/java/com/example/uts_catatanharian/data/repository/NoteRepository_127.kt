package com.example.uts_catatanharian.data.repository

import com.example.uts_catatanharian.data.dao.NoteDao_127
import com.example.uts_catatanharian.data.entity.NoteEntity_127
import kotlinx.coroutines.flow.Flow

class NoteRepository_127(private val noteDao: NoteDao_127) {
    suspend fun insertNote_127(note: NoteEntity_127): Long {
        return noteDao.insertNote_127(note)
    }

    suspend fun updateNote_127(note: NoteEntity_127): Int {
        return noteDao.updateNote_127(note)
    }

    suspend fun deleteNote_127(noteId: Long, userId: Long): Int {
        return noteDao.deleteNoteById_127(noteId, userId)
    }

    fun getAllNotes_127(userId: Long): Flow<List<NoteEntity_127>> {
        return noteDao.getAllNotesByUser_127(userId)
    }

    suspend fun getNoteById_127(noteId: Long, userId: Long): NoteEntity_127? {
        return noteDao.getNoteByIdAndUser_127(noteId, userId)
    }

    suspend fun getNoteCount_127(userId: Long): Int {
        return noteDao.getNoteCountByUser_127(userId)
    }
}
