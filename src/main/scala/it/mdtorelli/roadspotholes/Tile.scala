package it.mdtorelli.roadspotholes

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:27
  */

/**
  * Tile class
  * @author Daniele Torelli
  * @constructor Create a tile with specified height
  * @param height Height of the tile
  */
case class Tile(height: Int) extends Ordered[Tile] {

  /**
    * Compare two tiles by `height`
    * @param that Other tile to compare
    * @return - `x < 0` if `this < that`
    *         - `x == 0` if `this == that`
    *         - `x > 0` if  `this > that`
    */
  override def compare(that: Tile): Int = this.height - that.height

  /**
    * Print tile object in a compact form
    */
  override def toString = height.toString

}
