package com.test.arch.domain.utils

fun <A, B, R> ifNotNull(a: A?,
                        b: B?,
                        code: (A, B) -> R) {
    if (a != null && b != null) {
        code(a, b)
    }
}

fun <R, A, B> whenNotNull(a: A?,
                          b: B?,
                          block: (A, B) -> R): R? =
    if (a != null && b != null) {
        block(a, b)
    } else null
