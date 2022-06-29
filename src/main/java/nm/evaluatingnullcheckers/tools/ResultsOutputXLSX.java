package nm.evaluatingnullcheckers.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Produces results output as an XSSFWorkbook in XLSX format
 * 
 * @author Nick Mazey
 *
 */
public class ResultsOutputXLSX implements ResultsOutput<XSSFWorkbook> {

	@Override
	public XSSFWorkbook outputResults(HashMap<KnownChecker, CheckerResult> results) {
		ArrayList<String> subjects = InvokerUtils.getSubjectsFromResults(results);
		ArrayList<KnownChecker> checkersInOrder = new ArrayList<KnownChecker>();
		for (KnownChecker checker : results.keySet()) {
			checkersInOrder.add(checker);
		}
		Collections.sort(checkersInOrder);

		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet summary = summarySheet(results, checkersInOrder, workbook);
		for (int i = 0; i < summary.getLastRowNum(); i++) {
			// Fixes row height only in MS Excel, does not work for libreoffice
			summary.getRow(i).setHeight((short) -1);
		}
		for (int i = 0; i < 4; i++) {
			summary.autoSizeColumn(i);
		}
		for (KnownChecker checker : checkersInOrder) {
			Sheet checkerSheet = checkerDetailSheet(results, checker, workbook);
			for (int i = 0; i < checkerSheet.getLastRowNum(); i++) {
				// Fixes row height only in MS Excel, does not work for libreoffice
				checkerSheet.getRow(i).setHeight((short) -1);
			}
			for (int i = 0; i < 3; i++) {
				checkerSheet.autoSizeColumn(i);
			}
		}

		return workbook;
	}

	private Sheet summarySheet(HashMap<KnownChecker, CheckerResult> results, ArrayList<KnownChecker> checkersInOrder,
			Workbook workbook) {
		Sheet summary = workbook.createSheet("Summary");
		Row titles = summary.createRow(0);
		Cell checkers = titles.createCell(0);
		checkers.setCellValue("Checker");
		Cell precision = titles.createCell(1);
		precision.setCellValue("Precision");
		Cell recall = titles.createCell(2);
		recall.setCellValue("Recall");
		Cell time = titles.createCell(3);
		time.setCellValue("Time Taken");
		for (int i = 0; i < checkersInOrder.size(); i++) {
			KnownChecker checker = checkersInOrder.get(i);
			CheckerResult result = results.get(checker);
			Row checkerData = summary.createRow(i + 1);
			Cell checkerName = checkerData.createCell(0);
			checkerName.setCellValue(checker.toString());
			Cell checkerPrecision = checkerData.createCell(1);
			checkerPrecision.setCellValue(result.getPrecision());
			Cell checkerRecall = checkerData.createCell(2);
			checkerRecall.setCellValue(result.getRecall());
			Cell checkerTime = checkerData.createCell(3);
			checkerTime.setCellValue(result.getExecutionTime());
		}
		return summary;
	}

	private Sheet checkerDetailSheet(HashMap<KnownChecker, CheckerResult> results, KnownChecker checker,
			Workbook workbook) {
		Sheet details = workbook.createSheet(checker.toString());
		Row titles = details.createRow(0);
		Cell subjectNames = titles.createCell(0);
		subjectNames.setCellValue("Subject Name");
		Cell subjectFlag = titles.createCell(1);
		subjectFlag.setCellValue("Checker Result");
		Cell checkerMessage = titles.createCell(2);
		checkerMessage.setCellValue("Checker Message");
		CheckerResult result = results.get(checker);
		int i = 0;
		for (String subjectName : result.getSubjectResults().keySet()) {
			Row subject = details.createRow(i + 1);
			Cell name = subject.createCell(0);
			name.setCellValue(subjectName);
			Cell flag = subject.createCell(1);
			flag.setCellValue(result.getSubjectResults().get(subjectName).toString());
			Cell message = subject.createCell(2);
			message.setCellValue(result.getSubjectMessages().get(subjectName));
			i++;
		}
		return details;
	}

}
