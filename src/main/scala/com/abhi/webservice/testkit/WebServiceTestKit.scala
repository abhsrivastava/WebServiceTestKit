package com.abhi.webservice.testkit

import io.circe._
import io.circe.parser._
import org.http4s.Status.Successful
import org.http4s._
import org.http4s.client.blaze.PooledHttp1Client
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

/**
  * Created by ASrivastava on 10/27/17.
  */

case class WebTestKitResponse(status: StatusCodes.Value, body: String)
object WebServiceTestKit {

   implicit class FlareHttpRequestDsl(request: Request) {
      def ~>(f: Request => Request) : Request = f(request)
      def ~>(f: Request => Unit) : Unit = f(request)
   }

   val client = PooledHttp1Client()
   private def toUri(url: String): Uri = Uri.unsafeFromString(url)
   private def toRequest(url: String, method: Method) = Request(uri = toUri(url), method = method)

   // HTTP Verbs
   def Post[T](url: String, t: T)(implicit m: Manifest[T], e: Encoder[T]): Request = toRequest(url, Method.POST).withBody(t.asJson.noSpaces).unsafeRun()
   def Get[T](url: String, t: T)(implicit m: Manifest[T], e: Encoder[T]): Request = toRequest(url, Method.GET).withBody(t.asJson.noSpaces).unsafeRun()
   def Put[T](url: String, t: T)(implicit m: Manifest[T], e: Encoder[T]): Request = toRequest(url, Method.PUT).withBody(t.asJson.noSpaces).unsafeRun()
   def Delete[T](url: String, t: T)(implicit m: Manifest[T], e: Encoder[T]): Request = toRequest(url, Method.DELETE).withBody(t.asJson.noSpaces).unsafeRun()

   val addHeader : (String, String) => Request => Request = (name, value) => (req: Request) => req.putHeaders(Header(name, value))
   val addCookie: (String, String) => Request => Request = (name, value) => (req: Request) => req.putHeaders(org.http4s.headers.Cookie(Cookie(name, value)))

   def responseAs[T](str: String)(implicit m: Manifest[T], d: Decoder[T]) : T = decode[T](str) match {
      case Left(error) => throw error
      case Right(t) => t
   }

   val check : (WebTestKitResponse => Unit) => Request => Unit = (f) => (req: Request) => {
      val response = client.fetch[WebTestKitResponse](req) {
         case Successful(resp) => resp.as[String].map(b => WebTestKitResponse(StatusCodes.fromInt(200), b))
         case fail => fail.as[String].map(f => WebTestKitResponse(StatusCodes.fromInt(fail.status.code), f))
      }.unsafeRun()
      f(response)
   }
}