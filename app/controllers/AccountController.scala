package controllers

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.mvc.{Action, Controller}
import services.EncryptionService
import services.integration.IntegrationConfigsService
import services.integration.IntegrationConfigsService.GOOGLE_ANALYTICS_TRACKING_ID

@Singleton
class AccountController @Inject() (
                                     val integrations: IntegrationConfigsService,
                                     val encryptSvc: EncryptionService
                                   ) extends Controller {
  private val gaTrackingId = integrations.getString(GOOGLE_ANALYTICS_TRACKING_ID)

  def measurements = Action {
    Ok(views.html.measurements(gaTrackingId))
  }


}
