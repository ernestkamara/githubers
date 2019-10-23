package io.kamara.githubers.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.kamara.githubers.model.User
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class GithuberServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GithuberService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithuberService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }


    @Test
    fun getUser() {
        runBlocking {
            enqueueResponse("user-ernest.json")
            val ernest : User = service.getUser("ernestkamara").body()!!

            val request = mockWebServer.takeRequest()
            assertThat(request.path, `is`("/users/ernestkamara"))

            assertThat(ernest, IsNull.notNullValue())
            assertThat(ernest.name, `is` ("Ernest Saidu Kamara"))
            assertThat(
                ernest.avatarUrl,
                `is`("https://avatars1.githubusercontent.com/u/502798?v=4")
            )
        }
    }

    @Test
    fun getUsers() {
        runBlocking {
            enqueueResponse("users.json")
            val users = service.getUsers().body()!!

            val request = mockWebServer.takeRequest()
            assertThat(request.path, `is`("/users"))

            val mojombo = users[0]
            assertThat(mojombo.login, `is` ("mojombo"))
        }
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = (inputStream?.source())?.buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        if (source != null) {
            mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
        }
    }

}


