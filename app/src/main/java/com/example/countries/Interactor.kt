package com.example.countries

fun interface Interactor<T> : suspend () -> T
