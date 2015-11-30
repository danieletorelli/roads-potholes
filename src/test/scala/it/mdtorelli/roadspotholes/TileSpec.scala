package it.mdtorelli.roadspotholes

import org.scalatest._

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:42
  */
object TileSpec {

  val one = Tile(1)
  val two = Tile(2)

}

class TileSpec extends FlatSpec with Matchers {

  import TileSpec._

  "Tile" should "be comparable with others" in {
    one < two
    one <= one
    two > one
    two >= two
    one == Tile(1)
    one != Tile(3)
  }

  it should "be print-friendly" in {
    one.toString === "1"
    two.toString === "2"
  }

}
