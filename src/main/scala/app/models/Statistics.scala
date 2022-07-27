package app.models

import scala.collection.immutable.ListMap

/**
 * A `Statistics` contains measurements for a set of sensors.
 *
 * @param sensors the measurements grouped by sensors' ids
 * @param totalCount the total number of FetchDatas
 * @param invalidCount the number of invalid FetchDatas
 */
case class Statistics(
                       sensors: Map[String, SensorStatistics] = Map(),
                       totalCount: Int = 0,
                       invalidCount: Int = 0,
                       docCount: Int = 0
                     ) {

  /**
   * Add a FetchData to this statistics
   * @param FetchData the FetchData to be added
   * @return a new `Statistics` incremented by the FetchData
   */
  def +(fetchdata: FetchData): Statistics = Statistics(
    sensors + (fetchdata.id -> (sensors.getOrElse(fetchdata.id, EmptySensorStatistics()) + fetchdata)),
    totalCount + 1,
    fetchdata match {
      case _: FetchValidData => invalidCount
      case _: FetchInvalidData => invalidCount + 1
    },
    1
  )

  /**
   * Add data from another `Statistics`
   * @param statistics the statistics to be added
   * @return a new `Statistics` incremented by the statistics
   */
  def +(statistics: Statistics): Statistics = Statistics(
    sensors ++ statistics.sensors.map { case (k, v) => k -> (v + sensors.getOrElse(k, EmptySensorStatistics())) },
    totalCount + statistics.totalCount,
    invalidCount + statistics.invalidCount,
    docCount + statistics.docCount
  )

  def toPrintableString: String = {
    val sb = new StringBuilder("")
    sb ++= s"Num of processed files: $docCount\r\n"
    sb ++= s"Num of processed measurements: $totalCount\r\n"
    sb ++= s"Num of failed measurements: $invalidCount\r\n"
    sb ++= s"\r\n"
    sb ++= s"Sensors with highest avg humidity:\r\n"
    sb ++= s"\r\n"
    sb ++= s"sensor-id,min,avg,max\r\n"

    val sortedMap = ListMap(sensors.toSeq.sortWith(_._2.average > _._2.average):_*)
    for ((k, v) <- sortedMap) {
      sb ++= s"$k,$v\r\n"
    }

    sb.toString()
  }

}