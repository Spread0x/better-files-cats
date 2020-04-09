package com.github.spread0x.better_files_cats

import better.files.File
import com.github.spread0x.better_files_cats.interpreter.{ ActionExecutorEffect, FileSystemHandlerEffect, FileWatcherEffect }
import com.github.spread0x.better_files_cats.utils.FileTypeConverter
import monix.eval.Task

package object algebra {

  object implicits {
    implicit val ws: FileWatcher[Task]               = new FileWatcherEffect {}
    implicit val fh: FileSystemHandler[Task]         = new FileSystemHandlerEffect {}
    implicit val ae: ActionExecutor[Task, Throwable] = new ActionExecutorEffect {}
    implicit val betterFile: FileTypeConverter[File] = new FileTypeConverter[File] {
      override def fromBetter(file: File): File = file
    }
    implicit val javaIOFile: FileTypeConverter[java.io.File] = new FileTypeConverter[java.io.File] {
      override def fromBetter(file: File): java.io.File = file.toJava
    }
  }

}
