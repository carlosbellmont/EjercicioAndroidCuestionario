package com.cbellmont.ejercicioandroidcuestionario

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay


class MainActivityViewModel  : ViewModel() {

    private val preguntas = mutableListOf<Pregunta>()

    init {
        cargarPreguntas()
    }

    suspend fun getPregunta(position: Int): Pregunta {
        delay(1000)
        return preguntas[position]
    }

    fun preguntasSize() : Int {
        return preguntas.size
    }

    fun calcularCompatibilidad() : String {
        var sumatorio = 0
        preguntas.forEach {
            if (it.respuestaCorrecta()){
                sumatorio++
            }
        }
        return when (sumatorio) {
            0-> "0 de 4, sois sinceros, teneis futuro"
            1-> "1 de 4, la cosa va bien"
            2-> "2 de 4, aceptable"
            3-> "3 de 4, demaiadas mentiras..."
            4-> "4 de 4, cortad ya, esa relacion es una mentira"
            else -> "Valor desconocido"
        }
    }

    private fun cargarPreguntas() {
        preguntas.addAll(
            mutableListOf(
                Pregunta(" ¿A qué lugares te gustaría viajar?", listOf(Respuesta("Contigo a cualquier lado", true), Respuesta("Sin ti a cualquier lado", false)), null),
                Pregunta("¿Qué tipo de música escuchas para relajarte?", listOf(Respuesta("Tu dulce voz", true), Respuesta("Cualquier cosa que no sean tus berridos me relajan", false)), null),
                Pregunta("¿Con cuál de mis familiares te llevas mejor?", listOf(Respuesta("Con tu madre", true), Respuesta("Imposible de elegir entre tanto horror", false)), null),
                Pregunta("¿Qué te gusta hacer cuando tienes tiempo libre?", listOf(Respuesta("Estar contigo", true), Respuesta("Preparar mi plan de fuga", false)), null)
            )
        )
    }
}

