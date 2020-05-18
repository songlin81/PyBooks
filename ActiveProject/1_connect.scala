import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import spark.implicits._

val rdd = sc.parallelize(Array(1 to 10))
println("parallelize(Array): " + rdd.count())

val seqRDD = sc.parallelize(Seq(1,2,3))
println("parallelize(Seq): " + seqRDD.count())

val txtRDD = sc.textFile("file:///C:/tfs/trainings/Spark/ActiveProject/Readme.md")
println("textFile: " + txtRDD.count())

val counts = txtRDD.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_+_)
println("Number of entries: " + counts.count())
counts.saveAsTextFile("output")

val txtRDD2 = sc.textFile("file:///C:/tfs/trainings/Spark/ActiveProject/Readme.md")
val countSumup = txtRDD2.flatMap(line => line.split(" ")).map(word => (word, 1))
println("Number of words: " + countSumup.count())
val df = countSumup.toDF()
df.write
  .format("com.databricks.spark.csv")
  .option("delimiter", "\t")
  .mode("overwrite")
  .save("output2");

spark.stop()
//sys.exit
