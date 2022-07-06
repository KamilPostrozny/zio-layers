package pl.postrozny

import java.io.IOException

import zio.*

trait UserDb:
  def insert(user: User): ZIO[Config & Console & UserEmailer, IOException, Unit]

object UserDb:
  lazy val live: ULayer[UserDb] =
    ZLayer.succeed(
      new:
        override def insert(user: User): ZIO[Config & Console & UserEmailer, IOException, Unit] =
          for
            tableName <- ZIO.environmentWith[Config](_.get.tableName)
            _ <-
              ZIO.serviceWithZIO[Console](_.printLine(s"[Database] insert into $tableName values ('${user.email}')"))
            _ <- ZIO.serviceWithZIO[UserEmailer](_.notify(user, "Message"))
          yield ()
    )
