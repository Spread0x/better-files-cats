package com.github.spread0x.better_files_cats.interpreter

import better.files.File
import cats.MonadError
import cats.implicits._
import com.github.spread0x.better_files_cats.algebra.{ ActionExecutor, FileProcessor, FileSystemHandler, FileWatcher }
import com.github.spread0x.better_files_cats.config.WatcherEntry
import monix.eval.Task

import scala.concurrent.duration._

trait FileWatcherEffect extends FileWatcher[Task] {

  def watchEntry[Err](
      entry: WatcherEntry,
      fileProcessor: FileProcessor[Task]
  )(implicit FH: FileSystemHandler[Task], AE: ActionExecutor[Task, Err], E: MonadError[Task, Err]): Task[List[Unit]] = {

    def applyActionToFiles(files: List[File]): Task[List[Unit]] =
      files
        .traverse[Task, Unit] { file ⇒
          AE.run(entry.action, file, entry.folder.success, entry.folder.error, fileProcessor)
        }

    FH.listFiles(entry.folder.watching)
      .flatMap(applyActionToFiles)
      .delayExecution(entry.freqInMs.millisecond)
      .restartUntil(_ => true)
      .asyncBoundary
  }
}
