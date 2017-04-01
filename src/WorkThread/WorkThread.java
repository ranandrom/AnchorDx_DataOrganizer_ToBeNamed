package WorkThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import AnchorDx_CollectData_SearchFiles.AnchorDx_CollectData_SearchFiles;
import AnchorDx_DataOrganizer.AnchorDx_DataOrganizer;


/**
 * <br/>Function: This class is Thread class.
 * <br/>File Name: WorkThread.java
 * <br/>Date: 2017-03-30
 * @author Luzhirong ramandrom@139.com
 * @version V1.0.0
 */
public class WorkThread extends Thread
{
	private String SampleID_File_list_get = null;
	private String Output_File_Black = null;
	private String Output_File_White = null;
	private String Link_File_Black = null;
	private String Link_File_White = null;
	private String Source_File_Black = null;
	private String Source_File_White = null;
	private String Sample_statistics = null;
	private ArrayList <String> Search_Path =  new ArrayList <String>();
	private ArrayList <String> IFILE_List =  new ArrayList <String>();
	private ArrayList<String> list = new ArrayList <String>();
	private ArrayList<String> Black_List = new ArrayList <String>();
	private ArrayList<String> White_List = new ArrayList <String>();
	private ArrayList <String> White_All_Data = new ArrayList<String>();
	private ArrayList <String> Black_All_Data = new ArrayList<String>();
	private ArrayList<String> Updata_SampleID_list = new ArrayList <String>();
	private ArrayList <String> Extension_List = new ArrayList <String>(); // Extension数据列表
	private String Extension_Path = null;
	private String Path1 = null;
	private String Path2 = null;
	private String Path3 = null;
	private String Head = "#SampleID"+"\t"+"Extension";
	private int Pattern = 0;
	
	/**
	 * WorkThread类的构造器。
	 * @param SampleID_File_list_get
	 * @param Pattern
	 */
	public WorkThread(String SampleID_File_list_get, int Pattern)
	{
        super();
        this.SampleID_File_list_get = SampleID_File_list_get;
		this.Extension_Path = "./Extension.txt";
		this.Path1 = "/Src_Data1/nextseq500/outputdata/";
		this.Path2 = "/Src_Data1/x10/outputdata/";
		this.Path3 = "/Src_Data1/analysis/Ironman/";
		this.Pattern = Pattern;
    }
	
