package com.example.myboaviagem_maul.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myboaviagem_maul.R
import com.example.myboaviagem_maul.model.Viagem
import com.example.myboaviagem_maul.util.Formatar


class AdapterViagens(private val viagens: MutableList<Viagem>) :
    RecyclerView.Adapter<AdapterViagens.MyViewHolder>() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    var onItemClick: ((Viagem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.viagens_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.viagens.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(viagens[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewDestino : TextView = itemView.findViewById(R.id.textDestinoViagem)
        var textViewDataChegada : TextView = itemView.findViewById(R.id.textDataChegada)
        var textViewDataPartida : TextView = itemView.findViewById(R.id.textDataPartida)
        var textViewGastos : TextView = itemView.findViewById(R.id.textGastos)
        var progressBarOrcamento : ProgressBar = itemView.findViewById(R.id.progressBarOrcamento)
        var textPorcetagem : TextView = itemView.findViewById(R.id.textPorcetagem)
        var icone : ImageView = itemView.findViewById(R.id.icone)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(viagens[adapterPosition])
            }
        }

        fun bind(viagem : Viagem) {
            textViewDestino.text = viagem.destino
            textViewDataChegada.text = viagem.dataChegada
            if (viagem.dataPartida == null){
                textViewDataPartida.text = "Não Definido!"
            }else {
                textViewDataPartida.text = viagem.dataPartida
            }
            if (viagem.tipoViagem.equals("Lazer")){
                icone.setImageResource(R.drawable.ic_baseline_local_airport_24)

            }else {
                icone.setImageResource(R.drawable.ic_baseline_work_24)
            }
            var total = viagem.calculaGastos()
            textViewGastos.text = "Total gastos R$: ${Formatar.formataPorcetagem(total)}"
            progressBarOrcamento.max = viagem.orcamento.toInt()
            progressBarOrcamento.progress = total.toInt()
            var porcentagem = viagem.calculaPorcetagemGasto()
            textPorcetagem.text = Formatar.formataPorcetagem(porcentagem) + "%"

            selecionaCorProgressBar(porcentagem, progressBarOrcamento)
        }

        private fun selecionaCorProgressBar(porcentagem : Double, progressBar: ProgressBar){
            when {
                porcentagem > 0 && porcentagem < 50 -> {
                    progressBar.progressDrawable.setColorFilter(
                        Color.rgb(0,128,0), android.graphics.PorterDuff.Mode.SRC_IN)
                }
                porcentagem >= 50 && porcentagem < 70 -> {
                    progressBar.progressDrawable.setColorFilter(
                        Color.rgb(173,255,47), android.graphics.PorterDuff.Mode.SRC_IN)
                }
                porcentagem > 70 && porcentagem < 90 -> {
                    progressBar.progressDrawable.setColorFilter(
                        Color.rgb(240,128,128), android.graphics.PorterDuff.Mode.SRC_IN)
                }

                porcentagem >= 90 -> {
                    progressBar.progressDrawable.setColorFilter(
                        Color.rgb(240,0,0), android.graphics.PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }
}