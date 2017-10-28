package com.abhi.webservice.testkit

import com.abhi.webservice.testkit.WebServiceTestKit._
import io.circe.generic.auto._

/**
  * Created by ASrivastava on 10/27/17.
  */

case class SayHello(name: String)
case class SayHelloResponse(msg: String)

object Example extends App {
   Post("/foo/bar", SayHello("abhishek")) ~> addHeader("auth", "xxxyyyy") ~> check { resp =>
      assert(resp.status == StatusCodes.OK)
      val response = responseAs[SayHelloResponse](resp.body)
      assert(response.msg == "Hello World abhishek")
   }
}
