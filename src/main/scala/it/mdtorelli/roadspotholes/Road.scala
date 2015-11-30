package it.mdtorelli.roadspotholes

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:04
  */
object Road {

  val DEFAULT_TILES_LENGTH: Int = 15
  val DEFAULT_TILES_MAX_HEIGHT: Int = 15

}

class Road(val tiles: Seq[Tile]) {

  // Auto-generate road with specified length and max height
  def this(tilesLength: Int = Road.DEFAULT_TILES_LENGTH,
           maxTilesHeight: Int = Road.DEFAULT_TILES_MAX_HEIGHT) = {
    this(for (i <- 0 until tilesLength) yield {
      Tile((Math.random() * maxTilesHeight).toInt)
    })
  }

  override def toString = tiles.toString

  def tileAt(idx: Int): Option[Tile] = if (tiles.isDefinedAt(idx)) Some(tiles(idx)) else None

  def peaks: Seq[Int] = {
    val peakCandidates = for (i <- tiles.indices if isPeak(i)) yield i
    val peakHollows = new Road(peakCandidates.map(tileAt(_).get)).hollows
    dropIndexes(peakCandidates, peakHollows)
  }

  def hollows: Seq[Int] = for (i <- tiles.indices if isHollow(i)) yield i

  def isPeak(idx: Int): Boolean = {
    val prev = if (idx == 0) Tile(0) else tiles(idx - 1)
    val next = if (idx == tiles.length - 1) Tile(0) else tiles(idx + 1)
    val curr = tiles(idx)
    curr >= prev && curr >= next
  }

  def isHollow(idx: Int): Boolean = {
    val prev = if (idx == 0) Tile(0) else tiles(idx - 1)
    val next = if (idx == tiles.length - 1) Tile(0) else tiles(idx + 1)
    val curr = tiles(idx)
    curr < prev && curr < next
  }

  def waterVolume(startIdx: Int, endIdx: Int): Int = {
    val height = Math.min(tiles(startIdx).height, tiles(endIdx).height)

    def sumVolume(idx: Int, acc: Int = 0): Int = {
      if (idx < endIdx) {
        sumVolume(idx + 1, acc + (height - tiles(idx).height))
      } else acc
    }

    sumVolume(startIdx + 1)
  }

  def totalWaterVolume = peaks.sliding(2).map(w => waterVolume(w.head, w.last)).sum

  private def dropIndexes[T](s: Seq[T], idxs: Seq[Int]): Seq[T] =
    if (idxs.nonEmpty) s.indices.filterNot(idxs.contains).map(s) else s

}
