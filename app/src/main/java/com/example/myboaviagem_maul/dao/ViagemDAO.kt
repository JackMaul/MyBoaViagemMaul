package com.example.myboaviagem_maul.dao

import com.example.myboaviagem_maul.model.Viagem

class ViagemDAO {

    companion object {
        private var listaDeViagens: ArrayList<Viagem> = mutableListOf<Viagem>() as ArrayList<Viagem>
    }

    fun salvar(viagem: Viagem?) {
        listaDeViagens.add(viagem!!)

    }

    fun getListViagem(): List<Viagem?>? {
        return listaDeViagens
    }

}