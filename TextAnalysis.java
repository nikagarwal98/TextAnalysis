import java.io.*;
import java.util.*;
import java.util.stream.*; 

class TextAnalysis {

	private File file;


	TextAnalysis(File file) {
		this.file = file;
	}

	int getTotalNumberOfWords() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		int count = 0;
		String currLine = br.readLine();
		while (currLine != null) {
			count += Arrays.stream(currLine.split(" ")).filter(word -> word != null && word.length() > 0)
					.toArray().length;
			currLine = br.readLine();
		}
		return count;
	}

	int getTotalUniqueWords() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		HashSet<String> uniqueWords = new HashSet<>();
		String currLine = br.readLine();
		while (currLine != null) {
			Arrays.stream(currLine.split(" "))
					.filter(word -> word != null && word.replaceAll("[^a-zA-Z0-9]", "").length() > 0 && !uniqueWords.contains(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()))
					.forEach(word -> uniqueWords.add(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()));
			currLine = br.readLine();
		}
		return uniqueWords.size();
	}

	private List<Map.Entry<String, Integer>> getKMostFrequent(HashMap<String, Integer> wordsFrequency, int k) {
		PriorityQueue<Map.Entry<String, Integer>> wordsByFrequency = new PriorityQueue<>(20, Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));
		wordsFrequency.entrySet().stream().forEach(entry -> wordsByFrequency.add(entry));
		ArrayList<Map.Entry<String, Integer>> twentyMostFrequent = new ArrayList<>();
		for (int i = 0; i < k; i++) twentyMostFrequent.add(wordsByFrequency.poll());
		return twentyMostFrequent;
	} 

	List<Map.Entry<String, Integer>> get20MostFrequentWords() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		HashMap<String, Integer> wordsFrequency = new HashMap<>();
		String currLine = br.readLine();
		while (currLine != null) {
			Arrays.stream(currLine.split(" ")).filter(word -> word != null && word.replaceAll("[^a-zA-Z0-9]", "").length() > 0).forEach(word -> wordsFrequency.put(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), wordsFrequency.getOrDefault(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), 0) + 1));
			currLine = br.readLine();
		}
		return getKMostFrequent(wordsFrequency, 20);
	}

	private HashSet<String> getKCommonWords(int k) throws Exception {
		File file = new File("1-1000.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		HashSet<String> commonWords = new HashSet<>();
		String currLine = br.readLine();
		int counter = 0;
		while (currLine != null && counter < k) {
			commonWords.add(currLine.trim());
			currLine = br.readLine();
			counter++;
		}
		return commonWords;
	}

	List<Map.Entry<String, Integer>> get20MostInterestingFrequentWords() throws Exception {
		HashSet<String> commonWords = getKCommonWords(100);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		HashMap<String, Integer> wordsFrequency = new HashMap<>();
		String currLine = br.readLine();
		while (currLine != null) {
			Arrays.stream(currLine.split(" ")).filter(word -> word != null && word.length() > 0 && !commonWords.contains(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase())).forEach(word -> wordsFrequency.put(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), wordsFrequency.getOrDefault(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), 0) + 1));
			currLine = br.readLine();
		}
		return getKMostFrequent(wordsFrequency, 20);
	}

	List<Map.Entry<String, Integer>> get20LeastFrequentWords() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		HashMap<String, Integer> wordsFrequency = new HashMap<>();
		String currLine = br.readLine();
		while (currLine != null) {
			Arrays.stream(currLine.split(" ")).filter(word -> word != null && word.replaceAll("[^a-zA-Z0-9]", "").length() > 0).forEach(word -> wordsFrequency.put(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), wordsFrequency.getOrDefault(word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), 0) + 1));
			currLine = br.readLine();
		}
		PriorityQueue<Map.Entry<String, Integer>> wordsByFrequency = new PriorityQueue<>(20, Comparator.comparing(Map.Entry::getValue));
		wordsFrequency.entrySet().stream().forEach(entry -> wordsByFrequency.add(entry));
		ArrayList<Map.Entry<String, Integer>> twentyLeastFrequent = new ArrayList<>();
		for (int i = 0; i < 20; i++) twentyLeastFrequent.add(wordsByFrequency.poll());
		return twentyLeastFrequent;
	}

	private int getNumChapters() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		int count = 0;
		String currLine = br.readLine();
		while (currLine != null) {
			if (currLine.contains("Chapter")) {
				count++;
			}
			currLine = br.readLine();
		}
		return count;
	}

	int[] getFrequencyOfWord(String input) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		final int numChapters = getNumChapters();
		int[] wordsFrequency = new int[numChapters + 1];
		int currChapter = 0;
		int count = 0;
		String currLine = br.readLine();
		while (currLine != null) {
			if (currLine.contains("Chapter")) {
				wordsFrequency[currChapter] = count;
				currChapter++;
				count = 0;
			}
			count += Arrays.stream(currLine.split(" ")).filter(word -> word != null && word.replaceAll("[^a-zA-Z0-9]", "").length() > 0 && !word.toLowerCase().replaceAll("[^a-zA-Z0-9]", "").equals(input.toLowerCase())).toArray().length;
			currLine = br.readLine();
		}
		wordsFrequency[currChapter] = count;
		return Arrays.copyOfRange(wordsFrequency, 1, wordsFrequency.length);
	}

	int getChapterQuoteAppears(String quote) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String currLine = br.readLine();
		int currChapter = 0;
		while (currLine != null) {
			if (currLine.contains("Chapter")) {
				currChapter++;
			}
			if (currLine.contains(quote)) {
				return currChapter;
			}
			currLine = br.readLine();
		}
		return -1;
	}

	String generateSentence(int numWords) throws Exception {
		ArrayList<String> wordsForNewLine = new ArrayList<>();
		wordsForNewLine.add("The");
		for (int iteration = 1; iteration < numWords; iteration++) {
			String prevWord = wordsForNewLine.get(wordsForNewLine.size() - 1).toLowerCase();
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String currLine = br.readLine();
			HashMap<String, Integer> nextWords = new HashMap<>();
			while (currLine != null) {
				if (currLine.toLowerCase().contains(prevWord)) {
					List<String> wordsInCurrLine = Arrays.stream(currLine.split(" ")).filter(word -> word != null && word.replaceAll("[^a-zA-Z0-9]", "").length() > 0).collect(Collectors.toList());
					for (int wordIndex = 0; wordIndex < wordsInCurrLine.size() - 1; wordIndex++) {
						if(wordsInCurrLine.get(wordIndex).toLowerCase().equals(prevWord)) {
							nextWords.put(wordsInCurrLine.get(wordIndex+1).replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), nextWords.getOrDefault(wordsInCurrLine.get(wordIndex+1).replaceAll("[^a-zA-Z0-9]", "").toLowerCase(), 0) + 1);
						}
					}
				}
				currLine = br.readLine();
			}
			List<String> top5Words = nextWords.entrySet().stream().sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed()).limit(5).map(Map.Entry::getKey).collect(Collectors.toList());
			Collections.shuffle(top5Words);
			if (top5Words.size() < 1) {
				break;
			}
			wordsForNewLine.add(top5Words.get(0));
		}
		return String.join(" ", wordsForNewLine) + ".";
	}


	public static void main(String[] args) throws Exception {
		File file = new File("harold_the_klansman.txt");
		TextAnalysis ta = new TextAnalysis(file);
		System.out.println(ta.generateSentence(20));
	}
}
