package Utils;

public class Constants {
	public static final String TRAIN_CSV_PATH = "./train.csv";
	public static final String TEST_CSV_PATH = "./test.csv";
	public static final String SUBMISSION_CSV_PATH = "./submission.csv";

	public static final String TRAIN_ARFF_PATH = "./weka/train_prev_hour.arff";
	public static final String TRAIN_ARFF_PATH_CASUAL = "./weka/train_prev_hour_casual.arff";
	public static final String TRAIN_ARFF_PATH_REGISTERED = "./weka/train_prev_hour_registered.arff";
	public static final String TEST_ARFF_PATH = "./weka/test_prev_hour.arff";

	public static final String CLASSIFIER_PATH = "./weka/trees.m5p-prev_hour.model";
	public static final String CLASSIFIER_PATH_REGISTERED = "./weka/trees.m5p-prev_hour_reg.model";
	public static final String CLASSIFIER_PATH_CASUAL = "./weka/trees.m5p-prev_hour_cas.model";
}
