package com.example.accesibilidadapp.util

inline fun <T> safeRun(
    onError: (Exception) -> T,
    block: () -> T
): T {
    return try {
        block()
    } catch (e: Exception) {
        onError(e)
    }
}

inline fun <T> safeRunOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

inline fun safeExecute(
    onError: (Exception) -> Unit = {},
    block: () -> Unit
) {
    try {
        block()
    } catch (e: Exception) {
        onError(e)
    }
}
