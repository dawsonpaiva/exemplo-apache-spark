package org.example

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object ReadCSV {

  def main (arg: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("Apache Spark: Read CSV").setMaster("local[2]")
    val spark = SparkSession
      .builder
      .config(sparkConf)
      .getOrCreate()

    // Leitura do arquivo
    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("C:\\Users\\dawso\\Documents\\cursos\\ufms\\sistemas-informacao\\8sem\\comp-dist\\spark\\BankChurners.csv")

    df.show(10)

    df.createOrReplaceTempView("cliente_cartao_credito")

    // Leitura de dados da tabela criada
    val df1 = spark.sql("SELECT * FROM cliente_cartao_credito")
    df1.show()

    val df2 = spark.sql("SELECT " +
      " c.Education_Level" +
      " , count(1) qtd " +
      " FROM cliente_cartao_credito c " +
      " group by c.Education_Level " +
      " order by count(1) desc ")

    df2.show()

  }

}
