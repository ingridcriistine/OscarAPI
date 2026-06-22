package com.example.oscarapi

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BoasVindasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boas_vindas)

        val tvBoasVindas = findViewById<TextView>(R.id.tvBoasVindas)
        val tvToken = findViewById<TextView>(R.id.tvToken)
        val btnVotarFilme = findViewById<LinearLayout>(R.id.btnVotarFilme)
        val btnVotarDiretor = findViewById<LinearLayout>(R.id.btnVotarDiretor)
        val btnConfirmarVoto = findViewById<LinearLayout>(R.id.btnConfirmarVoto)
        val btnSair = findViewById<LinearLayout>(R.id.btnSair)

        tvBoasVindas.text = "bem-vinda, ${SessaoVoto.usuarioNome ?: "usuário"}"
        tvToken.text = SessaoVoto.token?.toString() ?: "--"

        btnVotarFilme.setOnClickListener {
            // Implementação futura ou placeholder
        }

        btnVotarDiretor.setOnClickListener {
            startActivity(Intent(this, VotarDiretorActivity::class.java))
        }

        btnConfirmarVoto.setOnClickListener {
            startActivity(Intent(this, ConfirmarVotoActivity::class.java))
        }

        btnSair.setOnClickListener {
            SessaoVoto.limpar()
            startActivity(Intent(this, AutenticacaoActivity::class.java))
            finish()
        }
    }
}
