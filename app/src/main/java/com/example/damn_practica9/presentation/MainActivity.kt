package com.example.damn_practica9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.* // Importa todo desde wear.compose.material

// Clase de datos para representar un partido
data class MatchScore(val homeTeam: String, val awayTeam: String, val score: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura el contenido de la interfaz de usuario usando Jetpack Compose para Wear OS
        setContent {
            // CORRECCIÓN: Se debe usar el MaterialTheme de Wear Compose
            MaterialTheme {
                WearApp("DAMN-Practica9")
            }
        }
    }
}

@Composable
fun WearApp(appName: String) {
    // Lista de ejemplo de puntuaciones de partidos
    val matchScores = listOf(
        MatchScore("Real Madrid", "Barcelona", "2 - 1"),
        MatchScore("Atlético Madrid", "Valencia", "1 - 1"),
        MatchScore("Sevilla", "Betis", "3 - 0"),
        MatchScore("Liverpool", "Man Utd", "2 - 0"),
        MatchScore("Bayern M.", "Dortmund", "4 - 2"),
        MatchScore("PSG", "Marseille", "2 - 2")
    )

    val listState = rememberScalingLazyListState()

    // Utiliza el Scaffold para proporcionar una estructura básica de la pantalla de Wear OS
    Scaffold(
        timeText = {
            // Muestra la hora actual en la parte superior de la pantalla
            TimeText()
        },
        vignette = {
            // Agrega un efecto de viñeta para oscurecer los bordes de la pantalla
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            // Muestra una barra de desplazamiento que se adapta a la forma de la pantalla
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        // ScalingLazyColumn es una lista optimizada para pantallas redondas de Wear OS,
        // ya que escala los elementos a medida que se desplazan.
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 32.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = listState
        ) {
            // Elemento de título
            item {
                Text(
                    text = "Resultados",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            // CORRECCIÓN: Se usa `items` directamente sin el alias `wearItems` para mayor claridad.
            // Lista de puntuaciones de partidos
            items(matchScores) { score ->
                MatchScoreCard(score = score)
            }
        }
    }
}

@Composable
fun MatchScoreCard(score: MatchScore) {
    // Tarjeta individual para mostrar la puntuación de un partido
    Card(
        onClick = { /* Acción al hacer clic en la tarjeta (opcional) */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono de fútbol
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = "Fútbol",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(20.dp)
            )

            // Nombres de los equipos y puntuación
            Column(
                // MEJORA: Se añade padding horizontal para separar el texto de los iconos.
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${score.homeTeam} vs ${score.awayTeam}",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = score.score,
                    color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Otro icono para simetría
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = "Fútbol",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun WearAppPreview() {
    MaterialTheme {
        WearApp("DAMN-Practica9")
    }
}