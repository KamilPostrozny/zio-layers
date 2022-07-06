package pl.postrozny

import zio.Console.*
import zio.*

object Main extends ZIOAppDefault:

  override def run: Task[Unit] =
    lazy val env =
      ZLayer.succeed(ConsoleLive) ++ UserEmailer.live ++ UserDb.live ++ ZLayer.succeed(Config("table-x"))

    lazy val kamil = User("Kamil", "kamil@postrozny.pl")

    ZIO
      .serviceWithZIO[UserDb](_.insert(kamil))
      .provideLayer(env)
