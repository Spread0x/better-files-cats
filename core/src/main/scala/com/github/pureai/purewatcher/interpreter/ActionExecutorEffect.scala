package com.github.pureai.purewatcher.interpreter

import better.files.File
import cats.implicits._
import com.github.pureai.purewatcher.algebra.{ ActionExecutor, FileSystemHandler }
import com.github.pureai.purewatcher.config.Folder
import com.typesafe.scalalogging.LazyLogging
import monix.eval.Task

trait ActionExecutorEffect extends ActionExecutor[Task, Throwable] with LazyLogging {

  def moveTo(file: File, folder: Folder)(implicit FH: FileSystemHandler[Task]): Task[Unit] =
    Task.delay(logger.info(s"move $file to $folder")) <* FH.moveFile(file, folder)

  def onSuccess(file: File, successFolder: Folder)(implicit FH: FileSystemHandler[Task]): Task[Unit] =
    moveTo(file, successFolder)

  def onFailure(file: File, errorFolder: Folder, e: Throwable)(implicit FH: FileSystemHandler[Task]): Task[Unit] =
    moveTo(file, errorFolder)
}
