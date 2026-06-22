package com.example.oscarapi

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oscarapi.API.RetrofitHelper
import com.example.oscarapi.dtos.Diretor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VotarDiretorActivity : AppCompatActivity() {

    private lateinit var rgDiretores: RadioGroup
    private lateinit var btnConfirmarLocal: Button
    private var diretores: List<Diretor> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votar_diretor)

        rgDiretores = findViewById(R.id.rgDiretores)
        btnConfirmarLocal = findViewById(R.id.btnConfirmarLocal)

        carregarDiretores()

        btnConfirmarLocal.setOnClickListener {
            val selectedId = rgDiretores.checkedRadioButtonId
            if (selectedId != -1) {
                val radioButton = findViewById<RadioButton>(selectedId)
                val diretorSelecionado = diretores.find { it.nome == radioButton.text.toString() }
                
                if (diretorSelecionado != null) {
                    SessaoVoto.diretorId = diretorSelecionado.id
                    SessaoVoto.diretorNome = diretorSelecionado.nome
                    Toast.makeText(this, "Diretor selecionado: ${diretorSelecionado.nome}", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Selecione um diretor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun carregarDiretores() {
        RetrofitHelper.api.getDiretores().enqueue(object : Callback<List<Diretor>> {
            override fun onResponse(call: Call<List<Diretor>>, response: Response<List<Diretor>>) {
                if (response.isSuccessful) {
                    diretores = response.body() ?: emptyList()
                    popularRadioGroup(diretores)
                } else {
                    Toast.makeText(this@VotarDiretorActivity, "Erro ao carregar diretores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Diretor>>, t: Throwable) {
                Toast.makeText(this@VotarDiretorActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun popularRadioGroup(diretores: List<Diretor>) {
        rgDiretores.removeAllViews()
        val textColor = getColor(R.color.text_light)
        
        diretores.forEach { diretor ->
            val rb = RadioButton(this)
            rb.text = diretor.nome
            rb.id = android.view.View.generateViewId()
            rb.setTextColor(textColor)
            rb.buttonTintList = android.content.res.ColorStateList.valueOf(getColor(R.color.gold))
            rb.setPadding(16, 32, 16, 32)
            
            // Verifica se já estava selecionado
            if (SessaoVoto.diretorId == diretor.id) {
                rb.isChecked = true
            }
            
            rgDiretores.addView(rb)
        }
    }
}
