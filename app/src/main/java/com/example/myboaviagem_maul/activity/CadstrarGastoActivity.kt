package com.example.myboaviagem_maul.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myboaviagem_maul.R
import com.example.myboaviagem_maul.controller.ViagemController
import com.example.myboaviagem_maul.model.Gasto
import com.example.myboaviagem_maul.model.Viagem
import com.example.myboaviagem_maul.util.DateUtil
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.novo_gasto.*


class CadastraGastoActivity : AppCompatActivity() {

    //private lateinit var autoCompleteTipoGasto : AutoCompleteTextView
    private lateinit var botaoCancelarGasto: Button
    private lateinit var botaoAddGasto: Button
    private lateinit var botaoExcluir: Button
    private lateinit var spinergasto: Spinner
    private lateinit var spinerviagem: Spinner
    private lateinit var campoValor: TextInputEditText
    private lateinit var campoData: TextInputEditText
    private lateinit var campoDescricao: TextInputEditText
    private lateinit var campoLocal: TextInputEditText
    private var viagemController: ViagemController = ViagemController()

    private var gasto: Gasto = Gasto()
    private var isNewVariavel = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.novo_gasto)

        recuperaDadosIntentVariavel()
        initComponentes()
        verificaSeVariavelEhEditadaOuNova()
        preencheSpinnerTipoGasto()
        preencheSpinnerViagem()
        eventoClickAdicionarGasto()
        eventoClickBotaoCancelarGasto()
        eventoClickExlcuirGasto()
    }

    fun recuperaDadosIntentVariavel() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            this.gasto = bundle.getSerializable("gasto") as Gasto
        }
    }

    private fun verificaSeVariavelEhEditadaOuNova() {
        campoDescricao.setText(gasto.descicao)

        if (!gasto.descicao.isEmpty()) {
            campoData.setText(gasto.data)
            campoValor.setText(gasto.valor.toString())
            campoDescricao.setText(gasto.descicao)
            campoLocal.setText(gasto.local)
            preencheSpinnerTipoGasto()
            preencheSpinnerViagem()
            botaoExcluir.visibility = View.VISIBLE
            botaoCancelarGasto.visibility = View.VISIBLE
            /**EDITAR*/
            botaoAddGasto.text = getString(R.string.editar)
        } else {
            botaoExcluir.visibility = View.INVISIBLE
            botaoCancelarGasto.visibility = View.VISIBLE
            isNewVariavel = true
        }

    }

    fun preencheSpinnerTipoGasto() {
        val tipoDeGastos = arrayOf("Alimentação", "Transporte", "Hospedagem")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                R.layout.menu_pop, tipoDeGastos
            )
        spinergasto.setAdapter(adapter)
    }

    fun preencheSpinnerViagem() {
        val viagens: List<Viagem> = viagemController.getViagens() as List<Viagem>
        val adapter: ArrayAdapter<Viagem> =
            ArrayAdapter(
                this,
                R.layout.menu_pop, viagens
            )
        spinerviagem.adapter = adapter
    }

    fun initComponentes() {
        botaoCancelarGasto = findViewById(R.id.ButtonCancelar)
        spinergasto = findViewById(R.id.SpinnerGasto)
        spinerviagem = findViewById(R.id.SpinnerViagem)
        botaoAddGasto = findViewById(R.id.gastobutton)
        botaoExcluir = findViewById(R.id.buttonExcluirGasto)
        campoValor = findViewById(R.id.textoValor)
        campoData = findViewById(R.id.textoData)
        campoDescricao = findViewById(R.id.textoDescricao)
        campoLocal = findViewById(R.id.textoLocal)

        campoData.setText(DateUtil.dataAtual())

    }

    fun eventoClickBotaoCancelarGasto() {
        botaoCancelarGasto.setOnClickListener {
            finish()
        }
    }

    fun eventoClickExlcuirGasto() {
        botaoExcluir.setOnClickListener {
            val list = viagemController.getViagens()
            list!!.forEach { viagem ->
                viagem!!.gastos!!.forEach { g ->
                    if (g.descicao.equals(gasto.descicao) && g.valor.equals(gasto.valor)) {
                        viagem.gastos!!.remove(gasto)
                    }
                }
            }
            startActivity(Intent(this@CadastraGastoActivity, PrincipalActivity::class.java))
            finish()
        }
    }

    fun eventoClickAdicionarGasto() {
        botaoAddGasto.setOnClickListener {
            var valor = textoValor.text.toString().toDouble() ?: null
            var data = textoData.text.toString() ?: ""
            var descricao = textoDescricao.text.toString()  ?: ""
            var local = textoLocal.text.toString()  ?: ""
            var tipoGasto = spinergasto.selectedItemId.toString() ?: ""
            var viagem = spinerviagem.selectedItem.toString()   ?: ""

            val g = Gasto("dsadsa", valor!!, data, descricao, local, tipoGasto)

            val list = viagemController.getViagens()
            list!!.forEach { v ->
                if (v!!.destino.equals(viagem)) {
                    if (validaCamposGasto()) {
                        if (isNewVariavel) {
                            if (v!!.gastos == null) {
                                v.gastos = mutableListOf()
                            }
                            v!!.adicionaGasto(g)
                            finish()
                        } else {
                            list.forEach { v ->
                                if (v!!.destino.equals(viagem)) {
                                    if (validaCamposGasto()) {
                                        v.gastos!!.forEach { g ->
                                            if (g.descicao == gasto.descicao) {
                                                g.tipoGasto = campoData.text.toString()
                                                g.data = campoData.text.toString()
                                                g.valor = campoValor.text.toString().toDouble()
                                                g.descicao = campoDescricao.text.toString()
                                                g.idGasto = gasto.idGasto
                                                g.local = campoLocal.text.toString()
                                            }
                                        }
                                    }
                                }
                            }
                            startActivity(
                                Intent(
                                    this@CadastraGastoActivity,
                                    PrincipalActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }
            }
        }
    }

    fun validaCamposGasto(): Boolean {
        if (campoValor.text.toString().isEmpty()) {
            campoValor.error = "Campo Obrigatorio"
            return false
        }

        if (campoData.text.toString().isEmpty()) {
            campoData.error = "Campo Obrigatorio"
            return false
        }

        if (campoDescricao.text.toString().isEmpty()) {
            campoDescricao.error = "Campo Obrigatorio"
            return false
        }

        if (campoLocal.text.toString().isEmpty()) {
            campoLocal.error = "Campo Obrigatorio"
            return false
        }

        if (spinergasto.equals("")) {
            Toast.makeText(this, "Informe o tipo do gasto!", Toast.LENGTH_LONG).show()
            return false
        }

        if (spinerviagem.equals("")) {
            Toast.makeText(this, "Informe a viagem!", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }


}