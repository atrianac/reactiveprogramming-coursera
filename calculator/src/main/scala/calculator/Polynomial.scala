package calculator

import java.lang.Math._

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
    c: Signal[Double]): Signal[Double] = {
    Var(pow(b(), 2) - (4 * a() * c()))
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
    c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    
    Var(
        delta() match {
          case empty if empty < 0 => Set(0)
          case delta => Set(
          (-b() + sqrt(delta)) / (2 * a()),
          (-b() - sqrt(delta)) / (2 * a()))
        }
    )

  }
}
