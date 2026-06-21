package com.example.oscarapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oscarapi.R
import com.example.oscarapi.model.Filme
import com.squareup.picasso.Picasso

class FilmeAdapter(private val onItemClick: (Filme) -> Unit) :
    RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    private var filmes: List<Filme> = emptyList()

    fun submitList(novaLista: List<Filme>) {
        filmes = novaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filme, parent, false)
        return FilmeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.bind(filmes[position])
    }

    override fun getItemCount() = filmes.size

    inner class FilmeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
        private val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        private val tvGenero: TextView = itemView.findViewById(R.id.tvGenero)

        fun bind(filme: Filme) {
            tvNome.text = filme.nome
            tvGenero.text = filme.genero

            Picasso.get()
                .load(filme.foto)
                .placeholder(R.drawable.ic_film_placeholder)
                .error(R.drawable.ic_film_placeholder)
                .fit()
                .centerCrop()
                .into(ivPoster)

            itemView.setOnClickListener { onItemClick(filme) }
        }
    }
}
