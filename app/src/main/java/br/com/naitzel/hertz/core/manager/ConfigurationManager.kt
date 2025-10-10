package br.com.naitzel.hertz.core.manager

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore("app_session")

/**
 * Classe de gerenciamento de configuração.
 */
class ConfigurationManager(private val context: Context) {

    companion object Companion {
        private val THEME = stringPreferencesKey("theme")
    }
}