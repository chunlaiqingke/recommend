package com.recommend.frontend.controller;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Controller
@RequestMapping("recommend")
public class RecommendController {

	@RequestMapping("")
	@ResponseBody
	public String getRecommend(){
		userBasedRecommender(1L, 0);
		return "ok";
	}
	
	private List<RecommendedItem> userBasedRecommender(long userId, int size){
		try {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("localhost");
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dataSource.setDatabaseName("handsome");
			MySQLJDBCDataModel model = new MySQLJDBCDataModel(dataSource, "movie", "user_id", "item_id", "preference", "timestamp");
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
			CachingRecommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));
			List<RecommendedItem> list = recommender.recommend(userId, size);
			return list;
		} catch (TasteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
