package com.example.trendingmovies.presentation.common

import android.content.Context

sealed class UiEvent {
    class ShowSnackBar(private var messageId: Int) : UiEvent() {
        fun getMsgAsString(ctx: Context): String {
            return ctx.getString(messageId)
        }
    }
}