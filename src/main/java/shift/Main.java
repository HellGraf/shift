package shift;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	public static void main(String[] args)
	{
		List<String> input_files = new ArrayList<String>();
		
		List<Long> integers = new ArrayList<Long>();
		List<Float> floats = new ArrayList<Float>();
		List<String> strings = new ArrayList<String>();

		String path = "./";
		String prefix = "";
		boolean append = false;
		boolean stat = false;
		boolean full = false;
		
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals("-o"))
				path = args[++i];
			else if(args[i].equals("-p"))
				prefix = args[++i];
			else if(args[i].equals("-a"))
				append = true;
			else if(args[i].equals("-s"))
				stat = true;
			else if(args[i].equals("-f"))
				full = true;
			else
				input_files.add(args[i]);
		}
		
		for(String name : input_files)
		{
			try(BufferedReader br = Files.newBufferedReader(Paths.get(name), StandardCharsets.UTF_8))
			{
				String line;
				while((line = br.readLine()) != null)
				{
					line = line.trim();
					try
					{
						integers.add(Long.parseLong(line));
					}
					catch(NumberFormatException e)
					{
						try
						{
							floats.add(Float.parseFloat(line));
						}
						catch(NumberFormatException e1)
						{
							strings.add(line);
						}
					}
				} 
			}
			catch(IOException e)
			{
				System.out.println("ОШИБКА! Не удалось прочитать файл " + name + ". (" + e.getMessage() + ")");
				// e.printStackTrace();
			} 
		}
		
		File dir = new File(path);
		if(dir != null)
		{
			if(!dir.exists() && !dir.mkdirs())
			{
				System.out.println("ОШИБКА! Не удалось создать папку " + path + " для записи выходных файлов.");
				return;
			}
			else if(dir.exists() && !dir.isDirectory())
			{
				System.out.println("ОШИБКА! Указанный выходной путь является файлом а не папкой.");
				return;
			}
		}
		
		if(integers.size() > 0)
		{
			long min = Long.MAX_VALUE;
			long max = Long.MIN_VALUE;
			long summ = 0;
			
			File f = new File(path, prefix + "integers.txt");
			try(FileWriter fw = new FileWriter(f, append))
			{
				StringBuilder sb = new StringBuilder();
				for(Long i : integers)
				{
					if(full)
					{
						if(i < min)
							min = i;
	
						if(i > max)
							max = i;
						
						summ += i;
					}
					
					sb.append(i).append("\r\n");
				}
				
				fw.write(sb.toString());
				fw.flush();

				if(stat)
				{
					String item = " целых чисел в файл ";
					
					if(integers.size() % 10 == 1)
						item = " целое число в файл ";
					else if(integers.size() % 10 < 5)
						item = " целых числа в файл ";

					System.out.println("\nЗаписано " + integers.size() + item + prefix + "integers.txt");
				}
				
				if(full)
				{
					System.out.println("\nСтатистика по целым числам:");
					System.out.println("Минимальное: " + min);
					System.out.println("Максимальное: " + max);
					System.out.println("Сумма: " + summ);
					System.out.println("Среднее: " + (summ / integers.size()));
				}
			}
			catch(IOException e)
			{
				System.out.println("ОШИБКА! Не удалось записать данные в файл " + prefix + "integers.txt. (" + e.getMessage() + ")");
				// e.printStackTrace();
			}
		}
		
		if(floats.size() > 0)
		{
			float min = Float.MAX_VALUE;
			float max = Float.MIN_VALUE;
			float summ = 0;
			
			File f = new File(path, prefix + "floats.txt");
			try(FileWriter fw = new FileWriter(f, append))
			{
				StringBuilder sb = new StringBuilder();
				for(Float fl : floats)
				{
					if(full)
					{
						if(fl < min)
							min = fl;
	
						if(fl > max)
							max = fl;
						
						summ += fl;
					}
					
					sb.append(fl).append("\r\n");
				}
				
				fw.write(sb.toString());
				fw.flush();
				
				if(stat)
				{
					String item = " вещественных чисел в файл ";
					
					if(floats.size() % 10 == 1)
						item = " вещественное число в файл ";
					else if(floats.size() % 10 < 5)
						item = " вещественных числа в файл ";

					System.out.println("\nЗаписано " + floats.size() + item + prefix + "floats.txt");
				}
				
				if(full)
				{
					System.out.println("\nСтатистика по вещественным числам:");
					System.out.println("Минимальное: " + min);
					System.out.println("Максимальное: " + max);
					System.out.println("Сумма: " + summ);
					System.out.println("Среднее: " + (summ / floats.size()));
				}
			}
			catch(IOException e)
			{
				System.out.println("ОШИБКА! Не удалось записать данные в файл " + prefix + "floats.txt. (" + e.getMessage() + ")");
				// e.printStackTrace();
			}
		}

		if(strings.size() > 0)
		{
			long min = Long.MAX_VALUE;
			long max = Long.MIN_VALUE;
			
			File f = new File(path, prefix + "strings.txt");
			try(FileWriter fw = new FileWriter(f, append))
			{
				StringBuilder sb = new StringBuilder();
				for(String s : strings)
				{
					if(full)
					{
						if(s.length() < min)
							min = s.length();
	
						if(s.length() > max)
							max = s.length();
					}
					
					sb.append(s).append("\r\n");
				}
				
				fw.write(sb.toString());
				fw.flush();
				
				if(stat)
				{
					String item = " строк в файл ";
					
					if(floats.size() % 10 == 1)
						item = " строка в файл ";
					else if(floats.size() % 10 < 5)
						item = " строки в файл ";

					System.out.println("\nЗаписано " + strings.size() + item + prefix + "strings.txt");
				}
				
				if(full)
				{
					System.out.println("\nСтатистика по строкам:");
					System.out.println("Наименьшая строка содержит " + min + numberSuffix(min));
					System.out.println("Наибольшая строка содержит " + max + numberSuffix(max));
				}
			}
			catch(IOException e)
			{
				System.out.println("ОШИБКА! Не удалось записать данные в файл " + prefix + "strings.txt. (" + e.getMessage() + ")");
				// e.printStackTrace();
			}
		}
	}
	
	private static String numberSuffix(long i)
	{
		String suffix = " символов.";
		
		if(i % 10 == 1)
			suffix = " символ.";
		else if(i % 10 < 5)
			suffix = " символа.";
		
		return suffix;
	}
}
