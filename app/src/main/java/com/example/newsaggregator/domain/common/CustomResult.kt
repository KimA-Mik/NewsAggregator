package com.example.newsaggregator.domain.common

sealed interface CustomResult<out S, out E> {
    data class Success<out S, E>(val data: S) : CustomResult<S, E>
    data class Error<S, out E>(val data: E) : CustomResult<S, E>
}

inline fun <S, E> CustomResult<S, E>.valueOr(alternative: (E) -> S): S {
    return when (this) {
        is CustomResult.Error -> alternative(data)
        is CustomResult.Success -> data
    }
}
