package com.massimoregoli.esercitazionecf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.massimoregoli.esercitazionecf.model.User
import com.massimoregoli.esercitazionecf.ui.theme.EsercitazioneCFTheme
import androidx.compose.runtime.getValue
import com.massimoregoli.esercitazionecf.screens.MainView
import com.massimoregoli.esercitazionecf.ui.theme.BackgroundColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsercitazioneCFTheme {
                val user by rememberSaveable {
                    mutableStateOf(User(this))
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {
                    MainView(user)
                }
            }
        }
    }
}