	/**
	 * 重写的线程类run方法。
	 */
	public void run()
	{
		AnchorDx_CollectData_SearchFiles.readLibfile(Extension_Path, Extension_List); // 读取Extension数据到列表
				
		File SampleID_File = new File(SampleID_File_list_get);
		File SampleID_File_Path = new File(SampleID_File.getParent());
		String SampleID_PorjectName = SampleID_File_Path.getName();
		String Output_Sub_Directory = null;
		String Extension = null;
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter_Date = new SimpleDateFormat("yyyyMMdd");
		String Date = formatter_Date.format(now.getTime());
		
		int logg1 = 0;
		int logg2 = 0;
		int logg3 = 0;
		int logg4 = 0;
		int logg5 = 0;
		int logg6 = 0;
		int logg7 = 0;
		int logg8 = 0;
		int logg9 = 0;
		int logg10 = 0;	
		int loog = 0;
		
		Calendar now_porject = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(SampleID_PorjectName+"项目开始时间："+ formatter.format(now_porject.getTime()));
				
		String SampleID_Path = SampleID_File.getParent();
		String Master = SampleID_File.getParent() + "/" + "Master";
		AnchorDx_DataOrganizer.my_mkdir( Master ); // 创建Master目录
				
		// 返回文件查找结果
		for (int x = 0; x < Extension_List.size(); x++ ) {
			Search_Path.clear();
			list.clear();
			IFILE_List.clear();
					
			if (Extension_List.get(x).equals("R1_001.fastq.gz") || Extension_List.get(x).equals("R2_001.fastq.gz")) {
				Output_Sub_Directory = "RawFastq";
				Extension = "R[1-2]_001.fastq.gz";
				Search_Path.add(Path1);
				Search_Path.add(Path2);
				if (logg1 == 0) {
					Updata_SampleID_list.clear();
					White_All_Data.clear();
					Black_All_Data.clear();
				}
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg1, loog, Extension, Pattern );
				logg1++;
				if (logg1 == 2) {
					Updata_SampleID_list.clear();
					White_All_Data.clear();
					Black_All_Data.clear();
				}
				loog++;
			} else if (Extension_List.get(x).equals("R1_001.clean.fastq.gz") || Extension_List.get(x).equals("R2_001.clean.fastq.gz")) {
				Output_Sub_Directory = "CleanFastq";
				Extension = "R[1-2]_001.clean.fastq.gz";
				Search_Path.add(Path3);
				if (logg2 == 0) {
					Updata_SampleID_list.clear();
					White_All_Data.clear();
					Black_All_Data.clear();
				}
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg2, loog, Extension, Pattern );
				logg2++;
				if (logg2 == 2) {
					Updata_SampleID_list.clear();
					White_All_Data.clear();
					Black_All_Data.clear();
				}
				loog++;
			} else if (Extension_List.get(x).equals("sorted.deduplicated.bam")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "ProcessedBam";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg3, loog, Extension, Pattern );
				logg3++;
				loog++;
			} else if (Extension_List.get(x).equals("deduplicated_splitting_report.txt")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "BismarkReport";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg4, loog, Extension, Pattern );
				logg4++;
				loog++;
			} else if (Extension_List.get(x).equals("sorted.bam.insertSize.txt")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "RawHsmetrics";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg5, loog, Extension, Pattern );
				logg5++;
				loog++;
			} else if (Extension_List.get(x).equals("sorted.deduplicated.bam.hsmetrics.txt")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "DedupHsmetrics";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg6, loog, Extension, Pattern );
				logg6++;
				loog++;
			} else if (Extension_List.get(x).equals("sorted.deduplicated.bam.insertSize.txt")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "InsertSize";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg7, loog, Extension, Pattern );
				logg7++;
				loog++;
			} else if (Extension_List.get(x).equals("sorted.deduplicated.bam.perTarget.coverage")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "TargetCoverage";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg8, loog, Extension, Pattern );
				logg8++;
				loog++;
			} else if (Extension_List.get(x).equals("LCclassification_res.txt")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "LCclassification";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg9, loog, Extension, Pattern );
				logg9++;
				loog++;
			} else if (Extension_List.get(x).equals("CRclassification_res.txt")) {
				Extension = Extension_List.get(x);
				Output_Sub_Directory = "CRclassification";
				Search_Path.add(Path3);
				Updata_SampleID_list.clear();
				White_All_Data.clear();
				Black_All_Data.clear();
				this.Show_Data(SampleID_Path, Extension_List.get(x), SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, logg10, loog, Extension, Pattern );
				logg10++;
				loog++;
			}		
		}				
	}
	
	/**
	 * 写数据到文件统计表文件。
	 * @param Sample_statistics_head
	 * @param Output_File
	 * @param list
	 * @param log
	 */
	public static void Write_Sample_statistics(String Sample_statistics_head, String Output_File, ArrayList<String> list, int log)
	{
		String encoding = "GBK";
		File file = new File(Output_File);
		ArrayList<String> Day_list = new ArrayList<String>();
		try {
			if (log == 0) {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
				writer.write("#样本名" + "\t" + Sample_statistics_head + "\t" + "Reserve" + "\r\n");
				for (int i = 0; i < list.size(); i++) {
					writer.write(list.get(i) + "\t" + "Reserve" + "\r\n");
				}
				writer.close();
			} else {
				if (file.isFile() && file.exists()) { // 判断文件是否存在
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
					String lineTxt = null;

					while((lineTxt = reader.readLine()) != null) {
						Day_list.add(lineTxt);	
					}
					reader.close();
				} else {
					System.out.println("找不到指定的文件："+Output_File);
					return;
				}
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
				for (int i = 0; i < Day_list.size(); i++) {
					if (i == 0) {
						String str0[] = Day_list.get(i).split("\t");
						String Data = null;
						for (int y = 0; y < str0.length-1; y++) {
							if (y == 0) {
								Data = str0[y];
							} else {
								Data += "\t" + str0[y];
							}
						}
						writer.write(Data + "\t" + Sample_statistics_head + "\t" + "Reserve" + "\r\n");
					} else {
						String str[] = list.get(i-1).split("\t");
						String str1[] = Day_list.get(i).split("\t");
						String Data = null;
						for (int y = 0; y < str1.length-1; y++) {
							if (y == 0) {
								Data = str1[y];
							} else {
								Data += "\t" + str1[y];
							}
						}
						writer.write(Data + "\t" + str[1] + "\t" + "Reserve" + "\r\n");
					}
				}
				writer.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 当一个SampleID对应两个Extension时写数据到输出文件。(全盘模式)
	 * @param Head
	 * @param Output_File
	 * @param list
	 * @param log
	 * @param Extension
	 */
	@SuppressWarnings("resource")
	public static void TwoExtension_write_show( String Head, String Output_File, ArrayList<String> list, int log, String Extension)
	{
		ArrayList<String> Datalist = new ArrayList<String>(); // 数据列表
		String encoding = "GBK";
		// 如果用户不输入OutPutFilePath，则按格式输出到终端
		if (Output_File == null) {
			// 输出头信息
			if (log == 0) {
				System.out.println();
				if (Head == null) {
						System.out.println("#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName");
				} else {
						System.out.println(Head+"\t"+"Folder"+"\t"+"FileName");
				}
				for (int x = 0; x < list.size(); x++) {
					System.out.println(list.get(x));
				}
			} else {
				System.out.println();
				if (Head == null) {
					System.out.println("#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2");
				} else {
					System.out.println(Head+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2");
				}
			}
		} else { // 如果用户输入OutPutFilePath，则按格式写到OutPutFilePath.txt文件里
			try {
				if (log == 0) {
					FileWriter fw = new FileWriter(Output_File); // 每次覆盖以前数据
					BufferedWriter bw = new BufferedWriter(fw);
					if (Head != null) {
						bw.write(Head+"\t"+"Folder"+"\t"+"FileName"+"\r\n"); // 往文件上写头信息
					} else {
						String FileHead ="#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName";
						bw.write(FileHead+"\r\n");
					}
					if (list.size() != 0) {
						for (int i = 0; i < list.size(); i++) {
							bw.write(list.get(i)+"\r\n");
						}
					} else {
						return;
					}
					bw.close();
					fw.close();
				} else {
					File Infile = new File(Output_File);					
					if (Infile.isFile() && Infile.exists()) { // 判断文件是否存在
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Infile), encoding));
						String lineTxt = null;
						while((lineTxt = reader.readLine()) != null) {							
							//判断是否为头格式行数据
							if (lineTxt.substring(0,1).equals("#") || lineTxt.substring(0,2).equals("/*") || lineTxt.substring(0,1).equals("@")) {
								continue;
							} else {
								Datalist.add(lineTxt);
							}
						}
						reader.close();

					} else {
						System.out.println("找不到指定的文件："+Output_File);
						return;
					}
					FileWriter ffw = new FileWriter(Output_File); // 每次覆盖以前数据
					BufferedWriter bbw = new BufferedWriter(ffw);
					if (Head != null) {
						bbw.write(Head+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2"+"\r\n"); // 往文件上写头信息
					} else {
						String FileHead ="#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2";
						bbw.write(FileHead+"\r\n");
					}
					if (list.size() != 0) {
						for (int i = 0; i < Datalist.size(); i++) {
							String str1[] = Datalist.get(i).split("\t");
							for (int j = 0; j < list.size(); j++) {
								String str2[] = list.get(j).split("\t");
								if (str1[0].equals(str2[0])) {
									bbw.write(Datalist.get(i)+"\t"+str2[str2.length-1]+"\r\n");
								} else {
									continue;
								}
							}
						}
					} else {
						for (int i = 0; i < Datalist.size(); i++) {
							bbw.write(Datalist.get(i)+"\r\n");
						}
					}
					bbw.close();
					ffw.close();
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 当一个SampleID对应两个Extension时写数据到输出文件。(更新模式)
	 * @param Head
	 * @param Input_File
	 * @param Output_File
	 * @param list
	 * @param log
	 * @param Extension
	 */
	public static void Updata_TwoExtension_write_show( String Head, String Input_File, String Output_File, ArrayList<String> list, int log, String Extension)
	{		
		ArrayList<String> Datalist = new ArrayList<String>(); // 数据列表
		String encoding = "GBK";
		// 如果用户不输入OutPutFilePath，则按格式输出到终端
		if (Output_File == null) {
			// 头信息
			if (log == 0) {
				System.out.println();
				if (Head == null) {
						System.out.println("#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName");
				} else {
						System.out.println(Head+"\t"+"Folder"+"\t"+"FileName");
				}
				for (int x = 0; x < list.size(); x++) {
					System.out.println(list.get(x));
				}
			} else {
				System.out.println();
				if (Head == null) {
					System.out.println("#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2");
				} else {
					System.out.println(Head+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2");
				}
			}
		} else { // 如果用户输入OutPutFilePath，则按格式写到OutPutFilePath.txt文件里
			try {
				File Infile = new File(Input_File);				
				if (Infile.isFile() && Infile.exists()) { // 判断文件是否存在
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Infile), encoding));
					String lineTxt = null;
					while((lineTxt = reader.readLine()) != null) {						
						// 判断是否为头格式行数据
						if (lineTxt.substring(0,1).equals("#") || lineTxt.substring(0,2).equals("/*") || lineTxt.substring(0,1).equals("@")) {
							continue;
						} else {
							Datalist.add(lineTxt);
						}
					}
					reader.close();
				} else {
					System.out.println("找不到指定的文件："+Input_File);
					return;
				}				
				if (log == 0) {
					FileWriter fw = new FileWriter(Output_File); // 每次覆盖以前数据
					BufferedWriter bw = new BufferedWriter(fw);
					if (Head != null) {
						bw.write(Head+"\t"+"Folder"+"\t"+"FileName"+"\r\n"); // 往文件上写头信息
					} else {
						String FileHead ="#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName";
						bw.write(FileHead+"\r\n");
					}
					if (list.size() != 0) {
						for (int i = 0; i < Datalist.size(); i++) {
							bw.write(Datalist.get(i)+"\r\n");
						}
						for (int i = 0; i < list.size(); i++) {
							bw.write(list.get(i)+"\r\n");
						}
					} else {
						for (int i = 0; i < Datalist.size(); i++) {
							bw.write(Datalist.get(i)+"\r\n");
						}
					}
					bw.close();
					fw.close();
				} else {
					FileWriter ffw = new FileWriter(Output_File); // 每次覆盖以前数据
					BufferedWriter bbw = new BufferedWriter(ffw);
					if (Head != null) {
						bbw.write(Head+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2"+"\r\n"); // 往文件上写头信息
					} else {
						String FileHead ="#SampleID"+"\t"+"Extension"+"\t"+"Folder"+"\t"+"FileName_1"+"\t"+"FileName_2";
						bbw.write(FileHead+"\r\n");
					}
					if (list.size() != 0) {
						for (int i = 0; i < Datalist.size(); i++) {
							String str1[] = Datalist.get(i).split("\t");
							for (int j = 0; j < list.size(); j++) {
								String str2[] = list.get(j).split("\t");
								if (str1[0].equals(str2[0])) {
									bbw.write(Datalist.get(j)+"\t"+str2[str2.length-1]+"\r\n");
								} else {
									continue;
								}
							}
						}
					} else {
						for (int i = 0; i < Datalist.size(); i++) {
							bbw.write(Datalist.get(i)+"\r\n");
						}
					}
					bbw.close();
					ffw.close();
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 全盘模式处理计算数据。
	 * @param SampleID_Path
	 * @param Extension_Data
	 * @param SampleID_PorjectName
	 * @param Output_Sub_Directory
	 * @param Search_Path
	 * @param Date
	 * @param loggn
	 * @param loog
	 * @param Extension
	 */
	@SuppressWarnings("unused")
	public void All_Show_Data(String SampleID_Path, String Extension_Data, String SampleID_PorjectName, String Output_Sub_Directory, ArrayList <String> Search_Path, String Date, int loggn, int loog, String Extension)
	{
		String dir_name = SampleID_Path + "/" + Output_Sub_Directory;
		String Sample_statistics_head = Extension_Data + "_" + Output_Sub_Directory;
		ArrayList <String> day_list = new ArrayList <String>();
		AnchorDx_DataOrganizer.my_mkdir(dir_name); // 创建输出子目录
		Black_List.clear();
		White_List.clear();
		AnchorDx_CollectData_SearchFiles.readLibfile(SampleID_File_list_get, IFILE_List);
		list = AnchorDx_CollectData_SearchFiles.Return_FilePath(IFILE_List, Search_Path, day_list, Extension_Data, Extension);
		Separate_Black_White_List(list, Black_List, White_List);
		Output_File_Black = SampleID_Path + "/Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_Black.txt";
		Output_File_White = SampleID_Path + "/Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_White.txt";
		Source_File_Black = "Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_Black.txt";
		Source_File_White = "Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_White.txt";
		Link_File_Black = SampleID_Path  + "/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + "Black.txt";
		Link_File_White = SampleID_Path  + "/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + "White.txt";
		Sample_statistics  = SampleID_Path + "/Master/" + SampleID_PorjectName + "_" + "样本统计表_" + Date + "_.txt";
		String Source_Sample_statistics = "Master/" + SampleID_PorjectName + "_" + "样本统计表_" + Date + "_.txt";
		String Link_Sample_statistics = SampleID_Path + "/" + SampleID_PorjectName + "_" + "样本统计表" + ".txt";
		// 显示文件查找结果
		if (Extension.equals("R[1-2]_001.fastq.gz") || Extension.equals("R[1-2]_001.clean.fastq.gz")) {
			TwoExtension_write_show( Head, Output_File_Black, Black_List, loggn, Extension);
			TwoExtension_write_show( Head, Output_File_White, White_List, loggn, Extension);
		} else {
			AnchorDx_CollectData_SearchFiles.write_show(Head, Output_File_Black, Black_List, loggn);
			AnchorDx_CollectData_SearchFiles.write_show(Head, Output_File_White, White_List, loggn);
		}
		MyLink(White_List, dir_name);
		Write_Sample_statistics(Sample_statistics_head, Sample_statistics, day_list, loog);
		try {
			String line = null;
			
			String cmd_Black = "ln -s -f " + Source_File_Black + " " + Link_File_Black;
			Process process_Black = Runtime.getRuntime().exec(cmd_Black); // 链接黑名单
			BufferedReader input_Black = new BufferedReader(new InputStreamReader(process_Black.getInputStream()));
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input_Black.readLine()) != null) {
				// System.out.println(line);
			}
			
			String cmd_White = "ln -s -f " + Source_File_White + " " + Link_File_White;
			Process process_White = Runtime.getRuntime().exec(cmd_White); // 链接白名单
			BufferedReader input_White = new BufferedReader(new InputStreamReader(process_White.getInputStream()));
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input_White.readLine()) != null) {
				// System.out.println(line);
			}
			
			String cmd_Sample_statistics = "ln -s -f " + Source_Sample_statistics + " " + Link_Sample_statistics;
			Process process_Sample_statistics = Runtime.getRuntime().exec(cmd_Sample_statistics); // 链接样本统计表
			BufferedReader input_Sample_statistics = new BufferedReader(new InputStreamReader(process_Sample_statistics.getInputStream()));
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input_Sample_statistics.readLine()) != null) {
				// System.out.println(line);
			}
			
		} catch (Exception e) {
			System.out.println("链接出错！");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取白名单SampleID列表
	 * @param White_All_Data
	 * @return
	 */
	public static ArrayList<String> Read_White_List(ArrayList<String> White_All_Data)
	{
		ArrayList<String> SampleID_Data = new ArrayList<String>(); // SampleID数据列表
		for (int i = 0; i < White_All_Data.size(); i++) {
			String str[] = White_All_Data.get(i).split("\t");
			if (SampleID_Data.contains(str[0])) {
				continue;
			} else {
				SampleID_Data.add(str[0]);
			}
		}
		return SampleID_Data;
	}
	
	/**
	 * 获取需要更新的SampleID列表（根据黑名单获取）
	 * @param Updata_File_Path
	 * @param Updata_File_part_name
	 * @param Black_SampleID_list
	 * @return
	 */
	@SuppressWarnings("unused")
	public static ArrayList<String>  Updata_Black_List(String Updata_File_Path, String Updata_File_part_name, ArrayList<String> Black_SampleID_list)
	{
		ArrayList<String> Updata_ID_list = new ArrayList <String>();
		ArrayList<String> Updata_Flie_list = new ArrayList <String>();		
		File UFP = new File(Updata_File_Path);
		// 判断目录下是不是空的
		if (UFP == null) {
			System.out.println("该目录为空："+UFP.getName());
			return null;
		} else {
			for (File pathname : UFP.listFiles()) {
				if (pathname.isFile()) { // 如果是目录
					String this_File_name = pathname.getName();
					String Updatafile = pathname.getParent() + "/" + this_File_name;
					
					if (this_File_name.contains(Updata_File_part_name)) {
						AnchorDx_DataOrganizer.readLibfile(Updatafile, Updata_Flie_list);
					} else {
						continue;	
					}
				} else {
					continue;	
				}
			}		
			for (int i = 0; i < Black_SampleID_list.size(); i++) {
				if (Updata_ID_list.contains(Black_SampleID_list.get(i))) {
					continue;
				} else {
					Updata_ID_list.add(Black_SampleID_list.get(i));
				}
			}
			for (int i = 0; i < Updata_Flie_list.size(); i++) {
				if (Updata_ID_list.contains(Updata_Flie_list.get(i))) {
					continue;
				} else {
					Updata_ID_list.add(Updata_Flie_list.get(i));
				}
			}
		}
		return Updata_ID_list;
	}
	
	/**
	 * 更新样本统计表.
	 * @param Sample_statistics_head
	 * @param Input_File
	 * @param Output_File
	 * @param Upday_list
	 */
	public static void Updata_Sample_statistics(String Sample_statistics_head, String Input_File, String Output_File, ArrayList<String> Upday_list)
	{
		String encoding = "GBK";
		File Infile = new File(Input_File);
		File Outfile = new File(Output_File);
		ArrayList<String> Day_list = new ArrayList<String>();
		ArrayList<String> UD_list = new ArrayList<String>();
		try {
			Day_list.clear();
			UD_list.clear();
			if (Infile.isFile() && Infile.exists()) { // 判断文件是否存在
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Infile), encoding));
				String lineTxt = null;
				while((lineTxt = reader.readLine()) != null) {
					Day_list.add(lineTxt);
				}
				reader.close();
			} else {
				System.out.println("找不到指定的文件："+Input_File);
				return;
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Outfile), encoding));
			for (int i = 0; i < Upday_list.size(); i++) {
				int x = 0;
				int log = 0;
				String str[] = Upday_list.get(i).split("\t");
				for (int k = 0; k < Day_list.size(); k++) {
					if (k == 0) {
						String str0[] = Day_list.get(k).split("\t");
						for (int j = 0; j < str0.length-1; j++) {
							if (str0[j].equals(Sample_statistics_head)) {
								x = j;
							} else {
								continue;
							}
						}
					} else {
						String str1[] = Day_list.get(k).split("\t");
						String Data = null;
						if (str1[0].equals(str[0])) {
							str1[x] = str[1];
							for (int j = 0; j < str1.length; j++) {
								if (j == 0) {
									Data = str1[j];
								} else {
									Data += "\t" + str1[j];
								}
							}
							UD_list.add(Data);
							log = 1;
							continue;
						} else {
							continue;
						}
					}
				}
				if (log == 0) {
					String str_null[] = Day_list.get(0).split("\t");
					String Data = null;
					for (int j = 0; j < str_null.length-1; j++) {
						if (j == 0) {
							Data = str[j];
						} else if (j == x) {
							Data += "\t"+str[1];
						} else {
							Data += "\t" + "NA";
						}
					}
					Day_list.add(Data + "\t" + "Reserve");
				}
			}
			for (int i = 0; i < Day_list.size(); i++) {
				int logg = 0;
				String strD[] = Day_list.get(i).split("\t");
				if (i == 0) {
					writer.write(Day_list.get(i) + "\r\n");	
				} else {
					for (int t = 0; t < UD_list.size(); t++) {
						String strU[] = UD_list.get(t).split("\t");
						if (strD[0].equals(strU[0])) {
							writer.write(UD_list.get(t) + "\r\n");
							logg = 1;
							continue;
						} else {
							continue;
						}
					}
					if (logg == 0) {
						writer.write(Day_list.get(i) + "\r\n");
					}
				}
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新模式处理计算数据。
	 * @param SampleID_Path
	 * @param Extension_Data
	 * @param SampleID_PorjectName
	 * @param Output_Sub_Directory
	 * @param Search_Path
	 * @param Date
	 * @param loggn
	 * @param loog
	 * @param Extension
	 */
	@SuppressWarnings("unused")
	public void Updata_Show_Data(String SampleID_Path, String Extension_Data, String SampleID_PorjectName, String Output_Sub_Directory, ArrayList <String> Search_Path, String Date, int loggn, int loog, String Extension)
	{
		String dir_name = SampleID_Path + "/" + Output_Sub_Directory;
		String Sample_statistics_head = Extension_Data + "_" + Output_Sub_Directory;
		ArrayList <String> day_list = new ArrayList <String>();
		ArrayList <String> Black_SampleID_list = new ArrayList <String>();
		AnchorDx_DataOrganizer.my_mkdir(dir_name); // 创建输出子目录
		Black_List.clear();
		White_List.clear();		
		Output_File_Black = SampleID_Path + "/Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_Black.txt";
		Output_File_White = SampleID_Path + "/Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_White.txt";
		Source_File_Black = "Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_Black.txt";
		Source_File_White = "Master/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + Date + "_White.txt";
		Link_File_Black = SampleID_Path  + "/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + "Black.txt";
		Link_File_White = SampleID_Path  + "/" + SampleID_PorjectName + "_" + Output_Sub_Directory + "_FileList_" + "White.txt";
		Sample_statistics  = SampleID_Path + "/Master/" + SampleID_PorjectName + "_" + "样本统计表_" + Date + "_.txt";
		String Source_Sample_statistics = "Master/" + SampleID_PorjectName + "_" + "样本统计表_" + Date + "_.txt";
		String Link_Sample_statistics = SampleID_Path + "/" + SampleID_PorjectName + "_" + "样本统计表" + ".txt";
		String Updata_File_Path = SampleID_Path;
		String Updata_File ="样本处理追踪表v1_广州基准医疗_Updata_.txt";
		
		if (loggn == 0) {
			AnchorDx_CollectData_SearchFiles.readLibfile(Link_File_White, White_All_Data);
			AnchorDx_CollectData_SearchFiles.readLibfile(Link_File_Black, Black_All_Data);
			Black_SampleID_list = Read_White_List(Black_All_Data); // 获取黑名单SampleID列表
			Updata_SampleID_list = Updata_Black_List(Updata_File_Path, Updata_File, Black_SampleID_list);
		}
		list = AnchorDx_CollectData_SearchFiles.Return_FilePath(Updata_SampleID_list, Search_Path, day_list, Extension_Data, Extension);
		Separate_Black_White_List(list, Black_List, White_List);
		for (int i = 0; i < White_List.size(); i++) {
			if (White_All_Data.contains(White_List.get(i))) {
				continue;
			} else {
				White_All_Data.add(White_List.get(i));
			}
		}
		// 显示文件查找结果
		if (Extension.equals("R[1-2]_001.fastq.gz") || Extension.equals("R[1-2]_001.clean.fastq.gz")) {
			TwoExtension_write_show( Head, Output_File_Black, Black_List, loggn, Extension);
			Updata_TwoExtension_write_show( Head, Link_File_White, Output_File_White, White_List, loggn, Extension);
		} else {
			AnchorDx_CollectData_SearchFiles.write_show(Head, Output_File_Black, Black_List, 0);
			AnchorDx_CollectData_SearchFiles.write_show(Head, Output_File_White, White_All_Data, 0);
		}
		MyLink(White_List, dir_name); // 做软连接
		Updata_Sample_statistics(Sample_statistics_head, Link_Sample_statistics, Sample_statistics, day_list);
		try {
			String line = null;
			
			String cmd_Black = "ln -s -f " + Source_File_Black + " " + Link_File_Black;
			Process process_Black = Runtime.getRuntime().exec(cmd_Black); // 链接黑名单
			BufferedReader input_Black = new BufferedReader(new InputStreamReader(process_Black.getInputStream()));
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input_Black.readLine()) != null) {
				// System.out.println(line);
			}
			
			String cmd_White = "ln -s -f " + Source_File_White + " " + Link_File_White;
			Process process_White = Runtime.getRuntime().exec(cmd_White); // 链接白名单
			BufferedReader input_White = new BufferedReader(new InputStreamReader(process_White.getInputStream()));
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input_White.readLine()) != null) {
				// System.out.println(line);
			}
			
			String cmd_Sample_statistics = "ln -s -f " + Source_Sample_statistics + " " + Link_Sample_statistics;
			Process process_Sample_statistics = Runtime.getRuntime().exec(cmd_Sample_statistics); // 链接样本统计表
			BufferedReader input_Sample_statistics = new BufferedReader(new InputStreamReader(process_Sample_statistics.getInputStream()));
			// 循环读出系统返回数据，保证系统调用已经正常结束
			while ((line = input_Sample_statistics.readLine()) != null) {
				// System.out.println(line);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("链接出错！");	
		}
	}
	
	/**
	 * 分离黑白名单列表
	 * @param Source_List
	 * @param Black_List
	 * @param White_List
	 */
	public static void Separate_Black_White_List(ArrayList <String> Source_List, ArrayList <String> Black_List, ArrayList <String> White_List)
	{
		for (int i = 0; i < Source_List.size(); i++) {
			String str[] = Source_List.get(i).split("\t");
			if (str[2].equals("NA")) {
				Black_List.add(Source_List.get(i));
			} else {
				White_List.add(Source_List.get(i));
			}
		}
	}
	
	/**
	 * 做软连接
	 * @param InputList
	 * @param Link_Path
	 */
	@SuppressWarnings("unused")
	public static void MyLink(ArrayList <String> InputList, String Link_Path)
	{
		try {
			String Source_File = null;
			for (int i = 0; i < InputList.size(); i++) {
				String str[] = InputList.get(i).split("\t");
				if (!(str[2].equals("NA"))) {
					Source_File = str[2]+"/"+str[3];
					String cmd = "ln -s -f " + Source_File + " " + Link_Path + "/" + str[3];
					Process process = Runtime.getRuntime().exec(cmd);
					BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = "";
					// 循环读出系统返回数据，保证系统调用已经正常结束
					while ((line = input.readLine()) != null) {
						// System.out.println(line);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("链接出错！");
		}
	}
	
	/**
	 * 根据选择模式显示结果。
	 * @param SampleID_Path
	 * @param Extension_Data
	 * @param SampleID_PorjectName
	 * @param Output_Sub_Directory
	 * @param Search_Path
	 * @param Date
	 * @param loggn
	 * @param loog
	 * @param Extension
	 * @param Pattern
	 */
	public void Show_Data(String SampleID_Path, String Extension_Data, String SampleID_PorjectName, String Output_Sub_Directory, ArrayList <String> Search_Path, String Date, int loggn, int loog, String Extension, int Pattern)
	{
		if (Pattern == 0) {
			// 全盘模式
			this.All_Show_Data(SampleID_Path, Extension_Data, SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, loggn, loog, Extension );
		} else {
			// 更新模式
			this.Updata_Show_Data(SampleID_Path, Extension_Data, SampleID_PorjectName, Output_Sub_Directory, Search_Path, Date, loggn, loog, Extension );
		}
	}
}
