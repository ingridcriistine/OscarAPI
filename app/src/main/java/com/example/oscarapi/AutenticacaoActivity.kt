package com.example.oscarapi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oscarapi.API.RetrofitHelper
import com.example.oscarapi.dtos.LoginRequest
import com.example.oscarapi.dtos.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AutenticacaoActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etSenha: EditText
    private lateinit var btnEntrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autenticacao)

        etUsuario = findViewById(R.id.etUsuario)
        etSenha = findViewById(R.id.etSenha)
        btnEntrar = findViewById(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val login = etUsuario.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (login.isEmpty()) {
                etUsuario.error = "Campo obrigatório"
                return@setOnClickListener
            }
            if (senha.isEmpty()) {
                etSenha.error = "Campo obrigatório"
                return@setOnClickListener
            }

            realizarLogin(login, senha)
        }
    }

    private fun realizarLogin(login: String, senha: String) {
        btnEntrar.isEnabled = false
        val request = LoginRequest(login, senha)

        RetrofitHelper.api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                btnEntrar.isEnabled = true
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        SessaoVoto.limpar()
                        SessaoVoto.usuarioId = loginResponse.usuarioId
                        SessaoVoto.usuarioNome = login
                        SessaoVoto.token = loginResponse.token
                        
                        startActivity(Intent(this@AutenticacaoActivity, BoasVindasActivity::class.java))
                        finish()
                    }
                } else {
                    val errorMsg = try {
                        val errorJson = response.errorBody()?.string()
                        org.json.JSONObject(errorJson).getString("mensagem")
                    } catch (e: Exception) {
                        "Login ou senha inválidos"
                    }
                    Toast.makeText(this@AutenticacaoActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                btnEntrar.isEnabled = true
                Toast.makeText(this@AutenticacaoActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
