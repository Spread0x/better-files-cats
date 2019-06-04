package com.github.pureai.purewatcher.algebra

import better.files.File
import com.github.pureai.purewatcher.model.Action
import com.github.pureai.purewatcher.utils.FileTypeConverter

final class FileProcessor[F[_]] private (val run: (Action, File) => F[Unit])

object FileProcessor {

  /** Apply a defined action on a specific file's type.
    *
    * @param userDefinedActions is the defined action.
    * @tparam F        is a type of effect wrapper.
    * @tparam FileType is the type of File.
    * @return is the result of an action.
    */
  def apply[F[_], FileType: FileTypeConverter](userDefinedActions: (Action, FileType) => F[Unit]): FileProcessor[F] =
    new FileProcessor[F]((action: Action, file: File) => {
      val fileUserFormat: FileType = FileTypeConverter[FileType].fromBetter(file)
      userDefinedActions(action, fileUserFormat)
    })
}
