package app.models

class SensorStatisticsTest extends org.scalatest.funsuite.AnyFunSuite {

  test("Given empty stats When adding invalid FetchData Then returns empty stats") {

    val invaliddata = FetchInvalidData("s1")
    val stats = EmptySensorStatistics()
    val result = stats + invaliddata
    assert(result == stats)

  }

  test("Given non-empty stats When adding invalid FetchData Then returns the same stats") {

    val invaliddata = FetchInvalidData("s1")
    val stats = NonEmptySensorStatistics(0, 100, 50, 1)
    val result = stats + invaliddata
    assert(result == stats)

  }

  test("Given non-empty stats When adding valid FetchData Then returns incremented stats") {

    val validdata = FetchValidData("s1", 50)
    val stats = NonEmptySensorStatistics(0, 100, 50, 1)
    val expected = NonEmptySensorStatistics(0, 100, 100, 2)
    val result = stats + validdata
    assert(result == expected)

  }

  test("Setting new minimum") {

    val validdata = FetchValidData("s1", 25)
    val stats = NonEmptySensorStatistics(50, 100, 50, 1)
    val expected = NonEmptySensorStatistics(25, 100, 75, 2)
    val result = stats + validdata
    assert(result == expected)

  }

  test("Setting new maximum") {

    val validdata = FetchValidData("s1", 75)
    val stats = NonEmptySensorStatistics(50, 50, 50, 1)
    val expected = NonEmptySensorStatistics(50, 75, 125, 2)
    val result = stats + validdata
    assert(result == expected)

  }

}
