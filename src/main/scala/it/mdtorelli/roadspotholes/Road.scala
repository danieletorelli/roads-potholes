package it.mdtorelli.roadspotholes

/**
  * Author: Daniele Torelli
  * Project: roads-potholes
  * Date: 30/11/15
  * Time: 11:04
  */
object Road {

  /**
    * Default tiles length (15)
    */
  val DEFAULT_TILES_LENGTH: Int = 15

  /**
    * Default tiles max height (15)
    */
  val DEFAULT_TILES_MAX_HEIGHT: Int = 15

}

/**
  * Road class
  * @author Daniele Torelli
  * @constructor Create a road by specified tiles
  * @param tiles Tiles of the road
  */
class Road(val tiles: Seq[Tile]) {

  /**
    * Auto-generate a random road with specified length and max height
    * @constructor Create a random road by length and max height
    * @param tilesLength Road length (default: 15)
    * @param maxTilesHeight Max height of tiles (default: 15)
    */
  def this(tilesLength: Int = Road.DEFAULT_TILES_LENGTH,
           maxTilesHeight: Int = Road.DEFAULT_TILES_MAX_HEIGHT) = {
    this(for (i <- 0 until tilesLength) yield {
      Tile((Math.random() * maxTilesHeight).toInt)
    })
  }

  /**
    * Print road objects in a compact form
    */
  override def toString = tiles.mkString("[", ", ", "]")

  /**
    * Get tile at specified index, if exists
    * @param idx Road index
    * @return `Some[Tail]` if index is valid, `None` otherwise
    */
  def tileAt(idx: Int): Option[Tile] = if (tiles.isDefinedAt(idx)) Some(tiles(idx)) else None

  /**
    * Find peaks in the road
    * @return Peaks indexes
    */
  def peaks: Seq[Int] = {
    val peakCandidates = for (i <- tiles.indices if isPeak(i)) yield i
    val peakHollows = new Road(peakCandidates.map(tileAt(_).get)).hollows
    dropIndexes(peakCandidates, peakHollows)
  }

  /**
    * Find hollows in the road
    * @return Hollows indexes
    */
  def hollows: Seq[Int] = for (i <- tiles.indices if isHollow(i)) yield i

  /**
    * Check if tile at specified index is a peak of the road
    * @param idx Road index
    * @return `true` if is a peak, `false` otherwise
    */
  def isPeak(idx: Int): Boolean = {
    val prev = if (idx == 0) Tile(0) else tiles(idx - 1)
    val next = if (idx == tiles.length - 1) Tile(0) else tiles(idx + 1)
    val curr = tiles(idx)
    curr >= prev && curr >= next
  }

  /**
    * Check if tile at specified index is a hollow of the road
    * @param idx Road index
    * @return `true` if is a hollow, `false` otherwise
    */
  def isHollow(idx: Int): Boolean = {
    val prev = if (idx == 0) Tile(0) else tiles(idx - 1)
    val next = if (idx == tiles.length - 1) Tile(0) else tiles(idx + 1)
    val curr = tiles(idx)
    curr < prev && curr < next
  }

  /**
    * Compute water volume between two road peaks
    * @param startIdx Starting peak index (excluded)
    * @param endIdx Ending peak index (excluded)
    * @return Water volume
    * @note The computation is performed with a tail recursive method
    */
  def waterVolume(startIdx: Int, endIdx: Int): Int = {
    val height = Math.min(tiles(startIdx).height, tiles(endIdx).height)

    def sumVolume(idx: Int, acc: Int = 0): Int = {
      if (idx < endIdx) {
        sumVolume(idx + 1, acc + (height - tiles(idx).height))
      } else acc
    }

    sumVolume(startIdx + 1)
  }

  /**
    * Compute water volume between all road peaks
    * @return Water volume
    * @note The computation is performed with a sliding window of size 2 between peaks
    */
  def totalWaterVolume = peaks.sliding(2).map(w => waterVolume(w.head, w.last)).sum

  /**
    * Drop a list of indexes from a specified `Seq[T]`
    * @param s Original sequence
    * @param idxs List of indexes to drop
    * @tparam T Original sequence object's type
    * @return `Seq[T]` without elements at specified indexes
    */
  private def dropIndexes[T](s: Seq[T], idxs: Seq[Int]): Seq[T] =
    if (idxs.nonEmpty) s.indices.filterNot(idxs.contains).map(s) else s

}
