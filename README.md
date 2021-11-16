# Command-Line-Interpreter
This is a "Command Line Interpreter" using java

| Command Name | What Must Be Implemented |
|--------------|--------------------------|
| echo | Takes 1 argument and prints it. |
| pwd | Takes no arguments and prints the current path. |
| cd | Implement all these cases:<br/>1. cd takes no arguments and changes the current path to the path of your home directory.<br/>2. cd takes 1 argument which is “..” (e.g. cd ..) and changes the current directory to the previous directory.<br/>3. cd takes 1 argument which is either the full path or the relative (short) path and changes the current path to that path. |
| ls | Takes no arguments and lists the contents of the current directory sorted alphabetically. |
| ls -r | Takes no arguments and lists the contents of the current directory in reverse order. |
| mkdir | Takes 1 or more arguments and creates a directory for each argument. Each argument can be:<br/>• Directory name (in this case the new directory is created in the current directory)<br/>• Path (full/short) that ends with a directory name (in this case the new directory is created in the given path) |
| rmdir | Implement all these cases:<br/>1. rmdir takes 1 argument which is “*” (e.g. rmdir *) and removes all the empty directories in the current directory.<br/>2. rmdir takes 1 argument which is either the full path or the relative (short) path and removes the given directory only if it is empty. |
