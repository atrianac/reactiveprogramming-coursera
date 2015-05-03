package calculator

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(
    namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions.mapValues ( sig => Var(eval(sig(), namedExpressions) ))
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {

    def aux(expr: Expr, refs: Set[String]): Double = {
      expr match {
        case Literal(v) => v
        case Plus(a, b) => aux(a, refs) + aux(b, refs)
        case Minus(a, b) => aux(a, refs) - aux(b, refs)
        case Times(a, b) => aux(a, refs) * aux(b, refs)
        case Divide(a, b) => aux(a, refs) / aux(b, refs)
        case Ref(name) => {
          if (refs(name)) Double.NaN
          else aux(getReferenceExpr(name, references), refs + name)
        }
      }

    }

    aux(expr, Set())

  }

  /**
   * Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
    references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
