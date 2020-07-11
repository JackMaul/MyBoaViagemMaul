package com.example.myboaviagem_maul.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myboaviagem_maul.R
import com.example.myboaviagem_maul.adapter.AdapterGastos
import com.example.myboaviagem_maul.controller.ViagemController
import com.example.myboaviagem_maul.model.Gasto
import com.example.myboaviagem_maul.model.Viagem
import com.example.myboaviagem_maul.util.RecyclerItemClickListener


class ListaGastosActivity : AppCompatActivity() {

    private lateinit var viagem: Viagem
    private lateinit var recyclerViewGastos: RecyclerView
    private lateinit var adapter: AdapterGastos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_gastos)
        recyclerViewGastos = findViewById(R.id.recyclerViewGastos)
        recuperaDadosIntentVariavel()

        configuraAdapter(viagem.gastos as List<Gasto>)
        eventoClickRecyclerView()
    }

    fun recuperaDadosIntentVariavel() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            this.viagem = bundle.getSerializable("viagem") as Viagem
            if (viagem.gastos == null){
                viagem.gastos = mutableListOf()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        configuraAdapter(viagem.gastos as List<Gasto>)

    }

    override fun onRestart() {
        super.onRestart()
        configuraAdapter(viagem.gastos as List<Gasto>)
    }

    private fun configuraAdapter(list: List<Gasto>) {
        adapter = AdapterGastos(list as MutableList<Gasto>)
        configuraRecyclerViewGastos(adapter)
    }

    private fun configuraRecyclerViewGastos(adapter: AdapterGastos) {
        val layout = LinearLayoutManager(this)
        this.adapter = adapter
        recyclerViewGastos.adapter = adapter
        recyclerViewGastos.layoutManager = layout
        recyclerViewGastos.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        recyclerViewGastos.setHasFixedSize(true)
        this.adapter.notifyDataSetChanged()
    }

    private fun eventoClickRecyclerView() {

        recyclerViewGastos.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                recyclerViewGastos,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View?, position: Int) {
                        val controller = ViagemController()
                        val gasto = viagem.gastos!!.get(position)
                        //val gasto  = controller.getViagens()!!.get(position)!!.gastos!!.get(position)
                        startActivity(
                            Intent(this@ListaGastosActivity, CadastraGastoActivity::class.java)
                            .putExtra("gasto", gasto))

                    }

                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                    }
                })
        )
    }
}