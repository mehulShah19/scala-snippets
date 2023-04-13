package com.apple.fear.fraudservice.chronosrt.client.okhttp.circuitbreaker

import okhttp3.mockwebserver.{MockResponse, MockWebServer}
import okhttp3.{OkHttpClient, Request}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}

@RunWith(classOf[JUnitRunner])
class DummyMockWebServer extends FunSuite with BeforeAndAfterEach {

  var server: MockWebServer = _

  // Declare a new OkHttpClient instance as a class variable
  var client: OkHttpClient = _

  override def beforeEach(): Unit = {
    // Before each test, create a new MockWebServer instance and start it
    server = new MockWebServer()
    //server.shutdown()
    server.url("https://www.dummy.com/")
    //    server.start()

    // Create a new OkHttpClient instance with the MockWebServer as the base URL
    client = new OkHttpClient.Builder().build()

  }
  override def afterEach(): Unit = {
    // After each test, stop the MockWebServer instance
    server.shutdown()
  }

  test("Running the sample") {
    // Create a new MockResponse with a JSON body
    val body = "{\"name\": \"John\", \"age\": 30}"
    val response = new MockResponse()
      .setResponseCode(200)
      .setBody(body)

    // Enqueue the MockResponse on the MockWebServer
    server.enqueue(response)

    // Create a new GET request
    val request = new Request.Builder()
      .url(server.url("/user"))
      .build()

    // Execute the request using the OkHttpCl
    // ient
    val httpResponse = client.newCall(request).execute()

    // Assert that the response was successful and has the expected body
    assert(httpResponse.isSuccessful)
    assert(httpResponse.body.string() == body)
  }
}
