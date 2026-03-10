package jp.alhinc.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateSales {

	// 支店定義ファイル名
	private static final String FILE_NAME_BRANCH_LST = "branch.lst";

	// 支店別集計ファイル名
	private static final String FILE_NAME_BRANCH_OUT = "branch.out";

	// エラーメッセージ
	private static final String UNKNOWN_ERROR = "予期せぬエラーが発生しました";
	private static final String FILE_NOT_EXIST = "支店定義ファイルが存在しません";
	private static final String FILE_INVALID_FORMAT = "支店定義ファイルのフォーマットが不正です";

	/**
	 * メインメソッド
	 *
	 * @param コマンドライン引数
	 */
	public static void main(String[] args) {
		// 支店コードと支店名を保持するMap
		Map<String, String> branchNames = new HashMap<>();
		// 支店コードと売上金額を保持するMap
		Map<String, Long> branchSales = new HashMap<>();

		// 支店定義ファイル読み込み処理
		if(!readFile(args[0], FILE_NAME_BRANCH_LST, branchNames, branchSales)) {
			return;
		}

		// ※ここから集計処理を作成してください。(処理内容2-1、2-2)
		//2-1
		//listFilesを使用してフォルダからファイルを取得する
		File[] files = new File(args[0]).listFiles();

		//ファイル情報の格納用領域の宣言
		List<File> rcdFiles = new ArrayList<>();

		//ファイル名の格納用領域の宣言
		String fileName = null;

		//取得したファイルを取得件数分繰り返す
		for(int i = 0; i < files.length; i++ ) {

			//ファイル名を取得する
			fileName = files[i].getName();

			//拡張子がrcd、かつファイル名が数字8桁の場合
			if(fileName.matches("[0-9]{8}.+rcd$")) {

				//ファイル情報格納用領域へ設定する
				rcdFiles.add(files[i]);
				System.out.println( "登録ファイル情報" + files[i] );
			}
		}

		//2-2
		BufferedReader br = null;

		try{

			//rcdFilesの件数分繰り返す
			for(int i = 0; i < rcdFiles.size(); i++) {

				//格納したファイルを読み込む
				//List→.get()：指定した要素を取得/.getName()：ファイル名の取得
				File file = new File(args[0],rcdFiles.get(i).getName());
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);

				//ファイル詳細情報格納用領域の宣言
				List<String> rcdDetailList = new ArrayList<>();

				String line;
				// 一行ずつ読み込む
				while((line = br.readLine()) != null) {

					//新規作成したrcdDetailListへ格納する
					rcdDetailList.add(line);
				}

				//キャストする
				long fileSale = Long.parseLong(rcdDetailList.get(1));
				System.out.println("売上金額：" + fileSale);

				//branchSalesのkeyに紐づくvalueを取得する
				Long saleAmount = branchSales.get(rcdDetailList.get(0));

				//読み込んだ売上金額を加算する
				saleAmount += fileSale;

				//keyに紐づくbranchSalesへ設定する
				branchSales.put(rcdDetailList.get(0), saleAmount);

				System.out.println(branchSales);
			}
		} catch(IOException e) {
			System.out.println(UNKNOWN_ERROR);
			return;
		} finally {
			// ファイルを開いている場合
			if(br != null) {
				try {
					// ファイルを閉じる
					br.close();
				} catch(IOException e) {
					System.out.println(UNKNOWN_ERROR);
					return;
				}
			}
		}

		// 支店別集計ファイル書き込み処理
		if(!writeFile(args[0], FILE_NAME_BRANCH_OUT, branchNames, branchSales)) {
			return;
		}

	}

	/**
	 * 支店定義ファイル読み込み処理
	 *
	 * @param フォルダパス
	 * @param ファイル名
	 * @param 支店コードと支店名を保持するMap
	 * @param 支店コードと売上金額を保持するMap
	 * @return 読み込み可否
	 */
	private static boolean readFile(String path, String fileName, Map<String, String> branchNames, Map<String, Long> branchSales) {
		BufferedReader br = null;

		try {
			File file = new File(path, fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);

			String line;
			// 一行ずつ読み込む
			while((line = br.readLine()) != null) {
				// ※ここの読み込み処理を変更してください。(処理内容1-2)

				//文字を分割する(区切った文字が設定されている)
				String[] splitLine = line.split(",");

				//区切った文字をキャストしbranchNamesへ設定する
				branchNames.put(splitLine[0], splitLine[1]);

				//keyに紐づくbranchSalesへ設定する(「0」円で追加)
				branchSales.put(splitLine[0], 0L);

				System.out.println(line);
			}

		} catch(IOException e) {
			System.out.println(UNKNOWN_ERROR);
			return false;
		} finally {
			// ファイルを開いている場合
			if(br != null) {
				try {
					// ファイルを閉じる
					br.close();
				} catch(IOException e) {
					System.out.println(UNKNOWN_ERROR);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 支店別集計ファイル書き込み処理
	 *
	 * @param フォルダパス
	 * @param ファイル名
	 * @param 支店コードと支店名を保持するMap
	 * @param 支店コードと売上金額を保持するMap
	 * @return 書き込み可否
	 */
	private static boolean writeFile(String path, String fileName, Map<String, String> branchNames, Map<String, Long> branchSales) {
		// ※ここに書き込み処理を作成してください。(処理内容3-1)

		return true;
	}

}
