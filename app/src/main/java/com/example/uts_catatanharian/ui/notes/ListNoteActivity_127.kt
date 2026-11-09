package com.example.uts_catatanharian.ui.notes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.adapter.NoteAdapter_127
import com.example.uts_catatanharian.data.database.AppDatabase_127
import com.example.uts_catatanharian.data.repository.NoteRepository_127
import com.example.uts_catatanharian.utils.SessionManager_127
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListNoteActivity_127 : AppCompatActivity() {

    private lateinit var noteRepository: NoteRepository_127
    private lateinit var sessionManager: SessionManager_127
    private lateinit var noteAdapter: NoteAdapter_127

    private var currentUserId: Long = -1L
    private lateinit var rvAllNotes: RecyclerView
    private lateinit var tvEmptyList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_note)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_127)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Semua Catatan"

        toolbar.setNavigationOnClickListener { finish() }

        sessionManager = SessionManager_127(this)
        currentUserId = sessionManager.getUserId()

        val noteDao = AppDatabase_127.getDatabase_127(this).noteDao_127()
        noteRepository = NoteRepository_127(noteDao)

        rvAllNotes = findViewById(R.id.rv_all_notes_127)
        tvEmptyList = findViewById(R.id.tv_empty_list_127)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvAllNotes.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter_127(
            emptyList(),
            onEditClick = { note ->
                val intent = Intent(this, EditNoteActivity_127::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                }
                startActivity(intent)
            },
            onDeleteClick = { note ->
                showDeleteConfirmationDialog(note.id)
            }
        )
        rvAllNotes.adapter = noteAdapter
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            noteRepository.getAllNotes_127(currentUserId).collect { notes ->
                withContext(Dispatchers.Main) {
                    noteAdapter.updateNotes_127(notes)
                    if (notes.isEmpty()) {
                        tvEmptyList.visibility = android.view.View.VISIBLE
                        rvAllNotes.visibility = android.view.View.GONE
                    } else {
                        tvEmptyList.visibility = android.view.View.GONE
                        rvAllNotes.visibility = android.view.View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(noteId: Long) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            .setPositiveButton("Hapus") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    noteRepository.deleteNote_127(noteId, currentUserId)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ListNoteActivity_127, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
                        loadNotes()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
