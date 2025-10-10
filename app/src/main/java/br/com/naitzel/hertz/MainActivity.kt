package br.com.naitzel.hertz

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import br.com.naitzel.hertz.core.manager.CrashedManager
import br.com.naitzel.hertz.core.manager.ThemeManager
import br.com.naitzel.hertz.presentation.ui.components.navigation.AppNavigation
import br.com.naitzel.hertz.presentation.ui.components.navigation.BackStackLogger
import br.com.naitzel.hertz.presentation.ui.components.navigation.NavigationObserver
import br.com.naitzel.hertz.presentation.ui.theme.HertzTheme
import com.squareup.leakcanary.core.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess

/**
 * Classe principal da aplicação.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Injeta o [ThemeManager] para gerenciar o tema da aplicação.
     */
    @Inject
    lateinit var themeManager: ThemeManager

    /**
     * Injeta o [CrashedManager] para gerenciar erros na aplicação
     */
    @Inject
    lateinit var crashManager: CrashedManager

    /**
     * Método chamado quando a atividade é criada.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Configuração do gerenciador de exceções.
         */
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            crashManager.crashed(thread, throwable)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            HertzTheme(themeManager) {
                val view = LocalView.current
                val statusBarColor by themeManager.statusBar.collectAsState()

                SideEffect {
                    (view.context as? Activity)?.window?.let { window ->
                        val color = statusBarColor.second.toArgb()
                        @Suppress("DEPRECATION")
                        window.statusBarColor = color

                        @Suppress("DEPRECATION")
                        window.navigationBarColor = color

                        WindowCompat.getInsetsController(window, window.decorView).apply {
                            isAppearanceLightStatusBars = statusBarColor.first
                            isAppearanceLightNavigationBars = statusBarColor.first
                        }
                    }
                }

                Box {
                    // Observa o estado de navegação
                    NavigationObserver(navController = navController)

                    // Navegação
                    AppNavigation(navController = navController)

                    // Apenas em debug
//                    if (BuildConfig.DEBUG) {
//
//                        // Log das rotas
//                        BackStackLogger(navController = navController)
//                    }
                }
            }
        }
    }
}
