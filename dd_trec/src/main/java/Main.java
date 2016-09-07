import db.DBReader3;
import db.DBUtility;
import db.DBWriter;
import db.DBWriter2;

import org.bson.Document;

import Trec2016.dd_trec.ContentCatcher;
import Trec2016.dd_trec.MyIndri;
import query.Doc2Mysql;
import query.ExtractQuery;
import search.PustdUpdater;
import search.SearchSession;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cat on 16/8/21.
 */
public class Main {

	private static DBReader3 dbReader3 = new DBReader3();

	private static int ITER_NUM = 6;

	// private static String FEEDBACK_PATH =
	// "/backup/dd_trec/jig/trec-dd-jig-master/DirectIndriMethod/DD16-1result.txt";

	// private static final String[] D_PATHS = {
	// "/backup/dd_trec/data/EbolaDataXML",
	// "/backup/dd_trec/data/PolarDataXML"
	//
	// };

	private static String reg = "[^a-zA-Z0-9\\s]";
	private static Pattern matchsip = Pattern.compile(reg);

	private static ContentCatcher contentCather = new ContentCatcher();

	public static void main(String args[]) {

		// import doc to mysql
		// Doc2Mysql.startImport(D_PATHS);

		// DBWriter2.initializeDatabase(D_PATHS,D_PATHS);

		// costly
		// DBWriter.initializeDatabase(false,true,false);
		// DBWriter.backup();

		try {
			int[] remainDocIDs = {21,36};
			for(int i: remainDocIDs){

//			for (int i = 37; i < 54; i++) {
				int isPolar = i > 27 ? 1 : 0;

				String queryNum = "DD16-" + i;

				String feedbackPath = "/backup/dd_trec/jig/trec-dd-jig-master/DirectIndriMethod/"
						+ queryNum + "result.txt";

				File firstResultFile = new File(firstResultPath(queryNum));

				ArrayList<String> firstResultDocs = new ArrayList<String>();

				// since the indri results are sorted, read them serially
				Scanner scanner = new Scanner(firstResultFile);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine().trim();
					if (line.length() > 0) {
						firstResultDocs.add(line);
					}
				}
				scanner.close();

				searchIter("", queryNum, feedbackPath, isPolar, firstResultDocs);
			}
		} catch (Exception e) {
			System.err.print(e);
		}
	}

	private static String firstResultPath(String queryNum) {
		return "/backup/dd_trec/dd_trec/" + queryNum + "ranking.txt";
	}

	// isPolar 0:ebola 1:Polar
	private static List<String> indriSearch(String query, int isPolar) {
		MyIndri myIndri = new MyIndri();
		return myIndri.search(query, 30, isPolar);
	}

	// get doc_id from doc_id:score
	private static String getDocID(String result) {
		return result.split(":")[0];
	}

	private static void searchIter(String initQuery, String queryNum,
			String feedbackPath, int isPolar, ArrayList<String> initResults)
			throws Exception {
		// Scanner scanner = new Scanner(System.in);
		SearchSession searchSession = new SearchSession();

		long startTime = System.currentTimeMillis();

		int iterIndex = 0;
		
		int totalIter = 0;

		// truncate updated pustd
		DBUtility.truncateUpdatedPustd();

		boolean keepIter = true;
		boolean emptyFeedback = false;

		ArrayList<String> queryList = new ArrayList<String>(1);
		queryList.add("");

		List<String> calcResults = initResults;

		List<String> preResults = new ArrayList<String>();
		List<String> indriResults = new ArrayList<String>();

		Set<String> foundResults = new HashSet<String>();

		String pre_query = initQuery;

		dbReader3.initialize(queryList);

		NextIter: while (keepIter) {

			String newQuery = initQuery + " "
					+ ExtractQuery.extract(feedbackPath);

			if (!(iterIndex == 0) && emptyFeedback == false) {

				// words of pre result D
				Set<String> Dpre = new TreeSet<String>();
				// words of previous query
				Set<String> qPre = new TreeSet<String>();
				// words of current query
				Set<String> qNow = new TreeSet<String>();

				for (String t : pre_query.split(" ")) {
					qPre.add(t);
				}

				for (String t : newQuery.split(" ")) {
					qNow.add(t);
				}

				for (String s : preResults) {
					String content = contentCather.getContentbyID(getDocID(s),
							isPolar);
					if (content != null) {
						for (String t : content.split(" ")) {
							Dpre.add(t);
						}
					}
				}

				PustdUpdater.update(Dpre, qPre, qNow, searchSession);

				preResults.clear();

			}

			queryList.set(0, newQuery);

			ArrayList<String> results = searchSession
					.getSearchResults(newQuery);

			Matcher mp = matchsip.matcher(newQuery);
			String strippedQuery = mp.replaceAll("");

			feedbackPath = "/backup/dd_trec/jig/trec-dd-jig-master/" + queryNum
					+ "result" + iterIndex + ".txt";

			StringBuilder jigCommand = new StringBuilder(
					"cd /backup/dd_trec/jig/trec-dd-jig-master; python jig/jig.py -runid LDA_Indri73 -topic ");
			jigCommand.append(queryNum + " -docs ");

			emptyFeedback = true;
			// new query is not empty, then do the normal iteration
			if (strippedQuery.trim().length() != 0) {
				emptyFeedback = false;

				pre_query = newQuery;

				indriResults = indriSearch(strippedQuery, isPolar);

				indriResults = withoutFoundResults(0, indriResults,
						foundResults);

				calcResults.clear();

				if (results != null) {
					for (String result : results) {
						if (!foundResults.contains(getDocID(result))) {
							if (isPolar == 0
									&& result.substring(0, 5).equals("ebola")) {
								calcResults.add(result);
							} else if (isPolar == 1
									&& !result.substring(0, 5).equals("ebola")) {
								calcResults.add(result);
							}
						}
					}
				}

				if (calcResults.size() < 2 || indriResults.size() < 3) {
					cantFindWarning(iterIndex, queryNum);
					keepIter = false;
					continue NextIter;
				}

				for (int i = 0; i < 3; i++) {
					addResult(indriResults.get(i), jigCommand, preResults,
							foundResults);
				}

				for (int i = 0; i < 2; i++) {
					addResult(calcResults.get(i), jigCommand, preResults,
							foundResults);
				}

				iterIndex++;

			} else if (iterIndex != 0) {
				// fail to extract new query
				// in this case , indri and calc results are all failed, so
				// update both result list

				// remove found docs
				calcResults = withoutFoundResults(2, calcResults, foundResults);
				indriResults = withoutFoundResults(3, indriResults,
						foundResults);

				if (calcResults.size() < 2 || indriResults.size() < 3) {
					cantFindWarning(iterIndex, queryNum);
					keepIter = false;
					continue NextIter;
				}

				for (int i = 0; i < 3; i++) {
					addResult(indriResults.get(i), jigCommand, preResults,
							foundResults);
				}
				for (int i = 0; i < 2; i++) {
					addResult(calcResults.get(i), jigCommand, preResults,
							foundResults);
				}
			} else {
				// the init feedback is blank
				// in this case, the preResults are all indri results

				// remove found docs
				calcResults = withoutFoundResults(5, calcResults,
						foundResults);

				if (calcResults.size() < 5) {
					cantFindWarning(iterIndex, queryNum);
					keepIter = false;
					continue NextIter;
				}

				for (int i = 0; i < 5; i++) {
					addResult(calcResults.get(i), jigCommand, preResults,
							foundResults);
				}
			}

			jigCommand.append("> " + feedbackPath);

			DBUtility.executeConsoleCommand(jigCommand.toString());

			totalIter++;
			
			// stop when meet the iter num
			if (iterIndex >= ITER_NUM || totalIter > 20)
				keepIter = false;
		}

		long endTime = System.currentTimeMillis();

		double usedMinutes = ((endTime - startTime) / 1000.0) / 60;

		System.out.println(queryNum + " finished! Used time: " + usedMinutes
				+ " mins");

	}

	private static void cantFindWarning(int iterIndex, String queryNum) {
		System.out.println("No suitable result can be found in iter"
				+ iterIndex + " for query " + queryNum + "!");
	}

	private static List<String> withoutFoundResults(int startIndex,
			List<String> resultList, Set<String> foundDocs) {
		List<String> newIndriResults = new ArrayList<String>();
		for (int i = startIndex; i < resultList.size(); i++) {
			String result = resultList.get(i);
			if (!foundDocs.contains(getDocID(result))) {
				newIndriResults.add(result);
			}
		}

		return newIndriResults;
	}

	private static void addResult(String result, StringBuilder jigCommand,
			List<String> preResults, Set<String> foundResults) {
		jigCommand.append(result + " ");
		preResults.add(result);
		foundResults.add(getDocID(result));
	}
}
