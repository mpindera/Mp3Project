package com.example.mp3project.view.custom

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

object CustomGradient {
  val lighterRed = lerp(Color.Red, Color.White, 0.5f)
  val lighterBlack= lerp(Color.Black, Color.White, 0.2f)

  val gradientBrush = Brush.linearGradient(
    colors = listOf(
      lighterRed, lighterBlack
    ), start = Offset.Zero, end = Offset.Infinite
  )

  val lighterRedL = lerp(Color.Red, Color.White, 0.7f)
  val lighterBlackL = lerp(Color.Black, Color.White, 0.5f)

  val gradientBrushL = Brush.linearGradient(
    colors = listOf(
      lighterRedL, lighterBlackL
    ), start = Offset.Zero, end = Offset.Infinite
  )
}