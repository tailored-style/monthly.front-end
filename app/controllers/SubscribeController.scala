package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller, Result}
import services.SubscriptionService
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

/**
  * Created by toby on 2016-12-20.
  */
@Singleton
class SubscribeController @Inject() extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.subscribe())
  }

  def create = Action.async(parse.tolerantFormUrlEncoded) { implicit request =>
    val formData = request.body
    val oName = formData.get("name").map(_.head)
    val oEmail = formData.get("email").map(_.head)
    val oSize = formData.get("shirt-size").map(_.head)

    if (oName.isEmpty) {
      Future.successful(BadRequest("Name must be provided"))
    } else if (oEmail.isEmpty) {
      Future.successful(BadRequest("Email must be provided"))
    } else if (oSize.isEmpty) {
      Future.successful(BadRequest("Size must be provided"))
    } else {
      val f = SubscriptionService.create(oName.get, oSize.get, oEmail.get)

      f.map { _ =>
        Ok(s"Subscription created: ${oName.getOrElse("Unknown")}")
      }
    }
  }
}
