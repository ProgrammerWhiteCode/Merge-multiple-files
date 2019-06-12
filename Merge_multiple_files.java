import java.io.*;
import java.util.*;

/*
Собираем файл из других файлов, лежащих в одной папке
*/

public class Merge_multiple_files {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the files path: ");
        String directory_name = reader.readLine(); // Считываем имя папки, в которой лежат файлы для объединения
        reader.close();

        File listFile = new File(directory_name);
        File exportFiles[] = listFile.listFiles();
        String[] names = new String[exportFiles.length]; // Создаем массив names
        for (int i = 0; i < names.length; i++) {
            names[i] = exportFiles[i].getName(); // Заполняем массив names именами файлов, лежащих в папке
        }

        Arrays.sort(names, new Comparator<String>() { // Сортируем массив names
            @Override
            public int compare(String o1, String o2) {
                int n1 = extractNumber(o1);
                int n2 = extractNumber(o2);
                return n1 - n2;
            }

            private int extractNumber(String name) {
                int i = 0;
                try {
                    int s = name.indexOf('_') + 1;
                    int e = name.lastIndexOf('.');
                    String number = name.substring(s, e);
                    i = Integer.parseInt(number);
                } catch (Exception e) {
                    i = 0; // если имя файла не соответствует формату, то по умолчанию 0
                }
                return i;
            }
        });

        try {
            String mainFile = directory_name + "/join_file.txt";
            File file = new File(mainFile);
            FileOutputStream outputStream = new FileOutputStream(mainFile);
            for (int i = 0; i < names.length; i++) {
                FileInputStream inputStream = new FileInputStream(directory_name + "/" + names[i]);
                byte[] buffer = new byte[inputStream.available()]; // Читаем весь файл одним куском
                inputStream.read(buffer);
                inputStream.close();
                outputStream.write(buffer);
            }
            outputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}