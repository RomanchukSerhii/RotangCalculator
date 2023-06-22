package com.example.rotangcalculator.presentation.viewmodels

sealed class State

object Error : State()
class Result(val result: String) : State()