# Command-Line-Interpreter
This is a "Command Line Interpreter" using java

| Command Name | What Must Be Implemented | Command Line |
|--------------|--------------------------|--------------|
| echo | Takes 1 argument and prints it. | echo hello world |
| pwd | Takes no arguments and prints the current path. | pwd |
| cd | Implement all these cases:<br/>1. cd takes no arguments and changes the current path to the path of your home directory.<br/>2. cd takes 1 argument which is “..” (e.g. cd ..) and changes the current directory to the previous directory.<br/>3. cd takes 1 argument which is either the full path or the relative (short) path and changes the current path to that path. | • cd<br/>• cd ..<br/>• cd E:\hp\dir1<br/>• cd hp\dir1 |
| ls | Takes no arguments and lists the contents of the current directory sorted alphabetically. | ls |
| ls -r | Takes no arguments and lists the contents of the current directory in reverse order. | ls -r |
| mkdir | Takes 1 or more arguments and creates a directory for each argument. Each argument can be:<br/>• Directory name (in this case the new directory is created in the current directory)<br/>• Path (full/short) that ends with a directory name (in this case the new directory is created in the given path) | mkdir dir1 dir2 E:\hp\dir3 ....|
| rmdir | Implement all these cases:<br/>1. rmdir takes 1 argument which is “*” (e.g. rmdir *) and removes all the empty directories in the current directory.<br/>2. rmdir takes 1 argument which is either the full path or the relative (short) path and removes the given directory only if it is empty. | • rmdir *<br/>• rmdir dir1<br/>• rmdir E:\hp\dir2 |
| touch | Takes 1 argument which is either the full path or the relative (short) path that ends with a file name and creates this file. | • touch fileName<br/>• touch E:\hp\fileName |
| cp | Takes 2 arguments, both are files and copies the first onto the second. | cp file1.txt file2.txt |
| cp -r | Takes 2 arguments, both are directories (empty or not) and copies the first directory (with all its content) into the second one. | cp -r dir1 E:\hp\dir2 |
| rm | Takes 1 argument which is a file name that exists in the current directory and removes this file. | rm fileName |
| cat | Takes 1 argument and prints the file’s content or takes 2 arguments and concatenates the content of the 2 files and prints it.| • cat file1.txt<br/>• cat file1.txt file2.txt |
| exit | which close the CLI. | exit |
