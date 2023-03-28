package me.xfl03.kit.controller

import me.xfl03.kit.config.pjskDownloadFile
import me.xfl03.kit.config.pjskPredictFile
import me.xfl03.kit.exception.BadRequestException
import me.xfl03.kit.exception.ForbiddenException
import me.xfl03.kit.request.DownloadRequest
import me.xfl03.kit.response.DownloadResponse
import me.xfl03.kit.service.CaptchaService
import me.xfl03.kit.service.CdnService
import me.xfl03.kit.util.isMobilePackageFile
import me.xfl03.kit.util.isNaturalNumber
import me.xfl03.kit.util.isPathContainsDir
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletResponse

@RestController
class PjskController {
    @Autowired
    lateinit var captchaService: CaptchaService

    @Autowired
    lateinit var cdnService: CdnService

    @PostMapping("/pd")
    fun download(response: HttpServletResponse, @RequestBody downloadRequest: DownloadRequest): DownloadResponse {
        val name = downloadRequest.filename
        if (isPathContainsDir(name) || !isMobilePackageFile(name)) {
            throw ForbiddenException("Filename not allowed")
        }
        if (downloadRequest.token == null && downloadRequest.recaptchaToken == null) {
            throw ForbiddenException("Captcha needed")
        }
        if (downloadRequest.recaptchaToken != null) {
            val recaptchaResult = captchaService.checkRecaptchaV3(downloadRequest.recaptchaToken)
            if (!recaptchaResult.first) {
                throw ForbiddenException("Recaptcha not success with score ${recaptchaResult.second}")
            }
        }
        if (downloadRequest.token != null && !captchaService.checkHcaptcha(downloadRequest.token)) {
            throw ForbiddenException("Hcaptcha not success")
        }

        return DownloadResponse(cdnService.getAliyunCdnUrl(name))
    }

    @GetMapping("/pi")
    fun index(): RedirectView {
        return cdnService.redirectTo(pjskDownloadFile)
    }

    @GetMapping("/pred")
    fun predict(): RedirectView {
        return cdnService.redirectTo(pjskPredictFile)
    }

    @GetMapping("/cheer-pred")
    fun cheerfulPredict(): RedirectView {
        return cdnService.redirectTo("cheerful_predict.json")
    }

    @GetMapping("/final")
    fun listEventFinal(): RedirectView {
        return cdnService.redirectTo("final-sample.json")
    }

    @GetMapping("/final/{eventId}")
    fun eventFinal(@PathVariable eventId: String): RedirectView {
        if (!isNaturalNumber(eventId)) {
            throw BadRequestException("Event id not correct")
        }
        return cdnService.redirectTo("final-sample/${eventId}.json")
    }

    @GetMapping("/user/{userId}")
    fun listUserEvent(@PathVariable userId: String): RedirectView {
        if (!isNaturalNumber(userId)) {
            throw BadRequestException("User id not correct")
        }
        return cdnService.redirectTo("user-data/${userId}.json")
    }

    @GetMapping("/user/{userId}/{eventId}")
    fun userEvent(@PathVariable userId: String, @PathVariable eventId: String): RedirectView {
        if (!isNaturalNumber(userId)) {
            throw BadRequestException("User id not correct")
        }
        if (!isNaturalNumber(eventId)) {
            throw BadRequestException("Event id not correct")
        }
        return cdnService.redirectTo("user-data/${userId}/${eventId}.json")
    }

    @GetMapping("/event/{eventId}/{rank}")
    fun eventRank(@PathVariable eventId: String, @PathVariable rank: String): RedirectView {
        if (!isNaturalNumber(eventId)) {
            throw BadRequestException("Event id not correct")
        }
        if (!isNaturalNumber(rank)) {
            throw BadRequestException("Rank not correct")
        }
        return cdnService.redirectTo("event-data/${eventId}/${rank}.json")
    }

    @GetMapping("/cheerful")
    fun listCheerful(): RedirectView {
        return cdnService.redirectTo("cheerful-sample.json")
    }

    @GetMapping("/cheerful/{eventId}")
    fun cheerful(@PathVariable eventId: String): RedirectView {
        if (!isNaturalNumber(eventId)) {
            throw BadRequestException("Event id not correct")
        }
        return cdnService.redirectTo("cheerful-sample/${eventId}.json")
    }

    @GetMapping("/music-meta")
    fun getMusicMeta(): RedirectView {
        return cdnService.redirectTo("music_metas.json")
    }
}