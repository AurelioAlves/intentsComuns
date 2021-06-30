package com.example.intentscomuns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MostrarContatoActivity : AppCompatActivity() {

    private lateinit var cName: TextView
    private lateinit var cPhone: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_contato)

        cName = findViewById(R.id.contato_nome_textView)
        cPhone = findViewById(R.id.contato_telefone_textView)

        val name = intent.getStringExtra("cName")
        val phone = intent.getStringExtra("cPhone")

        cName.text = name
        cPhone.text = phone
    }
}