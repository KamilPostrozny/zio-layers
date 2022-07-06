package pl.postrozny

import java.io.IOException

import zio.*

trait UserEmailer:
  def notify(user: User, message: String): ZIO[Console, IOException, Unit]

object UserEmailer:
  lazy val live: ULayer[UserEmailer] =
    ZLayer.succeed(
      new:
        override def notify(user: User, message: String): ZIO[Console, IOException, Unit] =
          ZIO.serviceWithZIO(_.printLine(s"[User emailer] Sending '$message' to ${user.email}"))
    )
