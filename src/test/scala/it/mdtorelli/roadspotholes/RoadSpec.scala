package it.mdtorelli.roadspotholes

import org.scalatest._

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:10
  */
object RoadSpec {

  lazy val road = new Road(tiles.map(Tile))
  private val tiles: Seq[Int] = Seq(3, 5, 8, 4, 3, 5, 2, 7, 3, 2, 4)

}

class RoadSpec extends FlatSpec with Matchers {

  import RoadSpec._

  "Road" should "find peaks" in {
    val peaks = road.peaks

    peaks shouldBe a[Seq[_]]
    peaks should have size 3
    peaks shouldBe sorted
    peaks should contain inOrderOnly(2, 7, 10)
  }

  it should "find hollows" in {
    val hollows = road.hollows

    hollows shouldBe a[Seq[_]]
    hollows should have size 3
    hollows shouldBe sorted
    hollows should contain inOrderOnly(4, 6, 9)
  }

  it should "determine if tile at index is a peak" in {
    road.isPeak(2) === true
    road.isPeak(7) === true
    road.isPeak(10) === true

    road.isPeak(4) === false
    road.isPeak(6) === false
    road.isPeak(9) === false
  }

  it should "determine if tile at index is a hollow" in {
    road.isHollow(4) === true
    road.isHollow(6) === true
    road.isHollow(9) === true

    road.isHollow(2) === false
    road.isHollow(7) === false
    road.isHollow(10) === false
  }

  it should "compute the water volume between two peaks" in {
    road.waterVolume(2, 7) === 14
    road.waterVolume(7, 10) === 3
  }

  it should "compute the total water volume between all peaks" in {
    road.totalWaterVolume === 17
  }

  it should "get tiles by index" in {
    val tile = road.tileAt(2)
    tile shouldBe a[Some[_]]
    tile.get === 8
  }

  it should "be print-friendly" in {
    road.toString === "3, 5, 8, 4, 3, 5, 2, 7, 3, 2, 4"
  }

  it should "not be writable" in {
    "road.tiles = Seq.empty" shouldNot compile
  }

}
