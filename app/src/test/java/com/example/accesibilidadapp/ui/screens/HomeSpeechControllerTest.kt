package com.example.accesibilidadapp.ui.screens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

/**
 * Pruebas unitarias para HomeScreen.
 * Verifican el comportamiento del botón "Presionar y hablar".
 */
class HomeSpeechControllerTest {

    private lateinit var controller: HomeSpeechController

    @Before
    fun setUp() {
        controller = HomeSpeechController()
    }

    /**
     * Prueba 1: Al recibir un error, el mensaje de estado debe actualizarse correctamente y dejar de escuchar.
     */
    @Test
    fun `cuando ocurre ERROR_NO_MATCH el mensaje indica que no se entendio`() {
        // Simular que el reconocimiento estaba activo
        controller.onReadyForSpeech()

        // Simular el error de sin coincidencia
        controller.onSpeechError(HomeSpeechController.ERROR_NO_MATCH)

        assertEquals(
            "El mensaje debe indicar que no se entendió",
            "No se entendió. Intenta de nuevo.",
            controller.statusMessage
        )
        assertFalse(
            "isListening debe ser false tras el error",
            controller.isListening
        )
    }

    /**
     * Prueba 2: Al recibir resultados de voz, el texto se almacena y el estado vuelve al mensaje inicial.
     */
    @Test
    fun `cuando se reciben resultados el texto reconocido se actualiza y el estado se resetea`() {
        val textoEsperado = "abrir aplicación de cámara"

        // Simular flujo: preparar → escuchar → resultados
        controller.startPreparing()
        controller.onReadyForSpeech()
        controller.onSpeechResults(textoEsperado)

        assertEquals(
            "El texto reconocido debe coincidir con el resultado recibido",
            textoEsperado,
            controller.recognizedText
        )
        assertEquals(
            "El estado debe volver al mensaje inicial",
            HomeSpeechController.STATUS_INITIAL,
            controller.statusMessage
        )
        assertFalse(
            "isListening debe ser false tras recibir resultados",
            controller.isListening
        )
    }
}
