package app.models

/**
 * The `FetchData` trait can be used to represent a single data, both valid or invalid.
 */
sealed trait FetchData {
  def id: String
}

/**
 * A `FetchValidData` represents a valid FetchData from the sensor
 * @param id the id of the sensor
 * @param value the value of the FetchData
 */
case class FetchValidData(id: String, value: Int) extends FetchData

/**
 * A `FetchInvalidData` represents an invalid FetchData from the sensor
 * @param id the id of the sensor
 */
case class FetchInvalidData(id: String) extends FetchData