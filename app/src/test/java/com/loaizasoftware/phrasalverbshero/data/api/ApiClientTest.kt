package com.loaizasoftware.phrasalverbshero.data.api

import android.content.Context
import com.loaizasoftware.phrasalverbshero.domain.model.Verb
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApiClientTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var retrofit: Retrofit // ✅ Store Retrofit instance separately
    private val mockContext: Context = mock() // ✅ Mock Context for ChuckerInterceptor

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start() // ✅ Start Fake Server

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Allows Moshi to handle Kotlin classes properly
            .build()

        // ✅ Create Retrofit instance with the mocked server URL
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString()) // ✅ Mock server URL
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // ✅ RxJava2 support
            .build()

        apiService = retrofit.create(ApiService::class.java) // ✅ Extract ApiService from Retrofit
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown() // ✅ Stop Mock Server after tests
    }

    @Test
    fun `test Retrofit is created with correct Base URL`() {
        assertEquals(mockWebServer.url("/").toString(), retrofit.baseUrl().toString()) // ✅ Ensure base URL is correct
    }

    @Test
    fun `test MoshiConverterFactory is added`() {
        assertTrue(retrofit.converterFactories().any { it is MoshiConverterFactory }) // ✅ Moshi exists
    }

    @Test
    fun `test RxJava2CallAdapterFactory is added`() {
        assertTrue(retrofit.callAdapterFactories().any { it is RxJava2CallAdapterFactory }) // ✅ RxJava Adapter exists
    }

    @Test
    fun `test API call returns correct response`() {
        // ✅ Simulated JSON Response from the Fake Server {"id": 1, "phrasalVerb": "go on", "definitions": []}
        val mockResponse = MockResponse()
            .setBody("""[{"id": 1, "verb": "go", "phrasalVerbs": [{"id":1, "phrasalVerb": "go on", "definitions": []}]}]""".trimIndent())
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse) // ✅ Fake API Response

        // ✅ Call API (RxJava `.blockingGet()` forces synchronous execution for testing)

        val verbsListResponse: ArrayList<Verb> = arrayListOf()

        apiService.getVerbsSingle()
            .doOnSuccess {
                verbsListResponse.addAll(it)
            }
            .subscribe()

        // ✅ Assertions
        assertNotNull(verbsListResponse) // Ensure response is not null
        assertEquals(1, verbsListResponse.size) // Ensure only 1 verb is returned
        assertEquals("go", verbsListResponse[0].verb) // Verify verb name
        assertEquals(1, verbsListResponse[0].phrasalVerbs.size) // Ensure 1 phrasal verb exists
        assertEquals("go on", verbsListResponse[0].phrasalVerbs[0].phrasalVerb) // Verify phrasal verb name
        //assertTrue(true)
    }
}
