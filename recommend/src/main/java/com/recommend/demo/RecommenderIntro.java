package com.recommend.demo;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class RecommenderIntro {

	private RecommenderIntro(){}
	
	public static void main(String[] args) {
		try {
			DataModel dataModel = new FileDataModel(new File("C:\\Users\\zhaojianya\\Desktop\\intro.csv"));
			UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
			Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
			List<RecommendedItem> list = recommender.recommend(1, 2);
			for (RecommendedItem recommendedItem : list) {
				System.out.println(recommendedItem);
			}
			
			//////////////////////////////////////////////////////////////////////
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("localhost");
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dataSource.setDatabaseName("handsome");
			MySQLJDBCDataModel model = new MySQLJDBCDataModel(dataSource, "movie", "user_id", "item_id", "preference", "timestamp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
