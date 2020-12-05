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

    // Leitura do arquivo
    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("C:\\Users\\dawso\\Documents\\cursos\\ufms\\sistemas-informacao\\8sem\\comp-dist\\spark\\BankChurners.csv")

    val text = spark.sparkContext.textFile("C:\\spark\\spark-3.0.1-bin-hadoop2.7\\README.md")

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
