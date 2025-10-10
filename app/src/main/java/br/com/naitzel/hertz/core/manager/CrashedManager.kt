package br.com.naitzel.hertz.core.manager

import android.content.Context
import java.io.File

/**
 * Classe de gerenciamento de exceções.
 */
class CrashedManager(private val context: Context) {

    private val crashLogFileName = "crash_log.txt"

    /**
     * Verificar se há uma exceção.
     */
    fun isCrashed(): Boolean {
        return File(context.filesDir, crashLogFileName).exists()
    }

    fun crashed(thread: Thread, throwable: Throwable) {
        val log = """
            Thread: ${Thread.currentThread().name}
            Erro: ${throwable.message}
            StackTrace: ${throwable.stackTraceToString()}
        """.trimIndent()

        val file = File(context.filesDir, crashLogFileName)
        file.appendText("$log\n\n")
    }

    fun clear() {
        val file = File(context.filesDir, crashLogFileName)
        file.delete()
    }

    fun details(): String {
        val file = File(context.filesDir, crashLogFileName)
        val log = file.readText()
        return log
    }
}