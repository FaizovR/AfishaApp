package ru.faizovr.afisha.presentation.contract

interface EventListContract {

    interface View {
        fun setText(message: String)
    }

    interface Presenter {
        fun init()
    }
}