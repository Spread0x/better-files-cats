package com.github.pureai.purewatcher.algebra

import better.files.File
import cats.MonadError
import cats.implicits._
import com.github.pureai.purewatcher.config.Folder
import com.github.pureai.purewatcher.model.Action

trait ActionExecutor[F[_], Err] {

  /** Execute a defined action on a given file.
    *
    * @param action        is the type of action.
    * @param processedFile is the file to process.
    * @param successFolder is the path to success folder.
    * @param errorFolder   is the path to error folder.
    * @param fileProcessor is a function representing what to do on a file depending on the associated watcher action.
    * @param E             constraint of F ensuring that the effect used is able to raise errors.
    * @return result of the computation.
    */
  def run(
      action: Action,
      processedFile: File,
      successFolder: Folder,
      errorFolder: Folder,
      fileProcessor: FileProcessor[F]
  )(implicit FH: FileSystemHandler[F], E: MonadError[F, Err]): F[Unit] =
    fileProcessor
      .run(action, processedFile)
      .flatMap(_ => onSuccess(processedFile, successFolder))
      .handleErrorWith(onFailure(processedFile, errorFolder, _))

  /** Move a specific file to a specific folder.
    *
    * @param file   is the file to move.
    * @param folder is the path to the target folder.
    * @param FH     type class constraint: F should be an instance of FileSystemHandler.
    * @return result of the computation.
    */
  def moveTo(file: File, folder: Folder)(implicit FH: FileSystemHandler[F]): F[Unit]

  /** Move a file to a specific directory if a Task was successfully executed.
    *
    * @param file          is the file to move.
    * @param successFolder is the path to success folder.
    * @param FH            type class constraint: F should be an instance of FileSystemHandler.
    * @return result of the computation.
    */
  def onSuccess(file: File, successFolder: Folder)(implicit FH: FileSystemHandler[F]): F[Unit]

  /** Move a file to a specific directory if a Task failed.
    *
    * @param file        is the file to move.
    * @param errorFolder is the path to error folder.
    * @param FH          type class constraint: F should be an instance of FileSystemHandler.
    * @return result of the computation.
    */
  def onFailure(file: File, errorFolder: Folder, e: Err)(implicit FH: FileSystemHandler[F]): F[Unit]
}
