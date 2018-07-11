package soSuspendableCallInLambda

import forTests.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

private fun foo(a: Any) {}

val waiter = WaitFinish()

fun main(args: Array<String>) {
    builder {
        //Breakpoint!
        run()
        foo("End")
    }

    foo("Main end")
    waiter.waitEnd()
}

suspend fun run() {
    suspendCoroutine { cont: Continuation<Unit> ->
        Thread {
            Thread.sleep(10)
            cont.resume(Unit)
            waiter.finish()
        }.start()
    }
}

// STEP_OVER: 2