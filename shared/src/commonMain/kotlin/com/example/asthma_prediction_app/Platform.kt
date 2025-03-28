package com.example.asthma_prediction_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform