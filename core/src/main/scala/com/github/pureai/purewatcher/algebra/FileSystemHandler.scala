package com.github.spread0x.better_files_cats.algebra

import better.files.File
import com.github.spread0x.better_files_cats.config.Folder

trait FileSystemHandler[F[_]] {

  /** Creates the directory if It does not exist.
    *
    * @param dir the directory to create.
    * @return is the result of computations.
    */
  def createDirectory(dir: Folder): F[File]

  /** Get list of files contained in a directory.
    *
    * @param dir is the directory watched.
    * @return is the result of computations.
    */
  def listFiles(dir: Folder): F[List[File]]

  /** Moves a file into the specified destination, using the given file.
    *
    * @param file        is the file to move.
    * @param destination is the directory name of the destination.
    * @return is the result of computations.
    */
  def moveFile(file: File, destination: Folder): F[File]

  /** Reads content in a file.
    *
    * @param file is the file to read from.
    * @return is the result of computations.
    */
  def readTextFromFile(file: File): F[Stream[String]]

  /**
    * Parse an input into an output.
    *
    * @param i input
    * @param SD
    * @return output
    */
  def parseLine[I, O](i: I)(implicit SD: SerDe[I, O, F]): F[O]
}

object FileSystemHandler {
  def apply[F[_]](implicit F: FileSystemHandler[F]): FileSystemHandler[F] = F
}
