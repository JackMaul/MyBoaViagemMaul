package com.example.myboaviagem_maul.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myboaviagem_maul.R
import com.example.myboaviagem_maul.adapter.AdapterViagens
import com.example.myboaviagem_maul.controller.ViagemController
import com.example.myboaviagem_maul.model.Viagem
import com.example.myboaviagem_maul.util.RecyclerItemClickListener


class ListaDeViagemActivity : AppCompatActivity() {

    private lateinit var recyclerViewViagens: RecyclerView
    private lateinit var adapter: AdapterViagens
    private var viagemController: ViagemController = ViagemController()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consulta_viagem)

        initComponentes()
        configuraAdapter(viagemController.getViagens() as List<Viagem>)
        eventoClickRecyclerView()
    }

    private fun initComponentes() {
        recyclerViewViagens = findViewById(R.id.recyclerViewViagens)
    }

    private fun configuraAdapter(list: List<Viagem>) {
        adapter =
            AdapterViagens(list as MutableList<Viagem>)
        configuraRecyclerViewVariaveis(adapter)
    }

    private fun configuraRecyclerViewVariaveis(adapter: AdapterViagens) {
        val layout = LinearLayoutManager(this)
        this.adapter = adapter
        recyclerViewViagens.adapter = adapter
        recyclerViewViagens.layoutManager = layout
        recyclerViewViagens.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        recyclerViewViagens.setHasFixedSize(true)
    }

    private fun eventoClickRecyclerView() {
        recyclerViewViagens.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                recyclerViewViagens,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {
                        val builder = AlertDialog.Builder(this@ListaDeViagemActivity)
                        builder.setTitle("Excluir Viagem")
                            .setMessage("Deseja realmente deletar viagem??")
                            .setPositiveButton("Sim") { dialog, which ->

                                val list = viagemController.getViagens()
                                val viagem = list!!.get(position)
                                list.remove(viagem)
                                finish()
                            }
                            .setNegativeButton("Não") { dialog, which ->

                            }
                            .setCancelable(false)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    }

                    override fun onItemClick(view: View?, position: Int) {
                        val viagem = viagemController.getViagens()?.get(position)
                        // viagem?.gastos = mutableListOf()
                        startActivity(
                            Intent(this@ListaDeViagemActivity, ListaGastosActivity::class.java)
                                .putExtra("viagem", viagem)
                        )
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