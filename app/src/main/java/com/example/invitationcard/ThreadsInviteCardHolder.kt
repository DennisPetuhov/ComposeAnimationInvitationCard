package com.example.invitationcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.abs

/**
 * This holder manages logic to show correct side of the card.
 */
@Composable
fun ThreadsInviteCardHolder(
    modifier: Modifier = Modifier,
    positionAxisY: Float,
    frontSide: @Composable () -> Unit = {},
    backSide: @Composable () -> Unit = {},
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = positionAxisY // Move card according to value of customY.
                cameraDistance = 14f * density
            },
    ) {

        // Here, logic is about coordinate system such as [0..90], [91..270], [270..360].
        if (abs(positionAxisY.toInt()) % 360 <= 90) {
            Box(
                Modifier.fillMaxSize()
            ) {
                frontSide()
            }
        } else if (abs(positionAxisY.toInt()) % 360 in 91..270) {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f // Important to avoid mirror effect.
                    },
            ) {
                backSide()
            }
        } else {
            Box(
                Modifier.fillMaxSize()
            ) {
                frontSide()
            }
        }
    }
}