package services

import java.util.UUID
import javax.inject._

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.amazonaws.services.sns.{AmazonSNS, AmazonSNSClientBuilder}
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import services.integration.IntegrationConfigsService
import services.integration.IntegrationConfigsService._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class SubscriptionService @Inject() (
                                      val integrations: IntegrationConfigsService
                                    ) {
  private val tableName = integrations.getString(AWS_DYNAMODB_SUBSCRIPTIONS_TABLE_NAME).get
  private val awsRegion = Regions.fromName(integrations.getString(AWS_REGION).get)
  private val snsTopic = integrations.getString(AWS_SNS_TOPICS_SUBSCRIPTION_CREATED).get

  case class Subscription(
                            id: String,
                            name: String,
                            size: Option[String],
                            email: String,
                            smsNumber: Option[String],
                            address: Address,
                            stripeToken: String,
                            signupDate: DateTime
                            )

  case class Address(
                       fullName: String,
                       line1: String,
                       line2: Option[String],
                       city: String,
                       province: String,
                       postalCode: String,
                       country: String
                       )

  def create(
              name: String,
              size: Option[String],
              email: String,
              smsNumber: Option[String],
              addressName: String,
              addressLine1: String,
              addressLine2: Option[String],
              addressCity: String,
              addressProvince: String,
              addressPostalCode: String,
              addressCountry: String,
              stripeToken: String
            )(implicit executionContext: ExecutionContext): Future[Subscription] = {

    val address = Address(
      fullName = addressName,
      line1 = addressLine1,
      line2 = addressLine2,
      city = addressCity,
      province = addressProvince,
      postalCode = addressPostalCode,
      country = addressCountry
    )
    val subscription = Subscription(
      id = UUID.randomUUID().toString,
      name = name,
      size = size,
      email = email,
      smsNumber = smsNumber,
      address = address,
      stripeToken = stripeToken,
      signupDate = DateTime.now(DateTimeZone.UTC)
    )

    val fDynamoDBWrite = Future {
      createDynamoDbRecord(subscription)
      subscription
    }

    fDynamoDBWrite.onSuccess {
      case sub: Subscription => notifySnsTopic(sub)
    }

    fDynamoDBWrite
  }

  private val dynamoDbClient: AmazonDynamoDB = {
    AmazonDynamoDBClientBuilder.standard()
        .withRegion(awsRegion)
        .build()
  }

  private val snsClient: AmazonSNS = {
    AmazonSNSClientBuilder.standard()
        .withRegion(awsRegion)
        .build()
  }

  private def createDynamoDbRecord(subscription: Subscription): Unit = {
    val item: java.util.Map[String, AttributeValue] = new java.util.HashMap[String, AttributeValue]()
    item.put("ID", new AttributeValue(subscription.id))
    item.put("Name", new AttributeValue(subscription.name))
    item.put("Email", new AttributeValue(subscription.email))
    if (subscription.size.isDefined) {
      item.put("Size", new AttributeValue(subscription.size.get))
    }
    if (subscription.smsNumber.isDefined) {
      item.put("SMS", new AttributeValue(subscription.smsNumber.get))
    }
    item.put("AddressName", new AttributeValue(subscription.address.fullName))
    item.put("AddressLine1", new AttributeValue(subscription.address.line1))
    if (subscription.address.line2.isDefined) {
      item.put("AddressLine2", new AttributeValue(subscription.address.line2.get))
    }
    item.put("AddressCity", new AttributeValue(subscription.address.city))
    item.put("AddressProvince", new AttributeValue(subscription.address.province))
    item.put("AddressPostalCode", new AttributeValue(subscription.address.postalCode))
    item.put("AddressCountry", new AttributeValue(subscription.address.country))
    item.put("StripeToken", new AttributeValue(subscription.stripeToken))

    val signupDate = subscription.signupDate
    val fmt = ISODateTimeFormat.dateTimeNoMillis()
    item.put("SignupDate", new AttributeValue(fmt.print(signupDate)))

    dynamoDbClient.putItem(tableName, item)
  }

  private def notifySnsTopic(subscription: Subscription): Unit = {
    val content =
      s"""
        |{
        |  "subscription": {
        |    "id": "${subscription.id}"
        |  }
        |}
      """.stripMargin
    snsClient.publish(snsTopic, content)
  }
}
