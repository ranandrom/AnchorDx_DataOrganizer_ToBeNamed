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
	 * main��������������.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println();

		String porject_Path = "./��׼������Ŀ������Ϣ����������׷�ٱ�"; // Դ�ļ�·��
		String target_Path = "./Projects/"; // ݔ���ļ�Ŀ��·��
		String excel_part_name = "��������׷�ٱ�v1_���ݻ�׼ҽ��"; // Դ�ļ��������ַ���
		int Pattern = 0; // �O�ù���ģʽ��0����ȫ�Pģʽ��1�������ģʽ
		File des_file = null;
		int time = 0; // �O�õȴ��r�g�Δ�

		int args_len = args.length; // ϵͳ�����������Ĳ�������
		int logo = 0; // "-o"����������������־
		int logm = 0; // "-m"����������������־
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
				System.out.println("�Բ����������Options�����ڣ�����ȱ�������������������²�����ʾ���룡");
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
				System.out.println("�Բ����������Options���ظ�����������²�����ʾ���룡");
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
		System.out.println("����ʼʱ��: " + formatter_star.format(now_star.getTime()));
		System.out.println("===============================================");
		System.out.println("Version: AnchorDx_DataOrganizer V1.0.0");
		System.out.println("***********************************************");

		int tag = copyExcel(); // ����zhirong_lu@192.192.192.200:/wdmycloud/anchordx_cloud/��ӨӨ/��׼������Ŀ������Ϣ����������׷�ٱ����أ�������������0�����򷵻�-1.
		if (tag == 0) {
			System.out.println("CopyExcel Success!");
		} else {
			System.out.println("CopyExcel Failure!");
			return;
		}

		while (true) {
			des_file = new File(porject_Path);
			if (des_file.exists()) { // �ж�Ŀ¼�Ƿ����
				break;
			} else if (time == 100) { // ���һǧ�����޷���ȡ����./��׼������Ŀ������Ϣ����������׷�ٱ�Ŀ¼�����������
				System.out.println("�Բ���������1000�����޷���ȡ����./��׼������Ŀ������Ϣ����������׷�ٱ�Ŀ¼����˽������򣡣���");
				System.out.println();
				System.out.println("===============================================");
				Calendar now_end = Calendar.getInstance();
				SimpleDateFormat formatter_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("�������ʱ��: " + now_end.getTime());
				System.out.println("�������ʱ��: " + formatter_end.format(now_end.getTime()));
				return;
			} else {
				Thread.sleep(10000); // ���߳�˯��ʮ��
				time++;
				continue;
			}
		}

		// ����������������������ɶ�Ӧ�ļ�
		int UF = 1;
		if (Pattern == 0) {
			UF = allCopydirAndReanExecl(des_file, target_Path, excel_part_name); // ȫ��ģʽ������Ŀ¼�ṹ���Լ���Ŀ�������
		} else {
			UF = updataCopydirAndReanExecl(des_file, target_Path, excel_part_name); // ����ģʽ������Ŀ¼�ṹ���Լ���Ŀ�������
		}
		if (UF == 0) {
			System.out.println("CopydirAndReanExecl Success!");
		} else {
			System.out.println("CopydirAndReanExecl Failure!");
			return;
		}

		// �����ļ�����������
		AnchorDx_CollectData_SearchFiles.mainFunction(target_Path, Pattern);
		System.out.println("AnchorDx_CollectData_SearchFiles finish!");

		Thread.sleep(3000); // ���Q��˯��3��
		upload_File(target_Path); // �ϴ��ļ����ƶ�

		System.out.println();
		System.out.println("===============================================");
		Calendar now_end = Calendar.getInstance();
		SimpleDateFormat formatter_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("�������ʱ��: " + formatter_end.format(now_end.getTime()));
	}

	/**
	 * Զ�̸����ļ��ķ�����
	 * ����rsyncԶ�̸���zhirong_lu@192.192.192.200:/wdmycloud/anchordx_cloud/��ӨӨ/
	 * ��׼������Ŀ������Ϣ����������׷�ٱ�Ŀ¼�����أ�������������0�����򷵻�-1.
	 */
	@SuppressWarnings("unused")
	public static int copyExcel()
	{
		try {
			String cmd_Sample_statistics[] = { "rsync", "-aP", "--include=*/", "--include=**/*��������׷�ٱ�*.xls*",
					"--exclude=*", "zhirong_lu@192.192.192.220:/wdmycloud/anchordx_cloud/��ӨӨ/��׼������Ŀ������Ϣ����������׷�ٱ�", "." };
			Process process = Runtime.getRuntime().exec(cmd_Sample_statistics);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = input.readLine()) != null) { // ѭ������ϵͳ�������ݣ���֤ϵͳ�����Ѿ���������
				// System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	/**
	 * ���Ҳ�������ӦĿ¼(ȫ��ģʽ)�ķ�����
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
		String Date = formatter_Date.format(now.getTime()); // ��ʽ���������

		// �ж�Ŀ¼���Ƿ�Ϊ��
		if (des_file == null) {
			System.out.println("��Ŀ¼Ϊ�գ�" + des_file.getName());
			return -1;
		} else {
			for (File pathname : des_file.listFiles()) {
				// �����Ŀ¼
				if (pathname.isDirectory()) {

					// ��ָ��·���´�����ӦĿ¼
					String dir_name = pathname.getName();
					String dir_path_name = target_Path + dir_name + "/Master";
					my_mkdir(dir_path_name); // ����Ŀ¼

					for (File porject_name : pathname.listFiles()) {
						// ������ļ�
						if (porject_name.isFile()) {
							String Suffix = porject_name.getName().substring(porject_name.getName().lastIndexOf(".")); // ��ȡ��׺��
							String Remove_suffix = porject_name.getName().replaceAll(Suffix, ""); // ȥ����׺��

							if (Remove_suffix.contains(excel_part_name) && !Remove_suffix.contains("~$")) {
								String Source_File = porject_name.getParent() + "/" + porject_name.getName(); // ��·����Դ�ļ���
								String Output_File = dir_path_name + "/" + Remove_suffix + "_" + Date + "_All_"
										+ ".txt"; // ���Ŀ����ļ���
								String Source_Output_File = "Master/" + Remove_suffix + "_" + Date + "_All_" + ".txt"; // ���ӵ�Դ�ļ�

								// ��excel�������
								ArrayList<String> Data_list = getFromExcel(Source_File);

								// д���ݵ�����ļ�
								writeFile(Data_list, Output_File);

								// �������ӣ������������ļ�
								try {
									String Link_File_All = target_Path + dir_name + "/" + Remove_suffix + "_All_"
											+ ".txt"; // ��·���������ļ���
									String cmd_All = "ln -s -f " + Source_Output_File + " " + Link_File_All; // Linux����
									Process process = Runtime.getRuntime().exec(cmd_All); // ����Linux����
									BufferedReader input = new BufferedReader(
											new InputStreamReader(process.getInputStream()));
									String line = null;

									// ѭ������ϵͳ�������ݣ���֤ϵͳ�����Ѿ���������
									while ((line = input.readLine()) != null) {
										// System.out.println(line);
									}

								} catch (Exception e) {
									System.out.println("���ӳ���");
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
	 * ���Ҳ�������ӦĿ¼(����ģʽ)�ķ�����
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
		String Date = formatter_Date.format(now.getTime()); // ��ʽ���������

		ArrayList<String> OldDatalist = new ArrayList<String>(); // �ɵ������б�
		ArrayList<String> UpdataDatalist = new ArrayList<String>(); // ��Ҫ���µ������б�

		// �ж�Ŀ¼���Ƿ�Ϊ��
		if (des_file == null) {
			System.out.println("��Ŀ¼Ϊ�գ�" + des_file.getName());
			return -1;
		} else {
			for (File pathname : des_file.listFiles()) {
				// �����Ŀ¼
				if (pathname.isDirectory()) {

					// ��ָ��·���´�����ӦĿ¼
					String dir_name = pathname.getName();
					String dir_path_name = target_Path + dir_name + "/Master";
					my_mkdir(dir_path_name); // ����Ŀ¼

					for (File porject_name : pathname.listFiles()) {
						// ������ļ�
						if (porject_name.isFile()) {
							String Suffix = porject_name.getName().substring(porject_name.getName().lastIndexOf(".")); // ��ȡ��׺��
							String Remove_suffix = porject_name.getName().replaceAll(Suffix, ""); // ȥ����׺��

							if (Remove_suffix.contains(excel_part_name) && !Remove_suffix.contains("~$")) {
								// Դ�ļ�
								String Source_File = porject_name.getParent() + "/" + porject_name.getName(); // ��·����Դ�ļ���
								String Output_File = dir_path_name + "/" + Remove_suffix + "_" + Date + "_All_"
										+ ".txt"; // ���Ŀ����ļ���
								String LastTime_Output_File = target_Path + dir_name + "/" + Remove_suffix + "_All_"
										+ ".txt"; // �ϴ���������ݵ��ļ�
								String Updata_File = dir_path_name + "/" + Remove_suffix + "_" + Date + "_Updata_"
										+ ".txt";// �������ݵ��ļ�
								String Source_All_Output_File = "Master/" + Remove_suffix + "_" + Date + "_All_"
										+ ".txt"; // ���ӵ��������ļ���Դ�ļ�
								String Source_Updata_Output_File = "Master/" + Remove_suffix + "_" + Date + "_Updata_"
										+ ".txt"; // ���ӵĸ��������ļ���Դ�ļ�

								// ����б�����
								OldDatalist.clear();
								UpdataDatalist.clear();
								readLibfile(LastTime_Output_File, OldDatalist); // ���ϴθ��µı�����
								ArrayList<String> Data_list = getFromExcel(Source_File); // ��excel��
								writeFile(Data_list, Output_File); // д���ݵ��������ļ�
								for (int i = OldDatalist.size(); i < Data_list.size(); i++) {
									UpdataDatalist.add(Data_list.get(i)); // ��ȡ��������
								}
								writeFile(UpdataDatalist, Updata_File); // д���ݵ����������ļ�

								// ��������
								try {
									String Link_File_All = target_Path + dir_name + "/" + Remove_suffix + "_All_"
											+ ".txt"; // ��·���������ļ���
									String cmd_All = "ln -s -f " + Source_All_Output_File + " " + Link_File_All; // Linux����
									Process process_All = Runtime.getRuntime().exec(cmd_All); // ����Linux��������������ļ�
									BufferedReader input_All = new BufferedReader(
											new InputStreamReader(process_All.getInputStream()));
									String line_All = null;
									// ѭ������ϵͳ�������ݣ���֤ϵͳ�����Ѿ���������
									while ((line_All = input_All.readLine()) != null) {
										// System.out.println(line);
									}

									String Link_File_Updata = target_Path + dir_name + "/" + Remove_suffix + "_Updata_"
											+ ".txt"; // ��·���������ļ���
									String cmd_Updata = "ln -s -f " + Source_Updata_Output_File + " "
											+ Link_File_Updata; // Linux����
									Process process_Updata = Runtime.getRuntime().exec(cmd_Updata); // ����Linux������Ӹ��������ļ�
									BufferedReader input_Updata = new BufferedReader(
											new InputStreamReader(process_Updata.getInputStream()));
									String line_Updata = null;
									// ѭ������ϵͳ�������ݣ���֤ϵͳ�����Ѿ���������
									while ((line_Updata = input_Updata.readLine()) != null) {
										// System.out.println(line);
									}

								} catch (Exception e) {
									System.out.println("���ӳ���");
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
	 * ���ýű����ϴ��ļ����ƶˡ�
	 * 
	 * @param PutPath
	 */
	@SuppressWarnings("unused")
	public static void upload_File(String PutPath)
	{
		String cmd = "/opt/local/bin/python35/python /var/script/alan/10k_api_script/white_black_collections.py -path "
				+ PutPath;
		try {
			Process process = Runtime.getRuntime().exec(cmd); // ����Linux����
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			// ѭ������ϵͳ�������ݣ���֤ϵͳ�����Ѿ���������
			while ((line = input.readLine()) != null) {
				// System.out.println(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����Ŀ¼�ķ�����
	 * 
	 * @param dir_name
	 */
	public static void my_mkdir(String dir_name)
	{
		File file = new File(dir_name);
		// �ж�Ŀ¼�Ƿ���ڣ�����ļ������ڣ��򴴽�
		if (!file.exists() && !file.isDirectory()) {
			// ����Ŀ¼
			file.mkdirs();
		}
	}

	/**
	 * ���б�����д������ļ��еķ�����
	 * 
	 * @param Data_list
	 * @param Output_File
	 */
	public static void writeFile(ArrayList<String> Data_list, String Output_File)
	{
		try {
			FileWriter fw = new FileWriter(Output_File);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("#Head" + "\r\n");// ���ļ���дͷ��Ϣ
			for (int i = 0; i < Data_list.size(); i++) {
				bw.write(Data_list.get(i) + "\r\n"); // д����
			}
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��xlsx��ʽ�ļ����б�.
	 * 
	 * @param file
	 * @return List
	 * @throws Exception
	 */
	public static ArrayList<String> readXlsx(File file) throws Exception
	{
		ArrayList<String> Data_list = new ArrayList<String>(); // �����б�
		InputStream is = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(is);
		XSSFSheet sheet = null;
		int Sheet_Num = wb.getNumberOfSheets(); // ��ȡ����������

		for (int numSheet = 0; numSheet < Sheet_Num; numSheet++) {
			sheet = wb.getSheetAt(numSheet); // ��ȡ������
			String Sheet_Name = sheet.getSheetName(); // ��ȡ��ǰ����������
			if (Sheet_Name.trim().equals("DNAԤ�Ŀ�")) {
				break;
			} else {
				sheet = null;
			}
		}
		// ��ȡ��ǰ��������ÿһ��
		for (int i = sheet.getFirstRowNum() + 3; i <= sheet.getLastRowNum(); i++) {
			XSSFRow xssfrow = sheet.getRow(i);
			XSSFCell xssfcell0 = xssfrow.getCell(0); // ��ȡ��ǰ�������ĵ�һ��
			XSSFCell xssfcell1 = xssfrow.getCell(1); // ��ȡ��ǰ�������ĵڶ���
			if ((xssfcell0 != null) && (xssfcell0.getStringCellValue().trim().equals("ʾ��"))) {
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
	 * ��xls��ʽ�ļ����б�
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
		int Sheet_Num = wb.getNumberOfSheets(); // ��ȡ����������
		for (int numSheet = 0; numSheet < Sheet_Num; numSheet++) {
			sheet = wb.getSheetAt(numSheet); // ��ȡ������
			String Sheet_Name = sheet.getSheetName(); // ��ȡ��ǰ����������
			if (Sheet_Name.trim().equals("DNAԤ�Ŀ�")) {
				break;
			} else {
				sheet = null;
			}
		}
		// ��ȡ��ǰ��������ÿһ��
		for (int i = sheet.getFirstRowNum() + 3; i <= sheet.getLastRowNum(); i++) {
			HSSFRow hssfrow = (HSSFRow) sheet.getRow(i); // ��ȡ��
			HSSFCell hssfcell0 = hssfrow.getCell(0); // ��ȡ��ǰ�������ĵ�һ��
			HSSFCell hssfcell1 = hssfrow.getCell(1); // ��ȡ��ǰ�������ĵڶ���
			hssfcell0.setCellType(Cell.CELL_TYPE_STRING);
			if ((hssfcell0 != null) && (hssfcell0.getStringCellValue().trim().equals("ʾ��"))) {
				continue;
			} else {
				if (hssfcell1 != null) {
					// ���õ�Ԫ������ΪString���ͣ��Ա��ȡʱ����string���ͣ�Ҳ������
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
	 * ��Excel���ļ���
	 * 
	 * @param filename
	 * @return List
	 */
	public static ArrayList<String> getFromExcel(String filename)
	{
		ArrayList<String> Data_list = new ArrayList<String>(); // �����б�
		String type = filename.substring(filename.lastIndexOf(".") + 1); // ��ȡ�ļ�����
		File file = new File(filename);
		try {
			if (type.equals("xls")) { // �����xls��ʽ�ļ�
				Data_list = readXls(file);
			} else if (type.equals("xlsx")) { // �����xlsx��ʽ�ļ�
				Data_list = readXlsx(file);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Data_list;
	}

	/**
	 * ��txt�ļ������б��Ȼ�󷵻ظ��ļ���ͷ���������ݵķ�����
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

			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.length() != 0) {
						// �ж��Ƿ�Ϊͷ��ʽ������
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
				System.out.println("�Ҳ���ָ�����ļ���" + filePath);
				return "OFF";
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���" + filePath);
			e.printStackTrace();
			return "OFF";
		}
		return Head;
	}
}
