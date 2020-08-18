package com.cbellmont.ejercicioandroidcuestionario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PreguntaAdapter(var listener : AllRadioChecked) : RecyclerView.Adapter<PreguntaAdapter.PreguntasViewHolder>() {


    class PreguntasViewHolder(root: View, var tvPregunta: TextView, var rbRespuesta1: RadioButton, var rbRespuesta2 : RadioButton) : RecyclerView.ViewHolder(root)

    private var preguntas = mutableListOf<Pregunta>()

    fun addPreguntaToList(pregunta: Pregunta) {
        this.preguntas.add(pregunta)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pregunta, parent, false)
        val tvPregunta = view.findViewById<TextView>(R.id.tvPregunta)
        val rbRespuesta1 = view.findViewById<RadioButton>(R.id.rbRespuesta1)
        val rbRespuesta2 = view.findViewById<RadioButton>(R.id.rbRespuesta2)


        return PreguntasViewHolder(view, tvPregunta, rbRespuesta1, rbRespuesta2)
    }

    override fun getItemCount(): Int {
        return preguntas.size
    }

    override fun onBindViewHolder(holder: PreguntasViewHolder, position: Int) {
        holder.tvPregunta.text = preguntas[position].enunciado
        holder.rbRespuesta1.text = preguntas[position].listaRespuestas[0].texto
        holder.rbRespuesta2.text = preguntas[position].listaRespuestas[1].texto

        holder.rbRespuesta1.setOnClickListener {
            preguntas[position].eleccionUsuario = 0
            comprobarOtrasRespuestas()
        }
        holder.rbRespuesta2.setOnClickListener {
            preguntas[position].eleccionUsuario = 1
            comprobarOtrasRespuestas()
        }
    }

    fun comprobarOtrasRespuestas() {
        var todaPreguntaTieneRespuesta = true
        preguntas.forEach {
            if (!it.tieneRespuesta()) {
                todaPreguntaTieneRespuesta = false
            }
        }

        if (todaPreguntaTieneRespuesta) {
            listener.onAllRadioChecked()
        }
    }
}
