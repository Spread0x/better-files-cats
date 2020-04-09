package com.github.spread0x.better_files_cats.interpreter

import better.files.File
import com.github.spread0x.better_files_cats.algebra.{ FileSystemHandler, SerDe }
import com.github.spread0x.better_files_cats.config.Folder
import monix.eval.Task

trait FileSystemHandlerEffect extends FileSystemHandler[Task] {

  override def createDirectory(dir: Folder): Task[File] =
    Task.delay(dir.f.createIfNotExists(asDirectory = true))

  override def listFiles(dir: Folder): Task[List[File]] =
    Task.delay(dir.f.list.filter(!_.isDirectory).toList)

  override def moveFile(file: File, destination: Folder): Task[File] =
    Task.delay(file.moveToDirectory(destination.f))

  override def readTextFromFile(file: File): Task[Stream[String]] =
    Task.delay(file.lines.toStream)

  override def parseLine[I, O](i: I)(implicit SD: SerDe[I, O, Task]): Task[O] =
    SD.serDe(i)
}
