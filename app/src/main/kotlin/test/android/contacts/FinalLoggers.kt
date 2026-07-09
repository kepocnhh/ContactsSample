package test.android.contacts

import android.util.Log

internal class FinalLoggers : Loggers {
    override fun create(tag: String): Logger {
        return FinalLogger(tag = tag)
    }
}

private class FinalLogger(
    private val tag: String,
) : Logger {
    override fun debug(message: String) {
        Log.d(tag, message)
    }
}
