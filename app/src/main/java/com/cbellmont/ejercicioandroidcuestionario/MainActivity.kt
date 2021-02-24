package com.cbellmont.ejercicioandroidcuestionario

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbellmont.ejercicioandroidcuestionario.databinding.ActivityMainBinding
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
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        createRecyclerView()
        setPreguntas()
        binding.bCompatibilidad.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Medidor de Compatibilidad")

            builder.setMessage("Los resultados son: ${model.calcularCompatibilidad()}. Que vas ?")


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
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setPreguntas(){
        lifecycleScope.launch {
            for (i in 0 until model.preguntasSize()) {
                val pregunta = loadPregunta(i)
                setPreguntaEnAdapter(pregunta)
            }
            hideProgressBarAndShowButton()
        }
    }

    private suspend fun loadPregunta(position : Int) : Pregunta {
        return model.getPregunta(position)

    }

    private suspend fun setPreguntaEnAdapter(pregunta : Pregunta) {
        adapter.addPreguntaToList(pregunta)
    }

    fun hideProgressBarAndShowButton(){
        binding.pbLoading.visibility = View.GONE
        binding.bCompatibilidad.visibility = View.VISIBLE
    }

    override fun onAllRadioChecked() {
        binding.bCompatibilidad.isEnabled = true
    }



}