package services.integration

import javax.inject.{Inject, Singleton}

import services.integration.IntegrationConfigsService.ConfigKey


object IntegrationConfigsService {
  sealed case class ConfigKey(private[services] val key: String) extends AnyVal

  val STRIPE_PUBLIC_KEY = ConfigKey("stripe.publicKey")
  val GOOGLE_ANALYTICS_TRACKING_ID = ConfigKey("google.analytics.trackingId")
  val AWS_DYNAMODB_SUBSCRIPTIONS_TABLE_NAME = ConfigKey("aws.dynamodb.subscriptionTable")
  val AWS_REGION = ConfigKey("aws.region")
  val AWS_SNS_TOPICS_SUBSCRIPTION_CREATED = ConfigKey("aws.sns.topics.subscriptionCreated")
}


@Singleton
class IntegrationConfigsService @Inject() (val configuration: play.api.Configuration) {

  def getString(key: ConfigKey ): Option[String] = configuration.getString(key.key)
}
