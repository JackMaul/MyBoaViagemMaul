package com.example.myboaviagem_maul.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myboaviagem_maul.R


class PrincipalActivity : AppCompatActivity() {

    private lateinit var imgNewTrip : ImageView
    private lateinit var imgNewGasto : ImageView
    private lateinit var imgListTrip : ImageView
    private lateinit var imgConfig : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_pricipal)

        initComponents()
        clickEventNewGasto()
        clickEventNewTrip()
        clickEventListTrips()
    }

    private fun initComponents(){
        imgNewTrip = findViewById(R.id.imageViewViagem)
        imgNewGasto = findViewById(R.id.novoGastoImage)
        imgListTrip = findViewById(R.id.listTrip)
        imgConfig = findViewById(R.id.imageViewConfig)
    }

    private fun clickEventNewGasto(){
        imgNewGasto.setOnClickListener{
            startActivity(Intent(this, CadastraGastoActivity::class.java))
        }
    }

    private fun clickEventNewTrip(){
        imgNewTrip.setOnClickListener{
            startActivity(Intent(this, CadastrarViagemActivity::class.java))
        }
    }

    private fun clickEventListTrips(){
        imgListTrip.setOnClickListener{
            startActivity(Intent(this, ListaDeViagemActivity::class.java))
        }
    }

    private fun clickEventConfiguration(){

    }
}
