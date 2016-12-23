package services

import java.util.UUID

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}

import scala.concurrent.{ExecutionContext, Future}


object SubscriptionService {
  private val tableName = "tailored.monthly.subscriptions"

  def create(name: String, size: String, email: String)(implicit executionContext: ExecutionContext): Future[Unit] = {
    val item: java.util.Map[String, AttributeValue] = new java.util.HashMap[String, AttributeValue]()
    item.put("ID", new AttributeValue(UUID.randomUUID().toString))
    item.put("Name", new AttributeValue(name))
    item.put("Email", new AttributeValue(email))
    item.put("Size", new AttributeValue(size))

    Future {
      dynamoDbClient.putItem(tableName, item)
    }
  }

  private var dynamoDbClient: AmazonDynamoDB = {
    AmazonDynamoDBClientBuilder.standard().build()
  }
}
