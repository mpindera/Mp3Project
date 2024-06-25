package com.example.mp3project.model.data

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.example.mp3project.R

class Tabs(val context: Context) {
  val tabs = listOf(
    context.getString(R.string.wszystkie),
    context.getString(R.string.polska),
    context.getString(R.string.swiat),
    context.getString(R.string.biznes),
    context.getString(R.string.technologie),
    context.getString(R.string.moto),
    context.getString(R.string.kultura),
    context.getString(R.string.sport),
    context.getString(R.string.favorite)
  )
}