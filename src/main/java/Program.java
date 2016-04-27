import Utils.FileHelper;

public class Program {

	public static void main(String[] args) throws Exception {
		System.out.println("Set train rows");
		FileHelper.setTrainRows();

		System.out.println("Set test rows");
		FileHelper.setTestRows();

		System.out.println("Calculate results");
		Trainer.calculateResults();

		System.out.println("Write submission");
		FileHelper.writeSubmissionResults();
	}

}
