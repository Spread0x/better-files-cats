package com.github.pureai.purewatcher.utils

trait FileTypeConverter[File] {
  def fromBetter(file: better.files.File): File
}

object FileTypeConverter {
  def apply[File](implicit F: FileTypeConverter[File]): FileTypeConverter[File] = F
}
