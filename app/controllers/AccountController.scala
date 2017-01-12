package controllers

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.mvc.{Action, Controller}
import services.EncryptionService
import services.integration.IntegrationConfigsService
import services.integration.IntegrationConfigsService.GOOGLE_ANALYTICS_TRACKING_ID
import views.Context

@Singleton
class AccountController @Inject() (
                                     val integrations: IntegrationConfigsService,
                                     val encryptSvc: EncryptionService
                                   ) extends Controller {
  private val gaTrackingId = integrations.getString(GOOGLE_ANALYTICS_TRACKING_ID)
  implicit private val viewContext = Context(gaTrackingId)

  def measurements = Action { implicit request =>
    val oAccountKey = request.getQueryString("accountKey")

    oAccountKey match {
      case None => BadRequest("Could not find accountKey. Required param.")
      case Some(_) => Ok(views.html.measurements())
    }
  }


}
