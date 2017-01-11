package controllers

import java.security.SecureRandom
import javax.inject.Inject

import com.google.inject.Singleton
import org.cryptonode.jncryptor.AES256JNCryptor
import play.api.mvc.{Action, Controller}
import services.SubscriptionService

/**
  * Created by toby on 2017-01-11.
  */
@Singleton
class AccountController  @Inject() (val configuration: play.api.Configuration, val subcriptionSvc: SubscriptionService) extends Controller {
  private val gaTrackingId = configuration.getString("google.analytics.trackingId")
  private val secretKey = configuration.getString("play.crypto.secret").get
  private val cryptor = new AES256JNCryptor()

  def measurements = Action { implicit request =>


    val oAccountId = request.getQueryString("accountKey").map(hex2bytes).map { k =>
      cryptor.decryptData(k, secretKey.toCharArray)
    }.map(_.toString)

    oAccountId match {
      case None => InternalServerError("Failed to recognize accountKey")
      case Some(key) =>{
        Ok(s"Received key: ${key}")
//        Ok(views.html.measurements(gaTrackingId))
      }
    }

  }

  def hex2bytes(hex: String): Array[Byte] = {
    hex.replaceAll("[^0-9A-Fa-f]", "").sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)
  }

    private def newSecretKey(size: Int): Array[Byte] = {
    val buf = new Array[Byte](size)
    new SecureRandom().nextBytes(buf)
    buf
  }

  def sample = Action { implicit request =>
    val data = "test@example.com".getBytes
    val encrypted = cryptor.encryptData(data, secretKey.toCharArray)
    val encryptedHex = encrypted.map("%02X" format _).mkString

    Ok(s"Account Key: $encryptedHex")
  }


}
