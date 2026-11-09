package com.example.uts_catatanharian.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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
import com.example.uts_catatanharian.ui.auth.LoginActivity_127
import com.example.uts_catatanharian.ui.notes.AddNoteActivity_127
import com.example.uts_catatanharian.ui.notes.EditNoteActivity_127
import com.example.uts_catatanharian.ui.notes.ListNoteActivity_127
import com.example.uts_catatanharian.utils.SessionManager_127
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity_127 : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager_127
    private lateinit var noteRepository: NoteRepository_127
    private lateinit var noteAdapter: NoteAdapter_127

    private var currentUserId: Long = -1L
    private lateinit var tvGreeting: TextView
    private lateinit var tvNoteCount: TextView
    private lateinit var tvEmptyState: TextView
    private lateinit var rvRecentNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager_127(this)
        val noteDao = AppDatabase_127.getDatabase_127(this).noteDao_127()
        noteRepository = NoteRepository_127(noteDao)

        currentUserId = sessionManager.getUserId()
        val username = sessionManager.getUsername() ?: "Pengguna"

        tvGreeting = findViewById(R.id.tv_greeting_127)
        tvNoteCount = findViewById(R.id.tv_note_count_127)
        tvEmptyState = findViewById(R.id.tv_empty_state_127)
        rvRecentNotes = findViewById(R.id.rv_recent_notes_127)

        tvGreeting.text = "Halo, $username"

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        rvRecentNotes.layoutManager = LinearLayoutManager(this)
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
        rvRecentNotes.adapter = noteAdapter
    }

    private fun setupListeners() {
        val btnAddNote = findViewById<MaterialButton>(R.id.btn_add_note_127)
        val tvViewAll = findViewById<TextView>(R.id.tv_view_all_127)
        val btnLogout = findViewById<ImageButton>(R.id.btn_logout_127)

        btnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity_127::class.java)
            startActivity(intent)
        }

        tvViewAll.setOnClickListener {
            val intent = Intent(this, ListNoteActivity_127::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    sessionManager.logoutUser()
                    val intent = Intent(this, LoginActivity_127::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = noteRepository.getNoteCount_127(currentUserId)
            noteRepository.getAllNotes_127(currentUserId).collect { notes ->
                withContext(Dispatchers.Main) {
                    tvNoteCount.text = count.toString()
                    val recentList = notes.take(5) // Show top 5 recent notes on dashboard
                    noteAdapter.updateNotes_127(recentList)

                    if (notes.isEmpty()) {
                        tvEmptyState.visibility = android.view.View.VISIBLE
                        rvRecentNotes.visibility = android.view.View.GONE
                    } else {
                        tvEmptyState.visibility = android.view.View.GONE
                        rvRecentNotes.visibility = android.view.View.VISIBLE
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
                        Toast.makeText(this@DashboardActivity_127, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
                        loadData()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
