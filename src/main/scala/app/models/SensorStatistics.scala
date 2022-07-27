package app.models

/**
 * The `SensorStatistics` trail can be used to represent statistical data of all FetchData for a single sensor
 */
sealed trait SensorStatistics {

  /**
   * Add a new FetchData to this measurement
   *
   * @param FetchData the FetchData to be added
   * @return a new `SensorStatistics` incremented by the FetchData
   */
  def +(FetchData: FetchData): SensorStatistics

  /**
   * Add statistical data from two measurements
   *
   * @param measurement the measurement to be added
   * @return a `SensorStatistics` that combines data from both measurements
   */
  def +(measurement: SensorStatistics): SensorStatistics

  /**
   * Calculates an average from all FetchDatas
   * @return the average from all FetchData
   */
  def average: Float = 0
}

/**
 * A `EmptySensorStatistics` represents an empty measurement or statistics for a sensor that has only invalid FetchDatas
 */
case class EmptySensorStatistics() extends SensorStatistics {

  def +(fetchdata: FetchData): SensorStatistics = fetchdata match {
    case _: FetchInvalidData => this
    case fetchvaliddata: FetchValidData => NonEmptySensorStatistics(
      fetchvaliddata.value,
      fetchvaliddata.value,
      fetchvaliddata.value,
      1
    )
  }

  def +(rhs: SensorStatistics): SensorStatistics = rhs match {
    case rhs: NonEmptySensorStatistics => rhs
    case _: EmptySensorStatistics => EmptySensorStatistics()
  }

  override def average: Float = -1

  override def toString: String = "NaN,NaN,NaN"
}

/**
 * A `NonEmptySensorStatistics` represents an non-empty statistics for a sensor, the sensor has at least one valid FetchData
 */
case class NonEmptySensorStatistics(min: Int, max: Int, value: Int, count: Int) extends SensorStatistics {

  def +(fetchdata: FetchData): SensorStatistics = fetchdata match {
    case _: FetchInvalidData => this
    case fetchvaliddata: FetchValidData => NonEmptySensorStatistics(
      math.min(min, fetchvaliddata.value),
      math.max(max, fetchvaliddata.value),
      value + fetchvaliddata.value,
      count + 1
    )
  }

  def +(rhs: SensorStatistics): SensorStatistics = rhs match {
    case rhs: NonEmptySensorStatistics => NonEmptySensorStatistics(
      math.min(min, rhs.min),
      math.max(max, rhs.max),
      value + rhs.value,
      count + rhs.count
    )
    case _: EmptySensorStatistics => NonEmptySensorStatistics(min, max, value, count)
  }

  override def average: Float = value.toFloat / count

  override def toString: String = s"$min,${math.round(average)},$max"

}
