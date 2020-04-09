package com.github.spread0x.better_files_cats.algebra

import cats.MonadError
import com.github.spread0x.better_files_cats.config.WatcherEntry

trait FileWatcher[F[_]] {

  /** Watches a folder and perform specific actions on files.
    *
    * @param watchEntries  is the path of the folders to watch.
    * @param fileProcessor is a function representing what to do on a file depending on the associated watcher action.
    * @tparam F   is a type of effect wrapper.
    * @tparam Err is a type of error.
    * @return is the result of computations.
    */
  def run[Err](
      watchEntries: List[WatcherEntry],
      fileProcessor: FileProcessor[F]
  )(implicit FH: FileSystemHandler[F], AE: ActionExecutor[F, Err], E: MonadError[F, Err]): List[F[List[Unit]]] =
    watchEntries.map(watchEntry(_, fileProcessor))

  /** Watches files for a given entry (element in the configuration file).
    *
    * @param entry         is the element in the config.
    * @param fileProcessor is a function representing what to do on a file depending on the associated watcher action.
    * @param FH            type class constraint: F should be an instance of FileSystemHandler.
    * @param AE            type class constraint: F should be an instance of ActionExecutor.
    * @param E             constraint of F ensuring that the effect used is able to raise errors.
    * @tparam Err is a type of Error.
    * @return is the result of computations.
    */
  def watchEntry[Err](
      entry: WatcherEntry,
      fileProcessor: FileProcessor[F]
  )(implicit FH: FileSystemHandler[F], AE: ActionExecutor[F, Err], E: MonadError[F, Err]): F[List[Unit]]
}

object FileWatcher {
  def apply[F[_]](implicit F: FileWatcher[F]): FileWatcher[F] = F
}
