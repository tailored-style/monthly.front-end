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
class SubscribeController @Inject() (val configuration: play.api.Configuration) extends Controller {

  def index = Action { implicit request =>
    val stripePublicKey = configuration.getString("stripe.publicKey")
    Ok(views.html.subscribe(stripePublicKey.get))
  }

  def create = Action.async(parse.tolerantFormUrlEncoded) { implicit request =>
    val formData = request.body
    val oName = formData.get("name").map(_.head)
    val oEmail = formData.get("email").map(_.head)
    val oSize = formData.get("shirt-size").map(_.head)
    val oSms = formData.get("sms-number").map(_.head)
    val oAddressName = formData.get("address-name").map(_.head)
    val oAddressLine1 = formData.get("address-line-1").map(_.head)
    val oAddressLine2 = formData.get("address-line-2").map(_.head)
    val oAddressCity = formData.get("address-city").map(_.head)
    val oAddressProvince = formData.get("address-province").map(_.head)
    val oAddressPostalCode = formData.get("address-postal-code").map(_.head)
    val oAddressCountry = formData.get("address-country").map(_.head)
    val oStripeToken = formData.get("stripeToken").map(_.head)
    val oStripeEmail = formData.get("stripeEmail").map(_.head)

    if (oName.isEmpty) {
      Future.successful(BadRequest("Name must be provided"))
    } else if (oEmail.isEmpty) {
      Future.successful(BadRequest("Email must be provided"))
    } else if (oSize.isEmpty) {
      Future.successful(BadRequest("Size must be provided"))
    } else if (oAddressName.isEmpty) {
      Future.successful(BadRequest("Address Name must be provided"))
    } else if (oAddressLine1.isEmpty) {
      Future.successful(BadRequest("Address Line 1 must be provided"))
    } else if (oAddressCity.isEmpty) {
      Future.successful(BadRequest("City must be provided"))
    } else if (oAddressProvince.isEmpty) {
      Future.successful(BadRequest("Province must be provided"))
    } else if (oAddressPostalCode.isEmpty) {
      Future.successful(BadRequest("Postal Code must be provided"))
    } else if (oStripeToken.isEmpty) {
      Future.successful(BadRequest("Payment must be provided"))
    } else {
      val f = SubscriptionService.create(
        name = oName.get,
        size = oSize.get,
        email = oEmail.get,
        smsNumber = oSms,
        addressName = oAddressName.get,
        addressLine1 = oAddressLine1.get,
        addressLine2 = oAddressLine2,
        addressCity = oAddressCity.get,
        addressProvince = oAddressProvince.get,
        addressPostalCode = oAddressPostalCode.get,
        addressCountry = oAddressCountry.getOrElse("CA"),
        stripeToken = oStripeToken.get
      )

      f.map { _ =>
        Ok(s"Subscription created: ${oName.getOrElse("Unknown")}")
      }
    }
  }
}
