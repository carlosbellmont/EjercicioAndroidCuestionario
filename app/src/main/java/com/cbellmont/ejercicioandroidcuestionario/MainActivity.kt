package com.cbellmont.ejercicioandroidcuestionario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface AllRadioChecked {
    fun onAllRadioChecked()
}

class MainActivity : AppCompatActivity(), AllRadioChecked {

    private var adapter : PreguntaAdapter = PreguntaAdapter(this)
    private lateinit var model :MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        createRecyclerView()
        setPreguntas()
        bCompatibilidad.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Medidor de Compatibilidad")

            builder.setMessage("Los resultados son: ${model.calcularCompatibilidad()}")


            builder.setPositiveButton("Cortar con la pareja") { dialog, which ->
                Toast.makeText(applicationContext, "Enhorabuena"
                    , Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Seguir adelante") { dialog, which ->
                Toast.makeText(applicationContext,
                    "Vaya!", Toast.LENGTH_SHORT).show()
            }


            builder.show()

        }
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setPreguntas(){
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until model.preguntasSize()) {
                val pregunta = loadPregunta(i)
                setPreguntaEnAdapter(pregunta)
            }
            hideProgressBarAndShowButton()
        }
    }

    private suspend fun loadPregunta(position : Int) : Pregunta {
        return withContext(Dispatchers.IO) {
            return@withContext model.getPregunta(position)
        }
    }

    private suspend fun setPreguntaEnAdapter(pregunta : Pregunta) {
        withContext(Dispatchers.Main) {
            adapter.addPreguntaToList(pregunta)
        }
    }

    fun hideProgressBarAndShowButton(){
        pbLoading.visibility = View.GONE
        bCompatibilidad.visibility = View.VISIBLE
    }

    override fun onAllRadioChecked() {
        bCompatibilidad.isEnabled = true
    }



}