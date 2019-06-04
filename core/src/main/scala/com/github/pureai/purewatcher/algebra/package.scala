package com.github.pureai.purewatcher

import better.files.File
import com.github.pureai.purewatcher.interpreter.{ ActionExecutorEffect, FileSystemHandlerEffect, FileWatcherEffect }
import com.github.pureai.purewatcher.utils.FileTypeConverter
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
