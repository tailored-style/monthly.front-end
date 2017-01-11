package services

import java.nio.charset.Charset
import java.util.Base64
import javax.inject.{Inject, Singleton}

import org.cryptonode.jncryptor.AES256JNCryptor

@Singleton
class EncryptionService @Inject() (val configuration: play.api.Configuration) {
  private val secretKey = configuration.getString("play.crypto.secret").get
  private val cryptor = new AES256JNCryptor()
  private val charset = Charset.forName("UTF-8")

  def encrypt(value: String): String = {
    val data = value.getBytes(charset)
    val encrypted = cryptor.encryptData(data, secretKey.toCharArray)
    Base64.getUrlEncoder.encodeToString(encrypted)
  }

  def decrypt(enc: String): String = {
    val bytes = Base64.getUrlDecoder.decode(enc)
    val decrypted = cryptor.decryptData(bytes, secretKey.toCharArray)
    new String(decrypted, charset)
  }
}
