package app.fileutil

import app.models.{FetchInvalidData, FetchValidData}
import org.scalatest.matchers.should

class FileParserTest extends org.scalatest.funsuite.AsyncFunSuite with should.Matchers{

  test("Given invalid measurement Then returns invalid result") {

    val line = "s1,NaN"
    val expected = FetchInvalidData("s1")

    val future = FileParser.parseLine(line)

    future map { result => assert(result == expected) }
  }

  test("Given valid measurement Then returns valid result") {

    val line = "s1,50"
    val expected = FetchValidData("s1", 50)

    val future = FileParser.parseLine(line)

    future map { result => assert(result == expected) }
  }

}
