package com.cybage.javademo.scala.controller

import play.api.mvc.Controller
import play.api.libs.ws.WS
import scala.concurrent.Await
import play.api.mvc.Action
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Logger
import org.apache.http.protocol.RequestContent
import java.util.Calendar
/**
 * WeatherServiceController: Using ScalaWS api to get the forcasted weather from api.openweathermap.org
 *
 * urlPrefix, urlPostfix, tokenKey : Inserting city as string as query string using forcasWeather method.
 *
 */
object WeatherServiceController extends Controller {
  val urlPrefix = "http://api.openweathermap.org/data/2.5/forecast?q="
  val urlPostfix = "&units=Metric&APPID="
  val tokenKey = "026bae7a696c12547b424cb730ff80e4"
  
  
  val urlPrefixDayWise="http://api.openweathermap.org/data/2.5/forecast/daily?q="
   // pune
   val urlPostfixDayWise="&mode=json&units=metric&cnt="
     // 10
     val tokenKeyDayWise="&appid=026bae7a696c12547b424cb730ff80e4"

  /**
   * forecastWeather: Getting called from  conf/routes file, having city as query string to be called using WS.get(URL)
   *
   * Getting response object from Await.result who is ensuring it will try to get the response until
   * the time reaches Double.PositiveInfinity(Duration.Inf)
   */
  def forecastByCity(city: String) = Action.async {

    implicit request =>

      val requestTime = Calendar.getInstance().getTime().getTime
      
      // Logger => Request ID, Method, Path, Host, Remote Address,Remote Version
      Logger.debug("Request => " + "Request ID :" + request.id + ", Method :" + request.method + ", Path :" + request.path + ", Host :" + request.host + ", Remote Address: " + request.remoteAddress + ", Remote Version: " + request.version)
      val resp = WS.url(urlPrefix + city + urlPostfix + tokenKey).get()
      val response = Await.result(resp, Duration.Inf)

      if (response.status == 200) {
        //total processing time 
        val totalRequestProcessingTime = ((Calendar.getInstance().getTime().getTime) - requestTime)
        Logger.info("Response =>" + response.json+ "totalRequestProcessingTime" + totalRequestProcessingTime + "ms")
        resp.map { response => Ok((response.json)).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")}
      } else {
        Logger.info("Response =>" + response.body.toString())
        resp.map { response => Ok(response.statusText).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")}
      }

  }
  def forecastByCityAndDays(city: String,days:Int) = Action.async {

    implicit request =>
      val requestTime = Calendar.getInstance().getTime().getTime
      
      Logger.debug("Request => " + "Request ID :" + request.id + ", Method :" + request.method + ", Path :" + request.path + ", Host :" + request.host + ", Remote Address: " + request.remoteAddress + ", Remote Version: " + request.version)
      println("==>URL : "+urlPrefixDayWise + city + urlPostfixDayWise+days.toString()+ tokenKeyDayWise)
      val resp = WS.url(urlPrefixDayWise + city + urlPostfixDayWise+days.toString()+ tokenKeyDayWise).get()
      val response = Await.result(resp, Duration.Inf)

      if (response.status == 200) {
        //total processing time 
        val totalRequestProcessingTime = ((Calendar.getInstance().getTime().getTime) - requestTime)
        Logger.info("Response =>" + response.json+ "totalRequestProcessingTime" + totalRequestProcessingTime + "ms")
        resp.map { response => Ok((response.json)).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")}
      } else {
        Logger.info("Response =>" + response.body.toString())
        resp.map { response => Ok(response.statusText).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")}
      }
      
      
  }

}