package it.mdtorelli.roadspotholes

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:27
  */
case class Tile(height: Int) extends Ordered[Tile] {

  override def compare(that: Tile): Int = this.height - that.height

  override def toString = height.toString

}
