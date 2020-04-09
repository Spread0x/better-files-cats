package com.github.spread0x.better_files_cats.utils

trait FileTypeConverter[File] {
  def fromBetter(file: better.files.File): File
}

object FileTypeConverter {
  def apply[File](implicit F: FileTypeConverter[File]): FileTypeConverter[File] = F
}
