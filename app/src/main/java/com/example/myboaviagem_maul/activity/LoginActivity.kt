package com.example.myboaviagem_maul.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myboaviagem_maul.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    private val email = "teste"
    private val senha = "1"

    private lateinit var textInputLayoutLogin: TextInputLayout
    private lateinit var textInputEditTextLogin: TextInputEditText
    private lateinit var textInputLayoutSenha: TextInputLayout
    private lateinit var textInputEditTextSenha: TextInputEditText
    private lateinit var buttonEntrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInputEditTextLogin = findViewById(R.id.textInputEditTextLogin)
        textInputEditTextSenha = findViewById(R.id.textInputEditTextSenha)
        buttonEntrar = findViewById(R.id.buttonentrar)

        EventoLogar()
    }

    fun EventoLogar() {
        buttonEntrar.setOnClickListener {
            var logindigitado = textInputEditTextLogin.text
            var senhadigitada = textInputEditTextSenha.text

            if (logindigitado!!.equals(email) && senhadigitada!!.equals(senha)) {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
            }
            else
            {
                var intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)

            }
        }


    }


}


