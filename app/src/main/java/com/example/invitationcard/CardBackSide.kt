package com.example.invitationcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardBackSide() {

    // All card surface.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {

        // Top black half circle.
        CardBlackHalfCircles(modifier = Modifier.align(alignment = Alignment.TopCenter))

        Image(
            modifier = Modifier.size(size = 160.dp),
            painter = painterResource(id = R.drawable.baseline_downhill_skiing_24),
            contentDescription =""
        )

        // Bottom black half circle.
        CardBlackHalfCircles(modifier = Modifier.align(alignment = Alignment.BottomCenter))
    }
}

@Composable
@Preview
fun CardBackSidePreview() {
    CardBackSide()
}