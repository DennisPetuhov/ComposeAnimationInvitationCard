package com.example.invitationcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.invitationcard.ui.theme.InvitationCardTheme
import kotlin.math.abs

val spaceBetweenItems = 28.dp
 val framePadding = 24.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvitationCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}


@Composable
fun ThreadsInviteCard() {
    // Manage animations.
    var isAutomaticAnimationActive by remember { mutableStateOf(true) }
    var isCompletingAnimationActive by remember { mutableStateOf(false) }
    var isQuickDragAnimationActive by remember { mutableStateOf(false) }

    // Follow drag amount to manage QuickDragAnimation.
    var animationDragAmount by remember { mutableStateOf(0f) }
    // This is our custom value of Axis-Y on coordinate system.
    var axisY by remember { mutableStateOf(0f) }

    ThreadsInviteCardHolder (
        frontSide = {
            CardFrontSide(user = User())
        },
        backSide = {
            CardBackSide()
        },
        positionAxisY = if (isAutomaticAnimationActive) {
            val automaticTurningAnimation = remember { Animatable(axisY) } // Auto-turning animation.

            LaunchedEffect(isAutomaticAnimationActive) {
                if (isAutomaticAnimationActive) {
                    automaticTurningAnimation.animateTo(
                        targetValue = if (axisY >= 0) {
                            axisY + 360f // Turn right.
                        } else {
                            - 360f + axisY // Turn left.
                        },
                        animationSpec = infiniteRepeatable(
                            tween(7000, easing = FastOutSlowInEasing)
                        ),
                    )
                }
            }
            axisY = automaticTurningAnimation.value // Do not forget to update axis-Y.
            automaticTurningAnimation.value // Finally, return animation value.
        }
        else if (isQuickDragAnimationActive) {
            val completeQuickDragAnimation = remember { Animatable(axisY) }

            LaunchedEffect(isQuickDragAnimationActive) {
                if (isQuickDragAnimationActive) {

                    val completeTurningAnimationState = completeQuickDragAnimation.animateTo(
                        targetValue = if (animationDragAmount > 0) {
                            360f * 2
                        } else {
                            -360f * 2
                        },
                        animationSpec = tween(1250, easing = LinearEasing)
                    ).endState

                    if (!completeTurningAnimationState.isRunning) {
                        isQuickDragAnimationActive = false
                        isAutomaticAnimationActive = true
                    }
                }
            }
            axisY = completeQuickDragAnimation.value
            completeQuickDragAnimation.value

        }
        else {
            axisY
        },
        modifier = Modifier
            .padding(
                horizontal = 48.dp,
                vertical = 210.dp
            )
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        isAutomaticAnimationActive = false // Stop animation.
                    },
                    onDragEnd = {   // Stop animations.
                        isAutomaticAnimationActive = false
                        isCompletingAnimationActive = false
                        isQuickDragAnimationActive = false

                        // If user did not drag enough, just show completing animation.
                        if (abs(animationDragAmount) > 12f) {
                            isQuickDragAnimationActive = true
                        } else {
                            isCompletingAnimationActive = true
                        }

                    },
                    onDragCancel = {

                    },
                    onHorizontalDrag = { change, dragAmount ->

                        // Decide to turn card in which side.
                        axisY = if (dragAmount < 0) {
                            (axisY - abs(dragAmount)) % 360 // Turn left for negative numbers.
                        } else {
                            (axisY + abs(dragAmount)) % 360 // Turn right for positive numbers.
                        }
                        animationDragAmount = dragAmount // Keep updated drag amount.
                    }
                )
            },
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
ThreadsInviteCard()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InvitationCardTheme {
        Greeting("Android")
    }
}