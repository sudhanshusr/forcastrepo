package com.cybage.javademo.scala.test

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  /*"forecat service for pincode" in new WithApplication {
    val forecast = route(FakeRequest(GET, "/forecast/271001")).get

    status(forecast) must equalTo(OK)
    contentType(forecast) must beSome.which(_ == "application/json")
    contentAsString(forecast) must contain("Gonda")
  }*/
  "forecast service for city name" in new WithApplication {
    val forecasta = route(FakeRequest(GET, "/forecast/pune")).get

    status(forecasta) must equalTo(OK)
    contentType(forecasta) must beSome.which(_ == "application/json")
    contentAsString(forecasta) must contain("Pune")
  }

}
