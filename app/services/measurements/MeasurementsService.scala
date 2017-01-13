package services.measurements

object MeasurementsService {
  case class Measurements(
                         height: Int,
                         weight: Int
                         )
}

class MeasurementsService {
  def update(profileId: String, measurements: MeasurementsService.Measurements): Unit = {
    // TODO
  }
}
