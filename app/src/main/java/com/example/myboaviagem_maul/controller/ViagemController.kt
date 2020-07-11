package com.example.myboaviagem_maul.controller

import com.example.myboaviagem_maul.dao.ViagemDAO
import com.example.myboaviagem_maul.model.Viagem


class ViagemController {

    companion object {
        private var viagemDAO : ViagemDAO = ViagemDAO()
    }


    fun salvar(viagem: Viagem?) {
        viagemDAO.salvar(viagem)
    }


    fun getViagens(): ArrayList<Viagem?>? {
        return viagemDAO.getListViagem() as ArrayList<Viagem?>?
    }

}