package com.example.oscarapi

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oscarapi.dtos.ApiResponse
import com.example.oscarapi.dtos.VotoRequest
import com.example.oscarapi.API.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmarVotoActivity : AppCompatActivity() {

    private lateinit var tvFilme: TextView
    private lateinit var tvDiretor: TextView
    private lateinit var etToken: EditText
    private lateinit var btnConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_voto)

        tvFilme = findViewById(R.id.tvFilme)
        tvDiretor = findViewById(R.id.tvDiretor)
        etToken = findViewById(R.id.etToken)
        btnConfirmar = findViewById(R.id.btnConfirmar)

        atualizarTela()
        btnConfirmar.setOnClickListener { confirmarVoto() }
    }

    private fun atualizarTela() {
        tvFilme.text = SessaoVoto.filmeNome ?: "não selecionado"
        tvDiretor.text = SessaoVoto.diretorNome ?: "não selecionado"

        if (SessaoVoto.confirmado) {
            bloquearTela()
        }
    }

    private fun confirmarVoto() {
        val tokenDigitado = etToken.text.toString().trim()

        if (SessaoVoto.filmeId == null || SessaoVoto.diretorId == null) {
            Toast.makeText(this, "Vote em filme e diretor antes de confirmar.", Toast.LENGTH_SHORT).show()
            return
        }
        if (tokenDigitado.isEmpty()) {
            etToken.error = "Informe o token"
            return
        }
        val tokenInt = tokenDigitado.toIntOrNull()
        if (tokenInt == null) {
            etToken.error = "Token deve ser numérico"
            return
        }
        if (SessaoVoto.usuarioId == -1L) {
            Toast.makeText(this, "Sessão inválida. Faça login novamente.", Toast.LENGTH_SHORT).show()
            return
        }

        btnConfirmar.isEnabled = false

        val request = VotoRequest(
            usuarioId = SessaoVoto.usuarioId,
            filmeId = SessaoVoto.filmeId!!,
            diretorId = SessaoVoto.diretorId!!,
            token = tokenInt
        )

        RetrofitHelper.api.confirmarVoto(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                btnConfirmar.isEnabled = true

                if (response.isSuccessful) {
                    SessaoVoto.confirmado = true
                    mostrarDialogCustomizado(
                        "voto confirmado!",
                        response.body()?.mensagem ?: "seus votos foram registrados com sucesso"
                    ) { bloquearTela() }
                } else {
                    val mensagemErro = extrairMensagem(response.errorBody()?.string())
                    mostrarDialogCustomizado("erro ao confirmar", mensagemErro, null)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                btnConfirmar.isEnabled = true
                mostrarDialogCustomizado("erro de conexão", "não foi possível conectar ao servidor", null)
            }
        })
    }

    private fun mostrarDialogCustomizado(titulo: String, mensagem: String, onOk: (() -> Unit)?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_voto_confirmado)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#CC000000")))
        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tvDialogTitulo).text = titulo
        dialog.findViewById<TextView>(R.id.tvDialogMensagem).text = mensagem

        dialog.findViewById<Button>(R.id.btnDialogOk).setOnClickListener {
            dialog.dismiss()
            onOk?.invoke()
        }

        dialog.show()
    }

    private fun extrairMensagem(json: String?): String {
        if (json == null) return "erro desconhecido"
        return try {
            org.json.JSONObject(json).optString("mensagem", "erro desconhecido")
        } catch (e: Exception) {
            "erro desconhecido"
        }
    }

    private fun bloquearTela() {
        etToken.isEnabled = false
        btnConfirmar.isEnabled = false
        btnConfirmar.text = "voto já confirmado"
    }
}