package services

sealed class Size(val label: String) extends AnyVal

class Sizes {
  val extraSmall = new Size("XS")
  val small = new Size("S")
  val medium = new Size("M")
  val large = new Size("L")
  val extraLarge = new Size("XL")
}

class SubscriptionService {
  def create(name: String, size: Size, email: String): Unit = {

  }
}
