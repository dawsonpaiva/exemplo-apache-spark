package org.example

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object WordCount {

  def main (arg: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("Apache Spark: Read CSV").setMaster("local[2]")
    val spark = SparkSession
      .builder
      .config(sparkConf)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    val text = spark.sparkContext.textFile("teste.txt")

    // removendo linhas em branco
    val counts = text.flatMap(line => line.split(" ")).filter(word => !word.trim.equals("")).map(word => (word, 1)).reduceByKey(_ + _)

    print("Word count: " + counts.collect().mkString(", "))

    val wordCount = counts.toDF()

    val sumWord = wordCount.agg(sum("_2")).first.get(0)

    print("Sum word: " + sumWord)

    // com Spark Sql

    wordCount.createOrReplaceTempView("word_count")

    val sumWordSql = spark.sql("select sum(_2) sum_word from word_count").first().get(0)

    print("Sum word Sql: " + sumWordSql)

  }

}
