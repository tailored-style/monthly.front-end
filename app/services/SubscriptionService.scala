package services

import java.util.UUID

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

import scala.concurrent.{ExecutionContext, Future}


object SubscriptionService {
  private val tableName = "tailored.monthly.subscriptions"
  private val dynamoDbRegion = Regions.US_WEST_2

  def create(
              name: String,
              size: String,
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
            )(implicit executionContext: ExecutionContext): Future[Unit] = {
    val item: java.util.Map[String, AttributeValue] = new java.util.HashMap[String, AttributeValue]()
    item.put("ID", new AttributeValue(UUID.randomUUID().toString))
    item.put("Name", new AttributeValue(name))
    item.put("Email", new AttributeValue(email))
    item.put("Size", new AttributeValue(size))
    if (smsNumber.isDefined) {
      item.put("SMS", new AttributeValue(smsNumber.get))
    }
    item.put("AddressName", new AttributeValue(addressName))
    item.put("AddressLine1", new AttributeValue(addressLine1))
    item.put("AddressLine2", new AttributeValue(addressLine2.getOrElse("")))
    item.put("AddressCity", new AttributeValue(addressCity))
    item.put("AddressProvince", new AttributeValue(addressProvince))
    item.put("AddressPostalCode", new AttributeValue(addressPostalCode))
    item.put("AddressCountry", new AttributeValue(addressCountry))
    item.put("StripeToken", new AttributeValue(stripeToken))

    val signupDate = DateTime.now(DateTimeZone.UTC)
    val fmt = ISODateTimeFormat.dateTime()
    item.put("SignupDate", new AttributeValue(fmt.print(signupDate)))

    Future {
      dynamoDbClient.putItem(tableName, item)
    }
  }

  private var dynamoDbClient: AmazonDynamoDB = {
    AmazonDynamoDBClientBuilder.standard()
        .withRegion(dynamoDbRegion)
        .build()
  }
}
