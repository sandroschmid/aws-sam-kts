package helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AppTest {

    @Mock
    private lateinit var context: Context

    @Test
    fun successfulResponse() {
        val app = App()
        val result = app.handleRequest(APIGatewayV2HTTPEvent(), context)
        assertEquals(result.statusCode, 200)
        assertEquals(result.headers["Content-Type"], "application/json")

        val content = result.body
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"hello world\""))
        assertTrue(content.contains("\"location\""))
    }
}