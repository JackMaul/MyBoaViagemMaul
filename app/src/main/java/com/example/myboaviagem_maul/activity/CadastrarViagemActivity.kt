package com.example.myboaviagem_maul.activity

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myboaviagem_maul.R
import com.example.myboaviagem_maul.controller.ViagemController
import com.example.myboaviagem_maul.model.Viagem
import com.example.myboaviagem_maul.util.DateUtil
import com.example.myboaviagem_maul.util.Mascaras
import com.google.android.material.textfield.TextInputEditText


class CadastrarViagemActivity : AppCompatActivity() {

    private lateinit var editTextDestino : TextInputEditText
    private lateinit var rdTipoViagem : RadioGroup
    private lateinit var radioButtonLazer : RadioButton
    private lateinit var radioButtonNegocio : RadioButton
    private lateinit var editTextDataChegada : EditText
    private lateinit var editTextTextPartida : EditText
    private lateinit var textInputOrcamento : TextInputEditText
    private lateinit var editTextQuantidadePessoas : EditText
    private lateinit var buttonFecharViagem : Button
    private lateinit var buttonAddViagem : Button

    private var viagemController : ViagemController = ViagemController()
    private  var tipoDeViagem : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_viagem)

        editTextDestino = findViewById(R.id.editTextDestino)
        rdTipoViagem = findViewById(R.id.rdTipoViagem)
        radioButtonLazer = findViewById(R.id.radioButtonLazer)
        radioButtonNegocio = findViewById(R.id.radioButtonNegocio)
        editTextDataChegada = findViewById(R.id.editTextDataChegada)
        editTextTextPartida = findViewById(R.id.editTextTextPartida)
        textInputOrcamento = findViewById(R.id.textInputOrcamento)
        editTextQuantidadePessoas = findViewById(R.id.editTextQuantidadePessoas)
        buttonFecharViagem = findViewById(R.id.buttonFecharViagem)
        buttonAddViagem = findViewById(R.id.buttonAddViagem)

        //Mascara...

        Mascaras.adicionaMascaraData(editTextTextPartida);
        Mascaras.adicionaMascaraData(editTextDataChegada)
        editTextDataChegada.setText(DateUtil.dataAtual())
        addViagem()
        eventoRadioGroup()
        fecharActivityViagem()

    }

    fun addViagem(){

        buttonAddViagem.setOnClickListener {
            val destino = editTextDestino.text.toString() ?: ""
            val dataChegada = editTextDataChegada.text.toString() ?: ""
            val dataPartida = editTextTextPartida.text.toString() ?: ""
            val orcamento = textInputOrcamento.text.toString() ?: ""
            val quantidadePessoa: Int = editTextQuantidadePessoas.text.toString().toInt() ?: 0

            val viagem  = Viagem("1", destino, null , tipoDeViagem,
                dataChegada, dataPartida, orcamento.toDouble(), quantidadePessoa)
            if (validaCampos()){
                viagemController.salvar(viagem)
                finish()
            }
        }
    }

    fun fecharActivityViagem(){
        buttonFecharViagem.setOnClickListener {
            finish()
        }
    }

    private fun validaCampos(): Boolean {
        when {
            editTextDestino.text.toString().isEmpty() -> {
                editTextDestino.error = "campoObrigatorio"
                return false
            }
            tipoClienteEhInvalido() -> {
                Toast.makeText(this, "Informe o tipo da Viagem!!", Toast.LENGTH_LONG).show();
                return false
            }
            editTextDataChegada.text.toString().isEmpty() -> {
                editTextDataChegada.error = "campoObrigatorio"
                return false
            }
            textInputOrcamento.text.toString().isEmpty() -> {
                textInputOrcamento.error = "campoObrigatorio"
                return false
            }
            editTextQuantidadePessoas.text.toString().isEmpty() -> {
                editTextQuantidadePessoas.error = "campoObrigatorio"
                return false
            }
            else -> return true
        }
    }

    private fun viagemEhLazer(): Boolean {
        return radioButtonLazer.isChecked
    }

    private fun viagemEhNegocios(): Boolean {
        return radioButtonNegocio.isChecked
    }

    private fun verificaTipoViagem() {
        if (viagemEhLazer()) {
            this.tipoDeViagem = radioButtonLazer.text.toString()
        }
        else if (viagemEhNegocios()) {
            this.tipoDeViagem = radioButtonNegocio.text.toString()
        }
        else {
            this.tipoDeViagem = "Não Informado!"
        }
    }

    private fun tipoClienteEhInvalido(): Boolean {
        verificaTipoViagem()
        return this.tipoDeViagem == null || this.tipoDeViagem == "Não Informado!"
                || this.tipoDeViagem == ""
    }

    fun eventoRadioGroup() {
        rdTipoViagem.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            verificaTipoViagem()
        }

    }


}