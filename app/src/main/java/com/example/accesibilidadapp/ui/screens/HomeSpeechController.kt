package com.example.accesibilidadapp.ui.screens

/**
 * Controlador de estado para el reconocimiento de voz en HomeScreen.
 * Clase pura Kotlin (sin dependencias de Android SDK) para facilitar pruebas unitarias.
 */
class HomeSpeechController {

    companion object {
        // Códigos de error equivalentes a SpeechRecognizer
        const val ERROR_NO_MATCH = 7
        const val ERROR_SPEECH_TIMEOUT = 6
        const val ERROR_NETWORK = 2

        const val STATUS_INITIAL = "Presiona el botón para hablar"
        const val STATUS_LISTENING = "Escuchando..."
        const val STATUS_PROCESSING = "Procesando..."
        const val STATUS_PREPARING = "Preparando micrófono..."
    }

    var statusMessage: String = STATUS_INITIAL
        private set

    var isListening: Boolean = false
        private set

    var recognizedText: String = ""
        private set

    fun onReadyForSpeech() {
        statusMessage = STATUS_LISTENING
        isListening = true
    }

    fun onEndOfSpeech() {
        isListening = false
        statusMessage = STATUS_PROCESSING
    }

    fun onSpeechError(errorCode: Int) {
        isListening = false
        statusMessage = when (errorCode) {
            ERROR_NO_MATCH -> "No se entendió. Intenta de nuevo."
            ERROR_SPEECH_TIMEOUT -> "No detecté voz. Intenta de nuevo."
            ERROR_NETWORK -> "Error de red."
            else -> "Error al reconocer voz (código $errorCode)."
        }
    }

    fun onSpeechResults(text: String?) {
        recognizedText = text ?: ""
        statusMessage = STATUS_INITIAL
        isListening = false
    }

    fun startPreparing() {
        recognizedText = ""
        statusMessage = STATUS_PREPARING
    }
}
