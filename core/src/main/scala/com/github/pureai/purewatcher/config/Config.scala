package com.github.spread0x.better_files_cats.config

import better.files.File
import cats.implicits._
import com.github.spread0x.better_files_cats.model.Action
import pureconfig.error.CannotConvert
import pureconfig.{ ConfigReader, loadConfigOrThrow }
import pureconfig.generic.auto._
import pureconfig.ConfigSource

import scala.util.Try

final case class Folder(f: File) extends AnyVal

final case class Folders(watching: Folder, error: Folder, success: Folder)

final case class WatcherEntry(folder: Folders, freqInMs: Long, action: Action)

final case class Config(watcherEntries: List[WatcherEntry])

object Config {

  implicit val strToFile: ConfigReader[Folder] = ConfigReader.fromString[Folder] { pathAsString =>
    val file = File(pathAsString)
    if (file.exists) Right(Folder(file))
    else
      Try(file.createDirectory()).toEither
        .bimap(_ => CannotConvert(pathAsString, "better.files.File", "File does not exist"), Folder(_))
  }

  def getConfig(): Config = ConfigSource.default.loadOrThrow[Config] //loadConfigOrThrow[Config]
}
