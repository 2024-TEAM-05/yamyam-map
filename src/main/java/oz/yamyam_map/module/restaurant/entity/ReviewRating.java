package oz.yamyam_map.module.restaurant.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewRating {

	private Long totalReviews;
	private Long totalScore;
	private double averageScore;

	// JPA를 적용하기 위해 기본 생성자 추가
	protected ReviewRating() {
		this.totalReviews = 0L;
		this.totalScore = 0L;
		this.averageScore = 0.0;
	}

	public ReviewRating(Long totalReviews, Long totalScore) {
		this.totalReviews = totalReviews;
		this.totalScore = totalScore;
		this.averageScore = totalReviews > 0 ? (double)totalScore / totalReviews : 0.0;
	}

	public void addReview(int newScore) {
		totalReviews++;
		totalScore += newScore;
		calculateNewReviewRating();
	}

	private void calculateNewReviewRating() {
		averageScore = (double)totalScore / totalReviews;
	}

}

