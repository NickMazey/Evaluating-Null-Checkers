package nm.evaluatingnullcheckers.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nm.evaluatingnullcheckers.tools.InvokerUtils.Flag;
import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Produces results output as an XSSFWorkbook in XLSX format
 * 
 * @author Nick Mazey
 *
 */
public class ResultsOutputXLSX implements ResultsOutput {

	@Override
	public byte[] outputResults(HashMap<KnownChecker, CheckerResult> results) {
		//To suppress warning
		System.setProperty("log4j2.loggerContextFactory","org.apache.logging.log4j.simple.SimpleLoggerContextFactory");
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
		for (int i = 0; i < 5; i++) {
			summary.autoSizeColumn(i);
		}
		Sheet notableSubjects = notableSubjects(results,checkersInOrder,workbook);
		for (int i = 0; i < notableSubjects.getLastRowNum(); i++) {
			// Fixes row height only in MS Excel, does not work for libreoffice
			notableSubjects.getRow(i).setHeight((short) -1);
		}
		for (int i = 0; i < checkersInOrder.size() +1; i++) {
			notableSubjects.autoSizeColumn(i);
		}
		for (KnownChecker checker : checkersInOrder) {
			Sheet checkerSheet = checkerDetailSheet(results, checker, workbook);
			for (int i = 0; i < checkerSheet.getLastRowNum(); i++) {
				// Fixes row height only in MS Excel, does not work for libreoffice
				checkerSheet.getRow(i).setHeight((short) -1);
			}
			for (int i = 0; i < 4; i++) {
				checkerSheet.autoSizeColumn(i);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try{
			workbook.write(out);
		} catch(IOException e){
			//Do nothing
		}


		return out.toByteArray();
	}

	private Sheet summarySheet(HashMap<KnownChecker, CheckerResult> results, ArrayList<KnownChecker> checkersInOrder,
			XSSFWorkbook workbook) {
		Sheet summary = workbook.createSheet("Summary");
		Row metrics = summary.createRow(0);
		Cell metricsTitle = metrics.createCell(0);
		metricsTitle.setCellValue("Metrics");
		CellUtil.setAlignment(metricsTitle, HorizontalAlignment.CENTER);
		summary.addMergedRegion(new CellRangeAddress(0,0,0,4));
		Row titles = summary.createRow(1);
		Cell checkers = titles.createCell(0);
		checkers.setCellValue("Checker");
		Cell accuracy = titles.createCell(1);
		accuracy.setCellValue("Accuracy");
		Cell precision = titles.createCell(2);
		precision.setCellValue("Precision");
		Cell recall = titles.createCell(3);
		recall.setCellValue("Recall");
		Cell time = titles.createCell(4);
		time.setCellValue("Total Execution Time (ms)");
		for (int i = 0; i < checkersInOrder.size(); i++) {
			KnownChecker checker = checkersInOrder.get(i);
			CheckerResult result = results.get(checker);
			Row checkerData = summary.createRow(summary.getLastRowNum() + 1);
			Cell checkerName = checkerData.createCell(0);
			checkerName.setCellValue(checker.toString());
			Cell checkerAccuracy = checkerData.createCell(1);
			checkerAccuracy.setCellValue(String.format("%.02f",result.getAccuracy()));
			Cell checkerPrecision = checkerData.createCell(2);
			checkerPrecision.setCellValue(String.format("%.02f",result.getPrecision()));
			Cell checkerRecall = checkerData.createCell(3);
			checkerRecall.setCellValue(String.format("%.02f",result.getRecall()));
			Cell checkerTime = checkerData.createCell(4);
			checkerTime.setCellValue(result.getExecutionTime());
		}
		
		//Similarity
		Row emptyRow = summary.createRow(summary.getLastRowNum() +1);
		Row indicesTitleRow = summary.createRow(summary.getLastRowNum() + 1);
		Cell indicesTitle = indicesTitleRow.createCell(0);
		indicesTitle.setCellValue("Similarity (Jaccard Index)");
		CellUtil.setAlignment(indicesTitle, HorizontalAlignment.CENTER);
		summary.addMergedRegion(new CellRangeAddress(indicesTitleRow.getRowNum(),indicesTitleRow.getRowNum(),0,checkersInOrder.size()));
		Row checkerNamesRow = summary.createRow(summary.getLastRowNum() + 1);
		//Titles
		for(int i = 0; i < checkersInOrder.size() + 1; i++) {
			if(i != 0) {
				Cell checkerName = checkerNamesRow.createCell(i);
				checkerName.setCellValue(checkersInOrder.get(i-1).toString());
			}
		}
		
		//Columns
		for(int i = 0; i < checkersInOrder.size(); i++) {
			KnownChecker checker = checkersInOrder.get(i);
			Row checkerSimilarityRow = summary.createRow(summary.getLastRowNum() + 1);
			Cell checkerName = checkerSimilarityRow.createCell(0);
			checkerName.setCellValue(checker.toString());
			for(int j = 0; j < checkersInOrder.size(); j++){
				KnownChecker other = checkersInOrder.get(j);
				Cell valueCell = checkerSimilarityRow.createCell(j + 1);
				if(checker != other) {
					valueCell.setCellValue(String.format("%.02f",results.get(checker).getSimilarity().get(other)));
					valueCell.setCellStyle(indexColour(workbook,results.get(checker).getSimilarity().get(other)));
				}else {
					valueCell.setCellValue(1);
					valueCell.setCellStyle(indexColour(workbook,1));
				}
			}
		}
		
		return summary;
	}
	
	private XSSFCellStyle indexColour(XSSFWorkbook workbook,double value) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		int r = (int)((1 - value) * 255.0);
		int g = (int)((1 - value)* 93.0) + 162;
		int b = 255;
		if(r > 255) {
			r = 255;
		}
		if(g > 255) {
			g = 255;
		}
		byte[] rgb = {(byte) r, (byte) g,(byte)b};
		style.setFillForegroundColor(new XSSFColor(rgb,null));
		return style;
		
		
	}
	
	private Sheet notableSubjects(HashMap<KnownChecker,CheckerResult> results,ArrayList<KnownChecker> checkersInOrder, Workbook workbook) {
		Sheet notableSubjects = workbook.createSheet("Notable Subjects");
		HashSet<String> allSubjects = new HashSet<String>();
		for(KnownChecker checker : results.keySet()) {
			allSubjects.addAll(results.get(checker).getSubjectResults().keySet());
		}
		HashSet<String> notableSubjectNames = new HashSet<String>();
		for(String subjectName : allSubjects) {
			for(KnownChecker checker : results.keySet()) {
				Flag subjectFlag =results.get(checker).getSubjectResults().get(subjectName);
				if(subjectFlag != Flag.TRUEPOSITIVE && subjectFlag != Flag.TRUENEGATIVE) {
					notableSubjectNames.add(subjectName);
				}
			}
		}
		ArrayList<String> orderedSubjects = new ArrayList<String>();
		orderedSubjects.addAll(notableSubjectNames);
		Collections.sort(orderedSubjects);
		
		Row checkerNamesRow = notableSubjects.createRow(notableSubjects.getLastRowNum() + 1);
		//Titles
		for(int i = 0; i < checkersInOrder.size() + 1; i++) {
			if(i != 0) {
				Cell checkerName = checkerNamesRow.createCell(i);
				checkerName.setCellValue(checkersInOrder.get(i-1).toString());
			}
		}
		for(int i = 0; i < orderedSubjects.size(); i++) {
			String subjectName = orderedSubjects.get(i);
			Row subjectRow = notableSubjects.createRow(notableSubjects.getLastRowNum() +1);
			Cell nameCell = subjectRow.createCell(0);
			nameCell.setCellValue(subjectName);
			for(int j = 0; j < checkersInOrder.size(); j++) {
				KnownChecker checker = checkersInOrder.get(j);
				Cell resultCell = subjectRow.createCell(j + 1);
				Flag flag = results.get(checker).getSubjectResults().get(subjectName);
				resultCell.setCellValue(flag.toString());
				resultCell.setCellStyle(getFlagStyle(workbook, flag));
			}
		}
		
		
		
		return notableSubjects;
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
		Cell executionTime = titles.createCell(3);
		executionTime.setCellValue("Execution Time (ms)");
		CheckerResult result = results.get(checker);
		int i = 0;
		ArrayList<String> subjectNamesList = new ArrayList<String>();
		subjectNamesList.addAll(result.getSubjectResults().keySet());
		//Adding sorting to make subject order consistent
		Collections.sort(subjectNamesList);
		for (String subjectName : subjectNamesList) {
			Row subject = details.createRow(i + 1);
			Cell name = subject.createCell(0);
			name.setCellValue(subjectName);
			Cell flag = subject.createCell(1);
			flag.setCellValue(result.getSubjectResults().get(subjectName).toString());
			flag.setCellStyle(getFlagStyle(workbook,result.getSubjectResults().get(subjectName)));

			Cell message = subject.createCell(2);
			message.setCellValue(result.getSubjectMessages().get(subjectName));
			Cell exectime = subject.createCell(3);
			exectime.setCellValue(result.getSubjectExecutionTimes().get(subjectName));
			i++;
		}
		return details;
	}
	
	private CellStyle getFlagStyle(Workbook workbook, Flag flag) {
		CellStyle style = workbook.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		switch(flag) {
		case ERROR:
			style.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
			break;
		case FALSENEGATIVE:
			style.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
			break;
		case FALSEPOSITIVE:
			style.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
			break;
		case TRUENEGATIVE:
			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			break;
		case TRUEPOSITIVE:
			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			break;
		default:
			style.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
			break;
		}
		return style;
	}

}
