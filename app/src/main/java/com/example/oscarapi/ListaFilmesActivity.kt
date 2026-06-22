package com.example.oscarapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oscarapi.API.RetrofitHelper
import kotlinx.coroutines.launch
import com.example.oscarapi.adapter.FilmeAdapter

class ListaFilmesActivity : AppCompatActivity() {

    private lateinit var recyclerFilmes: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: FilmeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_filmes)

        recyclerFilmes = findViewById(R.id.recyclerFilmes)
        progressBar = findViewById(R.id.progressBar)

        adapter = FilmeAdapter { filme ->
            val intent = Intent(this, DetalhesFilmeActivity::class.java).apply {
                putExtra(DetalhesFilmeActivity.EXTRA_FILME_ID, filme.id)
                putExtra(DetalhesFilmeActivity.EXTRA_FILME_NOME, filme.nome)
                putExtra(DetalhesFilmeActivity.EXTRA_FILME_GENERO, filme.genero)
                putExtra(DetalhesFilmeActivity.EXTRA_FILME_FOTO, filme.foto)
            }
            startActivity(intent)
        }

        recyclerFilmes.layoutManager = LinearLayoutManager(this)
        recyclerFilmes.adapter = adapter

        carregarFilmes()
    }

    private fun carregarFilmes() {
        progressBar.visibility = View.VISIBLE
        recyclerFilmes.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val filmes = RetrofitHelper.api.listarFilmes("http://200.236.3.97/filme.json")
                adapter.submitList(filmes)
                recyclerFilmes.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(
                    this@ListaFilmesActivity,
                    getString(R.string.erro_conexao),
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
