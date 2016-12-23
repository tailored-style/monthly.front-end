package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}

/**
  * Created by toby on 2016-12-20.
  */
@Singleton
class SubscribeController @Inject() extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.subscribe())
  }

  def create = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    var formData = request.body
    var name = formData.get("name").map(_.head).getOrElse("Unknown")

    InternalServerError(s"Not Implemented: $name")
  }
}
