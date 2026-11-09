package com.example.uts_catatanharian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.data.entity.NoteEntity_127

class NoteAdapter_127(
    private var noteList: List<NoteEntity_127>,
    private val onEditClick: (NoteEntity_127) -> Unit,
    private val onDeleteClick: (NoteEntity_127) -> Unit
) : RecyclerView.Adapter<NoteAdapter_127.NoteViewHolder_127>() {

    class NoteViewHolder_127(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_127)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_127)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content_snippet_127)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit_127)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete_127)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder_127 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_127, parent, false)
        return NoteViewHolder_127(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder_127, position: Int) {
        val note = noteList[position]
        holder.tvTitle.text = note.judul
        holder.tvDate.text = note.tanggal
        holder.tvContent.text = note.isi

        holder.btnEdit.setOnClickListener {
            onEditClick(note)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(note)
        }
    }

    override fun getItemCount(): Int = noteList.size

    fun updateNotes_127(newNotes: List<NoteEntity_127>) {
        this.noteList = newNotes
        notifyDataSetChanged()
    }
}
