public class Program {

	public static void main(String[] args) throws Exception {
		System.out.println("Set train instances");
		Trainer.setTrainInstances();

		System.out.println("Generate classifier");
		Trainer.generateClassifier();

		System.out.println("Set test instances");
		Trainer.setTestInstances();

		System.out.println("Classify test instances");
		Trainer.classifyTestInstances();

		System.out.println("Write submission");
		Trainer.writeSubmissionResults();
	}

}
