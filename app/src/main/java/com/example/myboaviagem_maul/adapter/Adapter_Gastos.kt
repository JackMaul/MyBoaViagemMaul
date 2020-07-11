package com.example.myboaviagem_maul.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myboaviagem_maul.R
import com.example.myboaviagem_maul.model.Gasto
import com.example.myboaviagem_maul.util.Formatar


class AdapterGastos(private val gastos: MutableList<Gasto>) :
    RecyclerView.Adapter<AdapterGastos.MyViewHolder>() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    var onItemClick: ((Gasto) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.gastos_detalhados, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.gastos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(gastos[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewDestino : TextView = itemView.findViewById(R.id.textDescricaoGasto)
        var textViewDataChegada : TextView = itemView.findViewById(R.id.textValorGasto)


        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(gastos[adapterPosition])
            }
        }

        fun bind(gasto : Gasto) {
            textViewDestino.text = gasto.descicao
            val gastoConvetido = Formatar.formataPorcetagem(gasto.valor)
            textViewDataChegada.text =  "R$: $gastoConvetido"
        }
    }
}