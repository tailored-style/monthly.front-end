package controllers

import javax.inject._

import play.api.mvc._
import services.integration.IntegrationConfigsService
import services.integration.IntegrationConfigsService.GOOGLE_ANALYTICS_TRACKING_ID

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (val integration: IntegrationConfigsService) extends Controller {
  private val gaTrackingId = integration.getString(GOOGLE_ANALYTICS_TRACKING_ID)

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index(gaTrackingId))
  }

}
