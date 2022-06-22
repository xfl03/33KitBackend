package me.xfl03.kit.controller

import me.xfl03.kit.config.pjskDownloadFile
import me.xfl03.kit.exception.ForbiddenException
import me.xfl03.kit.request.DownloadRequest
import me.xfl03.kit.response.DownloadResponse
import me.xfl03.kit.service.CaptchaService
import me.xfl03.kit.service.CdnService
import me.xfl03.kit.util.isMobilePackageFile
import me.xfl03.kit.util.isPathContainsDir
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
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
        val ret = RedirectView(cdnService.getAliyunCdnUrl(pjskDownloadFile))
        ret.setStatusCode(HttpStatus.TEMPORARY_REDIRECT)
        return ret
    }
}