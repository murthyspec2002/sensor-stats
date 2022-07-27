package app.models

class StatisticsTest extends org.scalatest.funsuite.AnyFunSuite {

  test("Given empty statistics When adding invalid reading Then returns updated statistics") {

    val emptyStatistics = Statistics()
    val invaliddata = FetchInvalidData("s1")
    val result = emptyStatistics + invaliddata
    val expected = Statistics(Map("s1" -> EmptySensorStatistics()), 1, 1, 1)
    assert(result == expected)
  }

  test("Given empty statistics When adding valid reading Then returns updated statistics") {

    val emptyStatistics = Statistics()
    val invaliddata = FetchValidData("s1", 50)
    val result = emptyStatistics + invaliddata
    val expected = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 1, 0, 1)
    assert(result == expected)
  }

  test("Given non-empty statistics When adding invalid reading Then returns updated statistics") {
    val emptyStatistics = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 1, 0, 1)
    val invaliddata = FetchInvalidData("s1")
    val result = emptyStatistics + invaliddata
    val expected = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 2, 1, 1)
    assert(result == expected)
  }

  test("Given non-empty statistics When adding valid reading Then returns updated statistics") {
    val emptyStatistics = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 1, 0, 1)
    val invaliddata = FetchValidData("s1", 50)
    val result = emptyStatistics + invaliddata
    val expected = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 100, 2)), 2, 0, 1)
    assert(result == expected)
  }

  test("Given empty statistics When adding empty statistics Then returns statistics") {
    val emptyStatistics1 = Statistics(Map("s1" -> EmptySensorStatistics()))
    val emptyStatistics2 = Statistics(Map("s1" -> EmptySensorStatistics()))
    val result = emptyStatistics1 + emptyStatistics2
    val expected = Statistics(Map("s1" -> EmptySensorStatistics()))
    assert(result == expected)
  }

  test("Given empty statistics When adding non-empty statistics Then returns non-empty statistics") {
    val emptyStatistics1 = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 1, 0, 1)
    val emptyStatistics2 = Statistics(Map("s1" -> EmptySensorStatistics()))
    val result = emptyStatistics1 + emptyStatistics2
    val expected = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 1, 0, 1)
    assert(result == expected)
  }

  test("Given non-empty statistics When adding non-empty statistics Then returns aggregated statistics") {
    val statistics1 = Statistics(Map("s1" -> NonEmptySensorStatistics(50, 50, 50, 1)), 3, 2, 1)
    val statistics2 = Statistics(Map("s2" -> NonEmptySensorStatistics(50, 50, 50, 1)), 2, 1, 1)
    val result = statistics1 + statistics2
    val expected = Statistics(
      Map(
        "s1" -> NonEmptySensorStatistics(50, 50, 50, 1),
        "s2" -> NonEmptySensorStatistics(50, 50, 50, 1)
      ), 5, 3, 2)
    assert(result == expected)
  }

}
