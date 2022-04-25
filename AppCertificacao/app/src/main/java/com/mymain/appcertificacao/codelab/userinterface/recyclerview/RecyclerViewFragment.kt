package com.mymain.appcertificacao.codelab.userinterface.recyclerview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.databinding.FragmentRecyclerViewBinding


class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {
   private lateinit var binding: FragmentRecyclerViewBinding

   // 1 - Defina seu modelo
   private var words = mutableListOf("Victor", "Itana", "Ricardo", "Julia", "Pedro", "Alfred")

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding = FragmentRecyclerViewBinding.bind(view)
      // 4 - Atribua o adapter ao seu recyclerView
      binding.wordRecycler.adapter = WordListAdapter(requireContext(), words)
      binding.addWordButton.setOnClickListener {
         words.add("+ New word" + words.size)
         (binding.wordRecycler.adapter as WordListAdapter).notifyItemChanged(words.size)
         binding.wordRecycler.smoothScrollToPosition(words.size)
      }
   }

   // 2 - Crie o seu viewAdpater
   class WordListAdapter(context: Context, val words: MutableList<String>) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>(){
      private val inflater: LayoutInflater = LayoutInflater.from(context)

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
         val item = inflater.inflate(R.layout.wordlist_item, parent, false)
         return WordViewHolder(item, this)
      }

      // Aqui é onde acontece a "adaptação" do modelo para o layout .xml
      override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
         holder.wordItemView.text = words[position]
      }

      override fun getItemCount(): Int {
         return words.size
      }

      // 3 - Defina um viewHolder e implement o onclick de cada item
      inner class WordViewHolder(item: View, private val adapter: WordListAdapter) : RecyclerView.ViewHolder(item), View.OnClickListener {

         val wordItemView: TextView = item.findViewById(R.id.word)

         init {
            wordItemView.setOnClickListener(this)
         }

         override fun onClick(p0: View?) {
            words[layoutPosition] = "Clicked! " + words[layoutPosition]
            adapter.notifyDataSetChanged()
         }

      }

   }
}