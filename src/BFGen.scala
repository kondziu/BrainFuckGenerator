import scala.annotation.tailrec

object BrainfuckGenerator {
  private def moveCounter(target: Int, counter: Int, counters: List[Int]): (String, Int) = {
    val (_, next) =
      (counters map ((value: Int) =>
        if (target > value)
          target - value
        else
          value - target))
        .zipWithIndex .sorted .head

    val move =
      if (next > counter)
        ">" * (next - counter)
      else if (next < counter)
        "<" * (counter - next)
      else
        ""

    (move, next)
  }

  private def changeCounter(target: Int, counter: Int, counters: List[Int]) = {
    val update =
      if (counters(counter) > target)
        "-" * (counters(counter) - target)
      else if (counters(counter) < target)
        "+" * (target - counters(counter))
      else
        ""

    val newCounters = counters.patch(counter, List(target), 1)

    (update, newCounters)
  }

  private def generatePreamble(counters: List[Int], precision: Int = 10, delimiter: String = ""): String = {
    val nonEmptyCounters =
      (counters filter ((value: Int) => value != 0))
    val iterations = "+" * precision
    val loopBegin = "["
    val values =
      ( nonEmptyCounters
        .map ((value: Int) => ">" + "+" * value))
        .reduce ((left: String, right: String) => left + right)
    val carriageReturn = "<" * nonEmptyCounters.length + "-"
    val loopEnd = "]"

    iterations + loopBegin + values + carriageReturn + loopEnd + delimiter
  }

  @tailrec
  private def generateProgram(targets: List[Int], counter: Int, counters: List[Int],
                              instructions: String, delimiter: String = ""): String = {
    targets match {
      case target :: remainder =>
        val (moveInstruction, newCounter) = moveCounter (target, counter, counters)
        val (updateInstuction, newCounters) = changeCounter (target, newCounter, counters)
        val newInstructions = instructions + moveInstruction + updateInstuction + "." + delimiter
        generateProgram(remainder, newCounter, newCounters, newInstructions)
      case Nil => instructions
    }
  }

  def generate(input: String, delimiter: String = "", precision: Int = 10): String = {
    val targets = (input map ((c: Char) => c.toInt)).toList
    val initialCounterValuesByPrecision =
      (((0::targets).toSet) map ((b: Int) => (b / precision))).toList.sorted

    val counters = initialCounterValuesByPrecision map
      ((value : Int) => value * precision)

    val preamble = generatePreamble(initialCounterValuesByPrecision, precision, delimiter)
    val program = generateProgram(targets, 0, counters, preamble, delimiter)

    program
  }
}

object BFGen extends App {
  val input = io.Source.stdin.getLines.toList.mkString("\n")
  val output = BrainfuckGenerator.generate(input)
  println(output)
}
