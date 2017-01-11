package controllers

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.mvc.{Action, Controller}
import services.{EncryptionService, SubscriptionService}

/**
  * Created by toby on 2017-01-11.
  */
@Singleton
class AccountController  @Inject() (
                                     val configuration: play.api.Configuration,
                                     val subcriptionSvc: SubscriptionService,
                                     val encryptSvc: EncryptionService
                                   ) extends Controller {
  private val gaTrackingId = configuration.getString("google.analytics.trackingId")

  def measurements = Action { implicit request =>
    val oAccountId = request.getQueryString("accountKey").map(encryptSvc.decrypt)

    oAccountId match {
      case None => InternalServerError("Failed to recognize accountKey")
      case Some(key) =>{
        Ok(s"Received key: $key")
//        Ok(views.html.measurements(gaTrackingId))
      }
    }

  }

  def sample = Action { implicit request =>
    val key = encryptSvc.encrypt("test@example.com")

    Ok(s"Account Key: $key")
  }


}
