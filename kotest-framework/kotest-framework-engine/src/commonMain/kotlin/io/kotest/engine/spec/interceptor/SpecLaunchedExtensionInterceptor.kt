package io.kotest.engine.spec.interceptor

import io.kotest.core.extensions.SpecLaunchedExtension
import io.kotest.core.spec.SpecRef
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlin.reflect.KClass

class SpecLaunchedExtensionInterceptor(
   private val extensions: List<SpecLaunchedExtension>
) : SpecRefInterceptor {

   override suspend fun intercept(
      fn: suspend (SpecRef) -> Map<TestCase, TestResult>
   ): suspend (SpecRef) -> Map<TestCase, TestResult> = { ref ->
      var results = emptyMap<TestCase, TestResult>()
      val initial: suspend (KClass<*>) -> Unit = { results = fn(ref) }
      val chain = extensions.foldRight(initial) { op, acc -> { op.launched(ref.kclass) { acc(ref.kclass) } } }
      chain.invoke(ref.kclass)
      results
   }
}
