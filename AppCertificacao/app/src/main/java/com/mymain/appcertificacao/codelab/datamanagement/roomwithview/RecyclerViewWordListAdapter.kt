package com.mymain.appcertificacao.codelab.datamanagement.roomwithview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mymain.appcertificacao.R

// +-----------------------------------------------------------------------------------------------|
// | RECYCLE VIEW ADAPTER - VOCÊ FORNECE OS DADOS E DEFINE A APARÊNCIA DE CADA ITEM, E A BIBLIOTECA|
//                          RECYCLERvIEW, QUANDO NACESSÁRIO, CRIA OS ELEMENTOS DINAMICAMENTE.      |
// +-----------------------------------------------------------------------------------------------|

// ADAPTER - >>> 3° <<< COISA A SER DEFINIDA
class WordListAdapter: ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word)
    }
    // VIEW HOLDER - >>> 2° <<< COISA A SER DEFINIDA
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }
    // COMPARADOR >>> 1° <<< COISA A SER DEFINIDA
    class WordsComparator: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
}