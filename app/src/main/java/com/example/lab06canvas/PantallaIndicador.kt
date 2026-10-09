package com.example.lab06canvas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaIndicador() {
    var progreso by remember { mutableFloatStateOf(0f) }

    val progresoAnimado by animateFloatAsState(
        targetValue = progreso,
        animationSpec = tween(durationMillis = 800),
        label = "progresoAnimado"
    )

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier.size(220.dp)) {
                val radio = size.minDimension / 2f - 16f
                val centro = Offset(size.width / 2f, size.height / 2f)

                drawCircle(
                    color = Color(0xFFE0E0E0),
                    radius = radio,
                    center = centro,
                    style = Stroke(width = 20f)
                )

                drawArc(
                    color = Color(0xFF3F51B5),
                    startAngle = -90f,
                    sweepAngle = 360f * progresoAnimado,
                    useCenter = false,
                    topLeft = Offset(centro.x - radio, centro.y - radio),
                    size = Size(radio * 2f, radio * 2f),
                    style = Stroke(width = 20f, cap = StrokeCap.Round)
                )

                val angulo = Math.toRadians((-90f + 360f * progresoAnimado).toDouble())
                val x = centro.x + radio * Math.cos(angulo).toFloat()
                val y = centro.y + radio * Math.sin(angulo).toFloat()
                drawCircle(color = Color(0xFFFF5722), radius = 18f, center = Offset(x, y))
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Progreso: ${(progresoAnimado * 100).toInt()}%",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (progresoAnimado < 0.5f) {
                    "Estado inicial: avance bajo"
                } else {
                    "Estado avanzado: más de la mitad completado"
                },
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(32.dp))

            Button(onClick = {
                progreso = if (progreso >= 1f) 0f else (progreso + 0.25f).coerceAtMost(1f)
            }) {
                Text("Avanzar progreso")
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(onClick = { progreso = 0f }) {
                Text("Reiniciar")
            }
        }
    }
}
