package helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors

class App : RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val headers = mapOf("Content-Type" to "application/json")
        val response = APIGatewayV2HTTPResponse.builder().withHeaders(headers)

        return try {
            val pageContents = getPageContents("https://checkip.amazonaws.com")
            val output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents)
            response.withStatusCode(200)
                    .withBody(output)
                    .build()
        } catch (e: Exception) {
            context.logger.log(e.stackTraceToString())
            response.withBody("{}")
                    .withStatusCode(500)
                    .build()
        }
    }

    @Throws(IOException::class)
    private fun getPageContents(address: String): String {
        val url = URL(address)
        BufferedReader(InputStreamReader(url.openStream())).use { br ->
            return br.lines().collect(Collectors.joining(System.lineSeparator()))
        }
    }
}