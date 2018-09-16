# Task2

Список команд:

## rename
Переименовывает файл. Синтаксис: rename [oldName] [newName]    
Пример:  
```
rename test3.txt newText.txt
```
  
## remove
Удаляет файл/дерикторию. Дериктория не может быть удалена если содержит какие то файлы.  
Синтаксис: remove [name]   
Пример:
```
remove newText.txt
```

## cat
Выводит содержимое файла. Синтаксис: cat [name]  
Пример:  
```
cat test.txt
hello
hi
```
  
## size
Выводит размер файла. Синтаксис: size [name]  
Пример:
```
size test.txt
4096
```
  
## ls -key
Выводит список файлов/дерикторий. Синтаксис: ls -[key]  
Значения key:  
-d (все каталоги)  
-f (все файлы)  
-fs (файлы в отсортированном виде, через Collections)  
-all (все содержимое)  

Пример:  
```
ls -f
  virtualDisk\1.zip
  virtualDisk\newText.txt
  virtualDisk\test.txt
ls -all
  virtualDisk\1
  virtualDisk\1.zip
  virtualDisk\2
  virtualDisk\newText.txt
  virtualDisk\test.txt
  virtualDisk\unzipped
```
## cd
Смена деритории. Синтаксис: cd [path]  
Пример:  
```
ls -all
  virtualDisk\1
  virtualDisk\1.zip
  virtualDisk\2
  virtualDisk\newText.txt
  virtualDisk\test.txt
  virtualDisk\unzipped
cd 1
ls -all
  virtualDisk\1\11
  virtualDisk\1\ha.txt
cd 11
ls -all
  virtualDisk\1\11\3.txt
cd ..
ls -all
  virtualDisk\1\11
  virtualDisk\1\ha.txt
```
  
## zip
Архивирует (без сжатия) каталог, ключая вложенные каталоги и файлы.
Пример:  
```
zip 1
ls -all
virtualDisk\1
virtualDisk\1.zip
virtualDisk\2
virtualDisk\newText.txt
virtualDisk\test.txt
```
## unzip
Разархивирует каталог. Резултат будет помещен в папку unzipped
Пример:  
```
unzip 1.zip
```
