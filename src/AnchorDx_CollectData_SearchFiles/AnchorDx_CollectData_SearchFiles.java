package AnchorDx_CollectData_SearchFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import WorkThread.WorkThread;

/**
 * <br/>
 * Function: This class is collect data and search files. <br/>
 * File Name: AnchorDx_CollectData_SearchFiles.java <br/>
 * Date: 2017-03-30
 * 
 * @author Luzhirong ramandrom@139.com
 * @version V1.0.0
 */

public class AnchorDx_CollectData_SearchFiles
{
	/**
	 * 创建线程池提交任务以及删除中间文件的方法。
	 * 
	 * @param SampleID_Path
	 * @param Pattern
	 */
	@SuppressWarnings("unused")
	public static void mainFunction(String SampleID_Path, int Pattern)
	{
		System.out.println();
		File des_file = new File(SampleID_Path);
		String SampleID_File_part_name = "样本处理追踪表v1_广州基准医疗_All_.txt"; // SampleID文件名中的部分字符串
		ArrayList<String> SampleID_File_list = searchSampleIDFile(des_file, SampleID_File_part_name);// 获取SampleID文件列表

		ExecutorService exe = Executors.newFixedThreadPool(20); // 设置线程池最大线程数为20
		for (int i = 0; i < SampleID_File_list.size(); i++) {
			exe.execute(new WorkThread(SampleID_File_list.get(i), Pattern)); // 向线程池提交任务
		}
		exe.shutdown(); // 关闭线程池
		while (true) {
			if (exe.isTerminated()) { // 先让所有的子线程运行完，再运行主线程
				String cmd = "rm -r ./基准所有项目收样信息表样本处理追踪表";
				try {
					Process process = Runtime.getRuntime().exec(cmd);
					BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;
					while ((line = input.readLine()) != null) { // 循环读出系统返回数据，保证系统调用已经正常结束
						// System.out.println(line);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("主线程睡醒出错！ ");
			}
		}
		System.out.println();
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
					// 判断是否为头格式行数据
					if (lineTxt.length() != 0) {
						if (lineTxt.substring(0, 1).equals("#") || lineTxt.substring(0, 2).equals("/*")
								|| lineTxt.substring(0, 1).equals("@")) {
							Head = lineTxt;
							continue;
						}
						if (IFILE.contains(lineTxt)) {
							continue;
						} else {
							IFILE.add(lineTxt);
						}
					}
				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错：" + filePath);
			e.printStackTrace();
			return "OFF";
		}
		return Head;
	}

	/**
	 * 判断一个Linux下的文件是否为链接文件，是返回true ,否则返回false
	 * 
	 * @param file
	 * @return boolean
	 */
	public static boolean isLink(File file)
	{
		String cPath = "";
		try {
			cPath = file.getCanonicalPath(); // 获取规范路径名
		} catch (Exception ex) {
			System.out.println("文件异常：" + file.getAbsolutePath()); // 获取绝对路径名
		}
		return !cPath.equals(file.getAbsolutePath());
	}

	/**
	 * 调用Linux命令获取符合要求的文件列表(跳过链接文件)的方法。
	 * 
	 * @param Path
	 * @param Extension
	 * @param list
	 */
	public static void findFile(String Path, String Extension, ArrayList<String> list)
	{
		try {
			String tar = "*" + Extension;
			String cmd = "find " + Path + " -type f -name " + tar;
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = input.readLine()) != null) {
				list.add(line);
			}
		} catch (Exception e) {
			System.out.println("linux命令异常！");
		}
	}

	/**
	 * 在数据列表中匹配SampleID。
	 * 
	 * @param list_Path
	 * @param SampleID
	 * @param Extension
	 * @return List
	 */
	public static ArrayList<String> getContainssWordFile(ArrayList<String> list_Path, String SampleID, String Extension)
	{
		ArrayList<String> list = new ArrayList<String>(); // 数据结果列表
		for (int x = 0; x < list_Path.size(); x++) {
			File file = new File(list_Path.get(x));
			String Folder = file.getParent(); // 获取文件的绝对路径
			String FileName = file.getName(); // 文件文件名
			if (FileName.contains(SampleID)) {
				String str = SampleID + "\t" + Extension + "\t" + Folder + "\t" + FileName;
				list.add(str);
			} else {
				continue;
			}
		}
		return list;
	}

	/**
	 * 查找SampleID文件列表.
	 * 
	 * @param des_file
	 * @param SampleID_File_part_name
	 * @return List
	 */
	@SuppressWarnings("null")
	public static ArrayList<String> searchSampleIDFile(File des_file, String SampleID_File_part_name)
	{
		ArrayList<String> SampleID_list = new ArrayList<String>(); // SampleID文件列表
		// 判断目录下是不是空的
		if (des_file == null) {
			System.out.println("该目录为空：" + des_file.getName());
			return null;
		} else {
			for (File pathname : des_file.listFiles()) {
				if (pathname.isDirectory()) { // 如果是目录
					for (File porject_name : pathname.listFiles()) {
						if (porject_name.isFile()) { // 如果是文件
							String this_SampleID_name = porject_name.getName();
							if (this_SampleID_name.contains(SampleID_File_part_name)) {
								String SampleID_Path = porject_name.getParent() + "/" + this_SampleID_name; // 带路径的文件名
								SampleID_list.add(SampleID_Path);
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
		return SampleID_list;
	}

	/**
	 * 读取修改时间的方法  
	 * 
	 * @param file
	 * @return String
	 */
	public static String getModifiedTime(String file)
	{
		File f = new File(file);
		Calendar cal = Calendar.getInstance();
		long time = f.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		cal.setTimeInMillis(time);
		return formatter.format(cal.getTime());
	}

	/**
	 * 返回文件查找结果
	 * 
	 * @param IFILE_List
	 * @param Search_Path
	 * @param Day_list
	 * @param Extension_Data
	 * @param Extension
	 * @return List
	 */
	public static ArrayList<String> Return_FilePath(ArrayList<String> IFILE_List, ArrayList<String> Search_Path,
			ArrayList<String> Day_list, String Extension_Data, String Extension)
	{
		ArrayList<String> FileName_List = new ArrayList<String>();
		ArrayList<String> list_Path = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		String SampleID = null;

		list_Path.clear();
		for (int y = 0; y < Search_Path.size(); y++) {
			findFile(Search_Path.get(y), Extension_Data, list_Path); // 获取符合要求的文件列表(跳过链接文件)
		}
		for (int i = 0; i < IFILE_List.size(); i++) {
			SampleID = IFILE_List.get(i);
			list.clear();
			int log = 0;

			list = getContainssWordFile(list_Path, SampleID, Extension); // 在数据列表中匹配SampleID
			if (list.size() == 0) {
				String str = SampleID + "\t" + Extension + "\t" + "NA" + "\t" + "NA";
				FileName_List.add(str);

				String day_data = SampleID + "\t" + "NA";
				Day_list.add(day_data);
			} else {
				for (int x = 0; x < list.size(); x++) {
					if (FileName_List.contains(list.get(x))) {
						continue;
					} else {
						FileName_List.add(list.get(x));
						if (log == 0) {
							String strr[] = list.get(x).split("\t");
							String file = strr[2] + "/" + strr[3];
							String Day = getModifiedTime(file); // 文件修改时间
							String day_data = SampleID + "\t" + Day;
							Day_list.add(day_data);
							log++;
						}
						continue;
					}
				}
			}
		}
		return FileName_List;
	}

	/**
	 * 写数据到输出文件，或把数据显示到终端上的方法。
	 * 
	 * @param Head
	 * @param Output_File
	 * @param list
	 * @param log
	 */
	public static void write_show(String Head, String Output_File, List<String> list, int log)
	{
		// 如果用户不输入OutPutFilePath，则按格式输出到终端
		if (Output_File == null) {
			// 头信息
			if (log == 0) {
				System.out.println();
				if (Head == null) {
					System.out.println("#SampleID" + "\t" + "Extension" + "\t" + "Folder" + "\t" + "FileName");
				} else {
					System.out.println(Head + "\t" + "Folder" + "\t" + "FileName");
				}
			}
			for (int x = 0; x < list.size(); x++) {
				System.out.println(list.get(x));
			}
		} else { // 如果用户输入OutPutFilePath，则按格式写到OutPutFilePath.txt文件里
			try {
				FileWriter fw = new FileWriter(Output_File); // 每次覆盖以前数据;
				BufferedWriter bw = new BufferedWriter(fw);
				if (log == 0) {
					if (Head != null) {
						bw.write(Head + "\t" + "Folder" + "\t" + "FileName" + "\r\n"); // 往文件上写头信息
					} else {
						String FileHead = "#SampleID" + "\t" + "Extension" + "\t" + "Folder" + "\t" + "FileName";
						bw.write(FileHead + "\r\n");
					}
				}
				for (int i = 0; i < list.size(); i++) {
					bw.write(list.get(i) + "\r\n");
				}
				bw.close();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
