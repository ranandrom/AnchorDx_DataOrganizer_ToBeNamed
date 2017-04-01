package AnchorDx_DataOrganizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import AnchorDx_CollectData_SearchFiles.AnchorDx_CollectData_SearchFiles;

/**
 * Description: <br/>
 * Function: This class is main class. <br/>
 * File Name: AnchorDx_DataOrganizer.java <br/>
 * Date: 2017-03-30
 * 
 * @author Luzhirong ramandrom@139.com
 * @version V1.0.0
 */

public class AnchorDx_DataOrganizer
{
	/**
	 * main方法，程序的入口.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println();

		String porject_Path = "./基准所有项目收样信息表样本处理追踪表"; // 源文件路径
		String target_Path = "./Projects/"; // 輸出文件目标路径
		String excel_part_name = "样本处理追踪表v1_广州基准医疗"; // 源文件名部分字符串
		int Pattern = 0; // 設置工作模式，0代表全盤模式，1代表更新模式
		File des_file = null;
		int time = 0; // 設置等待時間次數

		int args_len = args.length; // 系统传入主函数的参数长度
		int logo = 0; // "-o"参数输入次数计算标志
		int logm = 0; // "-m"参数输入次数计算标志
		for (int len = 0; len < args_len; len += 2) {
			if (args[len].equals("-O") || args[len].equals("-o")) {
				target_Path = args[len + 1] + "/";
				logo++;
			} else if (args[len].equals("-M") || args[len].equals("-m")) {
				Pattern = Integer.valueOf(args[len + 1]);
				logm++;
			} else if ((args_len == 1) && args[0].equals("-help")) {
				System.out.println();
				System.out.println("Version: V1.0.0");
				System.out.println();
				System.out.println("Usage:\t java -jar AnchorDx_DataOrganizer.jar [Options] [args...]");
				System.out.println();
				System.out.println("Options:");
				System.out.println("-help\t\t Obtain parameter description.");
				System.out.println(
						"-M or -m\t Set work pattern. Inuput 0 or 1, 0 representative operate all data and 1 operate update data. The default value is 0.");
				System.out.println("-O or -o\t Set output file. The default value is \"./Projects/\".");
				System.out.println();
				return;
			} else {
				System.out.println();
				System.out.println("对不起，您输入的Options不存在，或者缺少所需参数，请参照以下参数提示输入！");
				System.out.println();
				System.out.println("Options:");
				System.out.println("-help\t\t Obtain parameter description.");
				System.out.println(
						"-M or -m\t Set work pattern. Inuput 0 or 1, 0 representative operate all data and 1 operate update data. The default value is 0.");
				System.out.println("-O or -o\t Set output file. The default value is \"./Projects/\".");
				System.out.println();
				return;
			}
			if (logo > 1 || logm > 1) {
				System.out.println();
				System.out.println("对不起，您输的入Options有重复，请参照以下参数提示输入！");
				System.out.println();
				System.out.println("Options:");
				System.out.println("-help\t\t Obtain parameter description.");
				System.out.println(
						"-M or -m\t Set work pattern. Inuput 0 or 1, 0 representative operate all data and 1 operate update data. The default value is 0.");
				System.out.println("-O or -o\t Set output file. The default value is \"./Projects/\".");
				System.out.println();
				return;
			}
		}

		Calendar now_star = Calendar.getInstance();
		SimpleDateFormat formatter_star = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("程序开始时间: " + formatter_star.format(now_star.getTime()));
		System.out.println("===============================================");
		System.out.println("Version: AnchorDx_DataOrganizer V1.0.0");
		System.out.println("***********************************************");

		int tag = copyExcel(); // 复制zhirong_lu@192.192.192.200:/wdmycloud/anchordx_cloud/杨莹莹/基准所有项目收样信息表样本处理追踪表到本地，程序正常返回0，否则返回-1.
		if (tag == 0) {
			System.out.println("CopyExcel Success!");
		} else {
			System.out.println("CopyExcel Failure!");
			return;
		}

		while (true) {
			des_file = new File(porject_Path);
			if (des_file.exists()) { // 判断目录是否存在
				break;
			} else if (time == 100) { // 如果一千秒内无法获取到“./基准所有项目收样信息表样本处理追踪表”目录，则结束程序。
				System.out.println("对不起，由于在1000秒内无法获取到“./基准所有项目收样信息表样本处理追踪表”目录，因此结束程序！！！");
				System.out.println();
				System.out.println("===============================================");
				Calendar now_end = Calendar.getInstance();
				SimpleDateFormat formatter_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("程序结束时间: " + now_end.getTime());
				System.out.println("程序结束时间: " + formatter_end.format(now_end.getTime()));
				return;
			} else {
				Thread.sleep(10000); // 主线程睡眠十秒
				time++;
				continue;
			}
		}

		// 根据上面操作所得数据生成对应文件
		int UF = 1;
		if (Pattern == 0) {
			UF = allCopydirAndReanExecl(des_file, target_Path, excel_part_name); // 全盘模式：复制目录结构，以及读目标表数据
		} else {
			UF = updataCopydirAndReanExecl(des_file, target_Path, excel_part_name); // 更新模式：复制目录结构，以及读目标表数据
		}
		if (UF == 0) {
			System.out.println("CopydirAndReanExecl Success!");
		} else {
			System.out.println("CopydirAndReanExecl Failure!");
			return;
		}

		// 查找文件，并输出结果
		AnchorDx_CollectData_SearchFiles.mainFunction(target_Path, Pattern);
		System.out.println("AnchorDx_CollectData_SearchFiles finish!");

		Thread.sleep(3000); // 主綫程睡眠3秒
		upload_File(target_Path); // 上传文件到云端

		System.out.println();
		System.out.println("===============================================");
		Calendar now_end = Calendar.getInstance();
		SimpleDateFormat formatter_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("程序结束时间: " + formatter_end.format(now_end.getTime()));
	}

	/**
	 * 远程复制文件的方法：
	 * 利用rsync远程复制zhirong_lu@192.192.192.200:/wdmycloud/anchordx_cloud/杨莹莹/
	 * 基准所有项目收样信息表样本处理追踪表目录到本地，程序正常返回0，否则返回-1.
	 */
	@SuppressWarnings("unused")
	public static int copyExcel()
	{
		try {
			String cmd_Sample_statistics[] = { "rsync", "-aP", "--include=*/", "--include=**/*样本处理追踪表*.xls*",
					"--exclude=*", "zhirong_lu@192.192.192.220:/wdmycloud/anchordx_cloud/杨莹莹/基准所有项目收样信息表样本处理追踪表", "." };
			Process process = Runtime.getRuntime().exec(cmd_Sample_statistics);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = input.readLine()) != null) { // 循环读出系统返回数据，保证系统调用已经正常结束
				// System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	/**
	 * 查找并创建对应目录(全盘模式)的方法。
	 * 
	 * @param des_file
	 * @param target_Path
	 * @param excel_part_name
	 * @return
	 */
	@SuppressWarnings({ "null", "unused" })
	public static int allCopydirAndReanExecl(File des_file, String target_Path, String excel_part_name)
	{
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter_Date = new SimpleDateFormat("yyyyMMdd");
		String Date = formatter_Date.format(now.getTime()); // 格式化后的日期

		// 判断目录下是否为空
		if (des_file == null) {
			System.out.println("该目录为空：" + des_file.getName());
			return -1;
		} else {
			for (File pathname : des_file.listFiles()) {
				// 如果是目录
				if (pathname.isDirectory()) {

					// 在指定路径下创建对应目录
					String dir_name = pathname.getName();
					String dir_path_name = target_Path + dir_name + "/Master";
					my_mkdir(dir_path_name); // 创建目录

					for (File porject_name : pathname.listFiles()) {
						// 如果是文件
						if (porject_name.isFile()) {
							String Suffix = porject_name.getName().substring(porject_name.getName().lastIndexOf(".")); // 获取后缀名
							String Remove_suffix = porject_name.getName().replaceAll(Suffix, ""); // 去除后缀名

							if (Remove_suffix.contains(excel_part_name) && !Remove_suffix.contains("~$")) {
								String Source_File = porject_name.getParent() + "/" + porject_name.getName(); // 带路径的源文件名
								String Output_File = dir_path_name + "/" + Remove_suffix + "_" + Date + "_All_"
										+ ".txt"; // 输出目标的文件名
								String Source_Output_File = "Master/" + Remove_suffix + "_" + Date + "_All_" + ".txt"; // 链接的源文件

								// 读excel表表数据
								ArrayList<String> Data_list = getFromExcel(Source_File);

								// 写数据到输出文件
								writeFile(Data_list, Output_File);

								// 做软链接，链接总数据文件
								try {
									String Link_File_All = target_Path + dir_name + "/" + Remove_suffix + "_All_"
											+ ".txt"; // 带路径的链接文件名
									String cmd_All = "ln -s -f " + Source_Output_File + " " + Link_File_All; // Linux命令
									Process process = Runtime.getRuntime().exec(cmd_All); // 调用Linux命令
									BufferedReader input = new BufferedReader(
											new InputStreamReader(process.getInputStream()));
									String line = null;

									// 循环读出系统返回数据，保证系统调用已经正常结束
									while ((line = input.readLine()) != null) {
										// System.out.println(line);
									}

								} catch (Exception e) {
									System.out.println("链接出错！");
									e.printStackTrace();
									return -1;
								}
							} else {
								continue;
							}
						} else {
							continue;
						}
					}
				} else {
					continue;
				}
			}
		}
		return 0;
	}

	/**
	 * 查找并创建对应目录(更新模式)的方法。
	 * 
	 * @param des_file
	 * @param target_Path
	 * @param excel_part_name
	 * @return
	 */
	@SuppressWarnings({ "null", "unused" })
	public static int updataCopydirAndReanExecl(File des_file, String target_Path, String excel_part_name)
	{
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter_Date = new SimpleDateFormat("yyyyMMdd");
		String Date = formatter_Date.format(now.getTime()); // 格式化后的日期

		ArrayList<String> OldDatalist = new ArrayList<String>(); // 旧的数据列表
		ArrayList<String> UpdataDatalist = new ArrayList<String>(); // 需要更新的数据列表

		// 判断目录下是否为空
		if (des_file == null) {
			System.out.println("该目录为空：" + des_file.getName());
			return -1;
		} else {
			for (File pathname : des_file.listFiles()) {
				// 如果是目录
				if (pathname.isDirectory()) {

					// 在指定路径下创建对应目录
					String dir_name = pathname.getName();
					String dir_path_name = target_Path + dir_name + "/Master";
					my_mkdir(dir_path_name); // 创建目录

					for (File porject_name : pathname.listFiles()) {
						// 如果是文件
						if (porject_name.isFile()) {
							String Suffix = porject_name.getName().substring(porject_name.getName().lastIndexOf(".")); // 获取后缀名
							String Remove_suffix = porject_name.getName().replaceAll(Suffix, ""); // 去除后缀名

							if (Remove_suffix.contains(excel_part_name) && !Remove_suffix.contains("~$")) {
								// 源文件
								String Source_File = porject_name.getParent() + "/" + porject_name.getName(); // 带路径的源文件名
								String Output_File = dir_path_name + "/" + Remove_suffix + "_" + Date + "_All_"
										+ ".txt"; // 输出目标的文件名
								String LastTime_Output_File = target_Path + dir_name + "/" + Remove_suffix + "_All_"
										+ ".txt"; // 上次输出总数据的文件
								String Updata_File = dir_path_name + "/" + Remove_suffix + "_" + Date + "_Updata_"
										+ ".txt";// 更新数据的文件
								String Source_All_Output_File = "Master/" + Remove_suffix + "_" + Date + "_All_"
										+ ".txt"; // 链接的总数据文件的源文件
								String Source_Updata_Output_File = "Master/" + Remove_suffix + "_" + Date + "_Updata_"
										+ ".txt"; // 链接的更新数据文件的源文件

								// 清空列表数据
								OldDatalist.clear();
								UpdataDatalist.clear();
								readLibfile(LastTime_Output_File, OldDatalist); // 读上次更新的表数据
								ArrayList<String> Data_list = getFromExcel(Source_File); // 读excel表
								writeFile(Data_list, Output_File); // 写数据到总数据文件
								for (int i = OldDatalist.size(); i < Data_list.size(); i++) {
									UpdataDatalist.add(Data_list.get(i)); // 获取更新数据
								}
								writeFile(UpdataDatalist, Updata_File); // 写数据到更新数据文件

								// 做软链接
								try {
									String Link_File_All = target_Path + dir_name + "/" + Remove_suffix + "_All_"
											+ ".txt"; // 带路径的链接文件名
									String cmd_All = "ln -s -f " + Source_All_Output_File + " " + Link_File_All; // Linux命令
									Process process_All = Runtime.getRuntime().exec(cmd_All); // 调用Linux命令，链接总数据文件
									BufferedReader input_All = new BufferedReader(
											new InputStreamReader(process_All.getInputStream()));
									String line_All = null;
									// 循环读出系统返回数据，保证系统调用已经正常结束
									while ((line_All = input_All.readLine()) != null) {
										// System.out.println(line);
									}

									String Link_File_Updata = target_Path + dir_name + "/" + Remove_suffix + "_Updata_"
											+ ".txt"; // 带路径的链接文件名
									String cmd_Updata = "ln -s -f " + Source_Updata_Output_File + " "
											+ Link_File_Updata; // Linux命令
									Process process_Updata = Runtime.getRuntime().exec(cmd_Updata); // 调用Linux命令，链接更新数据文件
									BufferedReader input_Updata = new BufferedReader(
											new InputStreamReader(process_Updata.getInputStream()));
									String line_Updata = null;
									// 循环读出系统返回数据，保证系统调用已经正常结束
									while ((line_Updata = input_Updata.readLine()) != null) {
										// System.out.println(line);
									}

								} catch (Exception e) {
									System.out.println("链接出错！");
								}
							} else {
								continue;
							}
						} else {
							continue;
						}
					}
				} else {
					continue;
				}
			}
		}
		return 0;
	}

	/**
	 * 调用脚本，上传文件到云端。
	 * 
	 * @param PutPath
	 */
	@SuppressWarnings("unused")
	public static void upload_File(String PutPath)
	{
		String cmd = "/opt/local/bin/python35/python /var/script/alan/10k_api_script/white_black_collections.py -path "
				+ PutPath;
		try {
			Process process = Runtime.getRuntime().exec(cmd); // 调用Linux命令
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input.readLine()) != null) {
				// System.out.println(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建目录的方法。
	 * 
	 * @param dir_name
	 */
	public static void my_mkdir(String dir_name)
	{
		File file = new File(dir_name);
		// 判断目录是否存在，如果文件不存在，则创建
		if (!file.exists() && !file.isDirectory()) {
			// 创建目录
			file.mkdirs();
		}
	}

	/**
	 * 把列表数据写到输出文件中的方法。
	 * 
	 * @param Data_list
	 * @param Output_File
	 */
	public static void writeFile(ArrayList<String> Data_list, String Output_File)
	{
		try {
			FileWriter fw = new FileWriter(Output_File);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("#Head" + "\r\n");// 往文件上写头信息
			for (int i = 0; i < Data_list.size(); i++) {
				bw.write(Data_list.get(i) + "\r\n"); // 写数据
			}
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读xlsx格式文件到列表.
	 * 
	 * @param file
	 * @return List
	 * @throws Exception
	 */
	public static ArrayList<String> readXlsx(File file) throws Exception
	{
		ArrayList<String> Data_list = new ArrayList<String>(); // 数据列表
		InputStream is = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(is);
		XSSFSheet sheet = null;
		int Sheet_Num = wb.getNumberOfSheets(); // 获取工作薄个数

		for (int numSheet = 0; numSheet < Sheet_Num; numSheet++) {
			sheet = wb.getSheetAt(numSheet); // 获取工作薄
			String Sheet_Name = sheet.getSheetName(); // 获取当前工作薄名字
			if (Sheet_Name.trim().equals("DNA预文库")) {
				break;
			} else {
				sheet = null;
			}
		}
		// 获取当前工作薄的每一行
		for (int i = sheet.getFirstRowNum() + 3; i <= sheet.getLastRowNum(); i++) {
			XSSFRow xssfrow = sheet.getRow(i);
			XSSFCell xssfcell0 = xssfrow.getCell(0); // 获取当前工作薄的第一列
			XSSFCell xssfcell1 = xssfrow.getCell(1); // 获取当前工作薄的第二列
			if ((xssfcell0 != null) && (xssfcell0.getStringCellValue().trim().equals("示例"))) {
				continue;
			} else {
				if (xssfcell1 != null) {
					String cellValue = xssfcell1.getStringCellValue().trim();
					Data_list.add(cellValue);
				}
			}
		}
		try {
			is.close();
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Data_list;
	}

	/**
	 * 读xls格式文件到列表。
	 * 
	 * @param file
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<String> readXls(File file) throws Exception
	{
		ArrayList<String> Data_list = new ArrayList<String>();
		InputStream is = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(is);
		Sheet sheet = null;
		int Sheet_Num = wb.getNumberOfSheets(); // 获取工作薄个数
		for (int numSheet = 0; numSheet < Sheet_Num; numSheet++) {
			sheet = wb.getSheetAt(numSheet); // 获取工作薄
			String Sheet_Name = sheet.getSheetName(); // 获取当前工作薄名字
			if (Sheet_Name.trim().equals("DNA预文库")) {
				break;
			} else {
				sheet = null;
			}
		}
		// 获取当前工作薄的每一行
		for (int i = sheet.getFirstRowNum() + 3; i <= sheet.getLastRowNum(); i++) {
			HSSFRow hssfrow = (HSSFRow) sheet.getRow(i); // 获取行
			HSSFCell hssfcell0 = hssfrow.getCell(0); // 获取当前工作薄的第一列
			HSSFCell hssfcell1 = hssfrow.getCell(1); // 获取当前工作薄的第二列
			hssfcell0.setCellType(Cell.CELL_TYPE_STRING);
			if ((hssfcell0 != null) && (hssfcell0.getStringCellValue().trim().equals("示例"))) {
				continue;
			} else {
				if (hssfcell1 != null) {
					// 设置单元格类型为String类型，以便读取时候以string类型，也可其它
					hssfcell1.setCellType(Cell.CELL_TYPE_STRING);
					String cellValue = hssfcell1.getStringCellValue().trim();
					Data_list.add(cellValue);
				}
			}
		}
		try {
			is.close();
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Data_list;
	}

	/**
	 * 读Excel表文件。
	 * 
	 * @param filename
	 * @return List
	 */
	public static ArrayList<String> getFromExcel(String filename)
	{
		ArrayList<String> Data_list = new ArrayList<String>(); // 数据列表
		String type = filename.substring(filename.lastIndexOf(".") + 1); // 获取文件类型
		File file = new File(filename);
		try {
			if (type.equals("xls")) { // 如果是xls格式文件
				Data_list = readXls(file);
			} else if (type.equals("xlsx")) { // 如果是xlsx格式文件
				Data_list = readXlsx(file);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Data_list;
	}

	/**
	 * 把txt文件读到列表里，然后返回该文件的头数据行数据的方法。
	 * 
	 * @param filePath
	 * @param IFILE
	 * @return
	 */
	public static String readLibfile(String filePath, ArrayList<String> IFILE)
	{
		String Head = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.length() != 0) {
						// 判断是否为头格式行数据
						if (lineTxt.substring(0, 1).equals("#") || lineTxt.substring(0, 2).equals("/*")
								|| lineTxt.substring(0, 1).equals("@")) {
							Head = lineTxt;
							continue;
						}
						IFILE.add(lineTxt);
					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件：" + filePath);
				return "OFF";
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错：" + filePath);
			e.printStackTrace();
			return "OFF";
		}
		return Head;
	}
}
