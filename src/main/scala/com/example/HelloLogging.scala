package com.example

import com.typesafe.scalalogging.slf4j.Logging

object HelloLogging extends Logging {
  def main (args: Array[String]) {
    logger.debug("debugging here")
    logger.info("hello world")
    logger.error("opps")
  }
}