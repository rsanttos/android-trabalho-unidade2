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

        var position = intent.getStringExtra("NOTA_POSITION")
        var titulo = intent.getStringExtra("NOTA_TITULO")
        var texto = intent.getStringExtra("NOTA_TEXTO")

        if(isUpdate(position, titulo)){

            tvTitulo.text = titulo

            edtTexto.setText(texto)

            btnCadastrar.setOnClickListener{
                var textoNovo = edtTexto.text.toString()

                var newIntent = Intent(this, MainActivity::class.java)

                newIntent.putExtra("VALOR_POSITION", position)
                newIntent.putExtra("VALOR_TITULO", titulo)
                newIntent.putExtra("VALOR_TEXTO", textoNovo)

                Log.v("atv2-v-position", position)
                Log.v("atv2-v-titulo", titulo)
                Log.v("atv2-v-texto", textoNovo)

                setResult(1, newIntent)
                finish()
            }
        } else {
            tvTitulo.text = "Nova nota"

            btnCadastrar.setOnClickListener{
                DialogNota.show(supportFragmentManager, object : DialogNota.OnTextListener {
                    override fun onSetText(text: String) {

                    }
                })
            }
        }


        btnCancelar.setOnClickListener {
            finish()
        }
    }

    fun isUpdate(position: String, titulo: String): Boolean{
        return (position.isNullOrBlank() && titulo.isNullOrBlank())
    }


    fun openDialog(){
    }
}
