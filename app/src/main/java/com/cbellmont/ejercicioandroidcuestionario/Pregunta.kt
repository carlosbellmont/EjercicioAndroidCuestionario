package com.cbellmont.ejercicioandroidcuestionario

import java.lang.Exception

data class Pregunta(var enunciado: String, var listaRespuestas: List<Respuesta>, var eleccionUsuario: Int?) {

    fun tieneRespuesta() : Boolean {
        return eleccionUsuario != null
    }

    fun respuestaCorrecta() : Boolean{
        return try {
            return eleccionUsuario?.let { listaRespuestas[it].esCorrecta } ?: run {false}
    } catch (exception : Exception) {
            false
        }
    }
}

data class Respuesta (var texto : String, var esCorrecta : Boolean)