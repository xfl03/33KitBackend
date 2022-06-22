package me.xfl03.kit.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
class PjskControllerTests {
    @Autowired(required = false)
    lateinit var mockMvc: MockMvc

    @Test
    fun downloadSuccess() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/pd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "filename": "test.apk",
                        "token": "10000000-aaaa-bbbb-cccc-000000000001"
                    }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun downloadFilenameFail1() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/pd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "filename": "test",
                        "token": "10000000-aaaa-bbbb-cccc-000000000001"
                    }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun downloadFilenameFail2() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/pd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "filename": "/test/ftest.apk",
                        "token": "10000000-aaaa-bbbb-cccc-000000000001"
                    }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun downloadTokenFail() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/pd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "filename": "test.apk",
                        "token": "10000000-aaaa-bbbb-cccc-000000000003"
                    }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun index() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/pi")
        ).andExpect(MockMvcResultMatchers.status().isTemporaryRedirect)
    }
}