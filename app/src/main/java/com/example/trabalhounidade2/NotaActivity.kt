package com.example.trabalhounidade2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_nota.*

class NotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        var positionNota = intent.getStringExtra("NOTA_POSITION")
        var tituloNota = intent.getStringExtra("NOTA_TITULO")
        var textoNota = intent.getStringExtra("NOTA_TEXTO")

        if(!(positionNota.isNullOrBlank() && tituloNota.isNullOrBlank())){

            tvTitulo.text = tituloNota
            edtTextoNota.setText(textoNota)

            btnCadastrar.setOnClickListener{
                var textoNovo = edtTextoNota.text.toString()

                var newIntent = Intent(this, MainActivity::class.java)

                newIntent.putExtra("NOTA_POSITION", positionNota)
                newIntent.putExtra("NOTA_TITULO", tituloNota)
                newIntent.putExtra("NOTA_TEXTO", textoNovo)

                Log.v("atv2-v-position", positionNota)
                Log.v("atv2-v-titulo", tituloNota)
                Log.v("atv2-v-texto", textoNovo)

                setResult(1, newIntent)
                finish()
            }

        } else {
            tvTitulo.text = "Nova nota"

            btnCadastrar.setOnClickListener{
                var newIntent = Intent(this, MainActivity::class.java)
                DialogNota.show(supportFragmentManager, object : DialogNota.OnTextListener {
                    override fun onSetText(titulo: String) {
                        var newTexto = edtTextoNota.text
                        newIntent.putExtra("NOTA_TITULO", titulo)
                        newIntent.putExtra("NOTA_TEXTO", newTexto.toString())
                        setResult(1, newIntent)
                        finish()
                    }
                })
            }
        }


        btnCancelar.setOnClickListener {
            finish()
        }
    }
}
