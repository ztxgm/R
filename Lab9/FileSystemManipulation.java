import java.nio.file.*;
import java.io.IOException;

public class FileSystemManipulation {
    public static void main(String[] args) throws IOException {
        Path surnameDir = Paths.get("Milekhin");
        
        // a. Создание директории (если не существует)
        if (!Files.exists(surnameDir)) {
            Files.createDirectories(surnameDir);
            System.out.println("a. Создана директория: " + surnameDir);
        } else {
            System.out.println("a. Директория уже существует: " + surnameDir);
        }
        
        // b. Создание файла в директории (если не существует)
        Path nameFile = surnameDir.resolve("Nikita");
        if (!Files.exists(nameFile)) {
            Files.createFile(nameFile);
            System.out.println("b. Создан файл: " + nameFile);
        } else {
            System.out.println("b. Файл уже существует: " + nameFile);
        }
        
        // c. Создание вложенных директорий и копирование файла
        Path[] dirs = {
            surnameDir.resolve("dir1"),
            surnameDir.resolve("dir2"), 
            surnameDir.resolve("dir3")
        };
        
        System.out.println("c. Создание вложенных директорий:");
        for (Path dir : dirs) {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
                System.out.println("   - Создана директория: " + dir.getFileName());
            } else {
                System.out.println("   - Директория уже существует: " + dir.getFileName());
            }
            
            Path targetFile = dir.resolve("Nikita");
            // Используем REPLACE_EXISTING чтобы перезаписать если файл уже существует
            Files.copy(nameFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("   - Файл скопирован в: " + dir.getFileName());
        }
        
        // d. Создание file1 в dir1
        Path file1 = dirs[0].resolve("file1");
        if (!Files.exists(file1)) {
            Files.createFile(file1);
            System.out.println("d. Создан файл file1 в директории dir1: " + file1);
        } else {
            System.out.println("d. Файл file1 уже существует в директории dir1: " + file1);
        }
        
        // e. Создание file2 в dir2
        Path file2 = dirs[1].resolve("file2");
        if (!Files.exists(file2)) {
            Files.createFile(file2);
            System.out.println("e. Создан файл file2 в директории dir2: " + file2);
        } else {
            System.out.println("e. Файл file2 уже существует в директории dir2: " + file2);
        }
        
        // f. Рекурсивный обход директории
        System.out.println("\nf. Рекурсивный обход директории " + surnameDir + ":");
        Files.walk(surnameDir)
            .forEach(path -> {
                String prefix = Files.isDirectory(path) ? "D" : "F";
                String relativePath = surnameDir.relativize(path).toString();
                
                // Если это корневая директория (пустая строка после relativize)
                if (relativePath.isEmpty()) {
                    System.out.println(prefix + " - " + surnameDir.getFileName() + " (корневая директория)");
                } else {
                    System.out.println(prefix + " - " + relativePath);
                }
            });
        
        // g. Удаление dir1 с содержимым
        System.out.println("\ng. Удаление директории dir1 со всем ее содержимым:");
        deleteRecursive(dirs[0]);
        System.out.println("   Директория dir1 и все ее содержимое удалены");
        
        // Показать структуру после удаления
        System.out.println("\nСтруктура после удаления dir1:");
        Files.walk(surnameDir)
            .forEach(path -> {
                String prefix = Files.isDirectory(path) ? "D" : "F";
                String relativePath = surnameDir.relativize(path).toString();
                
                if (relativePath.isEmpty()) {
                    System.out.println(prefix + " - " + surnameDir.getFileName() + " (корневая директория)");
                } else {
                    System.out.println(prefix + " - " + relativePath);
                }
            });
    }
    
    private static void deleteRecursive(Path path) throws IOException {
        Files.walk(path)
            .sorted((p1, p2) -> -p1.compareTo(p2)) // Обратный порядок для удаления файлов перед директориями
            .forEach(p -> {
                try {
                    Files.delete(p);
                    System.out.println("   Удален: " + path.getFileName().relativize(p));
                } catch (IOException e) {
                    System.err.println("   Ошибка при удалении " + p + ": " + e.getMessage());
                }
            });
    }
}