package it.mdtorelli.roadspotholes

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:02
  */

/**
  * Main class
  * @author Daniele Torelli
  */
object Main {

  /**
    * Compute total water volume of an auto-generated random road
    */
  def main(args: Array[String]) = {
    val road = new Road                   // Create a random road with length 15 and max height 15
    val peaks = road.peaks                // Find peaks in the road
    val volume = road.totalWaterVolume    // Compute total water wolume between the road peeks

    println(s"Road: $road")
    println(s"Peaks: ${peaks.mkString("[", ", ", "]")}")
    println(s"Total water volume: $volume")
  }

}
