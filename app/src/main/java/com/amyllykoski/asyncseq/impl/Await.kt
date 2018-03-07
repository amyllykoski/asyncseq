package com.amyllykoski.asyncseq.impl

class Await

fun <T : Any> await(t: T, init: T.() -> Unit): T {
    t.init()
    return t
}