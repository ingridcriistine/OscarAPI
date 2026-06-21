package com.example.oscarapi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetalhesFilmeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILME_ID = "filme_id"
        const val EXTRA_FILME_NOME = "filme_nome"
        const val EXTRA_FILME_GENERO = "filme_genero"
        const val EXTRA_FILME_FOTO = "filme_foto"
    }

    private lateinit var ivPosterDetalhe: ImageView
    private lateinit var tvNomeDetalhe: TextView
    private lateinit var tvGeneroDetalhe: TextView
    private lateinit var btnVotar: Button
    private lateinit var tvVotoRegistrado: TextView

    private var filmeId: String = ""
    private var filmeNome: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_filme)

        ivPosterDetalhe = findViewById(R.id.ivPosterDetalhe)
        tvNomeDetalhe = findViewById(R.id.tvNomeDetalhe)
        tvGeneroDetalhe = findViewById(R.id.tvGeneroDetalhe)
        btnVotar = findViewById(R.id.btnVotar)
        tvVotoRegistrado = findViewById(R.id.tvVotoRegistrado)

        filmeId = intent.getStringExtra(EXTRA_FILME_ID) ?: ""
        filmeNome = intent.getStringExtra(EXTRA_FILME_NOME) ?: ""
        val filmeGenero = intent.getStringExtra(EXTRA_FILME_GENERO) ?: ""
        val filmeFoto = intent.getStringExtra(EXTRA_FILME_FOTO) ?: ""

        tvNomeDetalhe.text = filmeNome
        tvGeneroDetalhe.text = filmeGenero

        Picasso.get()
            .load(filmeFoto)
            .placeholder(R.drawable.ic_film_placeholder)
            .error(R.drawable.ic_film_placeholder)
            .fit()
            .centerCrop()
            .into(ivPosterDetalhe)

        findViewById<ImageView>(R.id.ivVoltar).setOnClickListener { finish() }

        btnVotar.setOnClickListener {
            SessaoVoto.filmeId = filmeId
            SessaoVoto.filmeNome = filmeNome
            atualizarStatusVoto()
            Toast.makeText(this, getString(R.string.voto_registrado_localmente), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        atualizarStatusVoto()
    }

    private fun atualizarStatusVoto() {
        val esteFilmeVotado = SessaoVoto.filmeId == filmeId && filmeId.isNotEmpty()
        tvVotoRegistrado.visibility = if (esteFilmeVotado) View.VISIBLE else View.INVISIBLE
    }
}
