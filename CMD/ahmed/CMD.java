import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;

public class CMD {
    /*
        Ahmed Sayed Hassan Youssef      => 20190034
        Khaled Ashraf Hanafy Mahmoud    => 20190186
        Ebrahim Mohamed Ebrahim Hegy    => 20190009
    */

    static class Parser {//echo ahmed sayed
        String commandName = "";
        String[] args;
        ArrayList<String> foundCommand = new ArrayList<>();

        public boolean parse(String input) throws IOException {
            boolean check = false;
            String[] options = input.split(" ");
            args = new String[options.length - 1];
            help();
            if (foundCommand.contains(options[0])) check = true;
            if (check) {
                commandName = options[0];
                for (int i = 1, k = 0; i < options.length; i++, k++) {
                    args[k] = options[i];
                }
            }
            return check;
        }

        public String help() {
            ArrayList<String> s = new ArrayList<>();
            s.add("echo");
            s.add("exit");//
            s.add("pwd");//
            s.add("cd");//
            s.add("cd ..");//
            s.add("ls");//
            s.add("ls -r");//
            s.add("mkdir");/////
            s.add("rmdir *");//
            s.add("rmdir");//
            s.add("touch");//
            s.add("cp");//
            s.add("cp -r");//
            s.add("rm");//e
            s.add("cat");//
            s.add(">");//
            s.add(">>");//
            foundCommand.addAll(s);
            return s.toString();
        }

        public String getCommandName() {
            return commandName;
        }

        public String[] getArgs() {
            return args;
        }
    }

    static class Terminal {
        Parser parser;
        public int checkCount = 0;
        public int checkCount2 = 0;
        private String path;

        public Terminal() {
            this.path = System.getProperty("user.dir");
        }

        public String echo(String e) {
            if (checkCount2 == 0) {
                System.out.println(e);
                return e;
            }
            checkCount2 = 0;
            return e;
        }

        public String pwd() {
            path = getPath();
            return path;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void cd(String[] args) {//cd cd ..
            String p = getPath();//ahmedsayed\\tst
            if ((args[0].equals(".."))) {
                int l = p.length();
                while ((l) >= 0) {
                    if (p.charAt(l - 1) != '\\') {
                        p = p.substring(0, (l - 1));
                        l--;
                    } else {
                        p = p.substring(0, (l - 1));
                        break;
                    }
                }
                echo(p);
                setPath(p);//cd ..
            } else {//    E:\CS\Operating Systems\Assignments\CMD   [E:\CS\Operating ,Systems\Assignments\CMD]
                if (args[0].contains(":")) {//long
                    StringBuilder sub = new StringBuilder(Arrays.toString(args));
                    for (int i = 0; i < sub.length(); i++) {
                        if (sub.indexOf(",") != -1) {
                            sub.deleteCharAt(sub.indexOf(","));
                        }
                    }
                    sub = new StringBuilder(sub.substring(1, sub.length() - 1));
                    setPath(sub.toString());
                    echo(sub.toString());
                } else {//cd out\production
                    StringBuilder sub = new StringBuilder(Arrays.toString(args));
                    for (int i = 0; i < sub.length(); i++) {
                        if (sub.indexOf(",") != -1) {
                            sub.deleteCharAt(sub.indexOf(","));
                        }
                    }
                    sub = new StringBuilder(sub.substring(1, sub.length() - 1));
                    setPath(p + "\\" + sub);
                    echo(p + "\\" + sub);
                }

            }
        }

        public String cd() {
            path = "user.dir";
            path = System.getProperty(path);
            return path;
        }

        //تصاعدي
        public String ls() {
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            String content = "";
            if (checkCount == 0) {
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        System.out.println("---------------------------------\n\tFile\nFile name -> : " + listOfFiles[i].getName() + "\nFile path -> : " + listOfFiles[i].getPath() + "\n----------------------------------");
                    } else if (listOfFiles[i].isDirectory()) {
                        System.out.println("---------------------------------\n\tFolder\nFolder name -> : " + listOfFiles[i].getName() + "\nFolder path -> : " + listOfFiles[i].getPath() + "\n----------------------------------");
                    }
                    content += listOfFiles[i].getName();
                    content += "\n";
                }
            } else {
                for (int i = 0; i < listOfFiles.length; i++) {
                    content += listOfFiles[i].getName();
                    content += "\n";
                }
            }
            checkCount = 0;
            return content;
        }

        //تنازلي
        public void lsr() {
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();

            for (int i = (listOfFiles.length) - 1; i >= 0; i--) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("---------------------------------\n\tFile\nFile name -> : " + listOfFiles[i].getName() + "\nFile path -> : " + listOfFiles[i].getPath() + "\n----------------------------------");
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("---------------------------------\n\tFolder\nFolder name -> : " + listOfFiles[i].getName() + "\nFolder path -> : " + listOfFiles[i].getPath() + "\n----------------------------------");
                }
            }
        }

        private int countSpaces(String s) {
            int spaces = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') spaces++;
            }
            return spaces;
        }

        public void mkdir(String s) throws IOException {
            //ahmed.txt ahned.cpp ah.pdf
            int spaces = countSpaces(s);
            int i = 0;
            for (int j = 0; j <= spaces; j++) {
                String sub = "";
                while (s.charAt(i) != ' ') {
                    sub += s.charAt(i);
                    i++;
                    if (i == s.length()) break;
                }
                i++;
                boolean check = false;
                for (int k = 0; k < sub.length(); k++) {
                    if (sub.charAt(k) == '.') {
                        check = true;
                        break;
                    }
                }
                File newFile = null;
                if (check) {
                    newFile = new File(path + "\\" + sub);
                    if (newFile.createNewFile()) {
                        echo(sub + " created");
                    } else {
                        echo(sub + " is already exist");
                    }
                } else {//  E:\CS\Operating Systems\Assignments\CMD
                    if (s.contains(":\\")) {//long
                        int size = s.length() - 1;
                        StringBuilder temp = new StringBuilder("");
                        while (s.charAt(size) != '\\') {
                            temp.append(s.charAt(size));
                            size--;
                        }
                        temp.reverse();
//                    System.out.println(temp);
                        if (new File(s).exists()) {
                            echo(temp + " is already exist");
                            break;
                        } else {
                            Files.createDirectories(Paths.get(s));
                            echo(temp + " is created");
                            break;
                        }
                    } else if (s.contains("\\") && !s.contains(":")) { //   CS\Operating Systems\Assignments\CMD
                        int l = s.length();
                        while ((l) >= 0) {
                            if (s.charAt(l - 1) != '\\') {
                                s = s.substring(0, (l - 1));
                                l--;
                            } else {
                                s = s.substring(0, (l - 1));
                                break;
                            }
                        }
//                    echo(s);//path 2
                        int k = sub.length() - s.length();
                        String temp = "";
                        temp += sub.substring((sub.length() - k) + 1);//    \
//                    System.out.println(path +"\\" +s +"\\" +temp);
                        if (new File(path + "\\" + s + "\\" + temp).exists()) {
                            echo(temp + " is already exist");
                            break;
                        } else {
                            Files.createDirectories(Paths.get(path + "\\" + s + "\\" + temp));
                            echo(temp + " is created");
                            break;
                        }

                    } else {
                        if (new File(path + "\\" + sub).exists()) {
                            echo(sub + " is already exist");
                            break;
                        } else {
                            Files.createDirectories(Paths.get(path + "\\" + sub));
                            echo(sub + " is created");
                            break;
                        }
                    }
                }
            }
        }

        public void rmdir(String s) throws IOException {
            if (s.equals("*")) {
                File f1 = new File(path);
                File[] list = f1.listFiles();
                for (int i = 0; i < list.length; i++) {
                    if (list[i].isDirectory()) {
                        if (list[i].length() == 0) {
                            File d = new File(path + "\\" + list[i].getName());
                            if (d.delete()) {
                                echo(list[i].getName() + " deleted");
                            }
                        }
                    }
                }
            } else {
                //rmdir ahmed.txt Sayed.txt
                int spaces = countSpaces(s);
                int i = 0;
                for (int j = 0; j <= spaces; j++) {
                    String sub = "";
                    while (s.charAt(i) != ' ') {
                        sub += s.charAt(i);
                        i++;
                        if (i == s.length()) break;
                    }
                    i++;
                    boolean check = false;
                    for (int k = 0; k < sub.length(); k++) {
                        if (sub.charAt(k) == '.') {
                            check = true;
                            break;
                        }
                    }
                    File newFile = null;
                    if (check) {
                        newFile = new File(path + "\\" + sub);
                        if (newFile.exists()) {
                            if (newFile.length() == 0) {
                                echo(sub + " is Empty not allowed remove it with this command");
                                break;
                            } else {
                                newFile.delete();
                                echo(sub + " deleted successfully");
                                break;
                            }
                        }
                    } else if (!check) {
                        ////////////////////
                        if (s.contains(":\\")) {
                            int size = s.length() - 1;
                            StringBuilder temp = new StringBuilder("");
                            while (s.charAt(size) != '\\') {
                                temp.append(s.charAt(size));
                                size--;
                            }
                            temp.reverse();
//                        System.out.println(temp);
                            File directory = new File(s);
                            if (new File(s).exists()) {
                                directory.delete();
                                echo(temp + " deleted successfully");
                                break;
                            } else {
                                echo("no such folder to delete");
                                break;
                            }

                        } else if (s.contains("\\") && !s.contains(":")) {
                            int l = s.length();
                            while ((l) >= 0) {
                                if (s.charAt(l - 1) != '\\') {
                                    s = s.substring(0, (l - 1));
                                    l--;
                                } else {
                                    s = s.substring(0, (l - 1));
                                    break;
                                }
                            }
//                        echo(s);//path 2
                            int k = sub.length() - s.length();
                            String temp = "";
                            temp += sub.substring((sub.length() - k) + 1);
//                        System.out.println(path +"\\" +s +"\\" +temp);
                            File directory = new File(path + "\\" + s + "\\" + temp);
                            if (new File(path + "\\" + s + "\\" + temp).exists()) {
                                directory.delete();
                                echo(temp + " deleted successfully");
                                break;
                            } else {
                                echo("no such folder to delete");
                                break;
                            }
                        } else {
                            File directory = new File(path + "\\" + sub);
                            if (directory.length() == 0) {
                                boolean result = directory.delete();
                                if (result) {
                                    echo(sub + " is Deleted");
                                    break;
                                } else {
                                    echo(sub + " not empty to delete");
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }

        public void touch(String paths) throws IOException {
            //  ahmed\\text.txt
            int i = paths.length() - 1;
            StringBuilder file = new StringBuilder();

            while (paths.charAt(i) != '\\') {
                file.append(paths.charAt(i));
                paths = paths.substring(0, i);
                i--;
            }// text.txt
            file.reverse();
            boolean check = false;
            for (int k = 0; k < file.length(); k++) {
                if (file.charAt(k) == '.') {
                    check = true;
                    break;
                }
            }
            File obj = new File(paths);
            if (obj.isDirectory() && check) {
                if (new File(String.valueOf(paths + file)).exists()) {
                    echo("this file already exists before");
                } else {
                    File created = new File(paths + file);
                    created.createNewFile();
                    echo(file + " is created");
                }
            } else if (!check) {
                echo("Enter valid File (Ex. newfile.Extension)");
            } else {
                echo("invalid path!");
            }
        }

        public void cp(File source, File dest) throws IOException {
            File f1 = new File(path + "\\" + source), f2 = new File(path + "\\" + dest);
            if (f1.exists() && f2.exists()) {
                FileInputStream sor = new FileInputStream(path + "\\" + source);
                FileOutputStream des = new FileOutputStream(path + "\\" + dest);
                int i = 0;
                String content = "";
                while ((i = sor.read()) != -1) {
                    content += (char) i;
                }
                sor.close();
                byte[] cons = content.getBytes();
                des.write(cons);
                des.flush();
                des.close();
                echo(source + " copied into " + dest);
            } else {
                if (!f1.exists()) {
                    echo(source + " not exist");
                }
                if (!f2.exists()) {
                    echo(dest + " not exist");
                }
            }
        }

        int v = 0;
        String shared = "";

        public void cpr(File source, File dest) throws IOException {
            File src;
            File des;
            if (v != 0) {
                src = new File(String.valueOf(source));
                v = 0;
                des = new File(String.valueOf(dest) + "\\" + shared);
            } else {
                src = new File(path + "\\" + source);
                des = new File(path + "\\" + dest);
            }

            File[] listOfFiles = src.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    File f = new File(src + "\\" + listOfFiles[i].getName());
                    FileInputStream sor = new FileInputStream(f);
                    int k = 0;
                    String content = "";
                    while ((k = sor.read()) != -1) {
                        content += (char) k;
                    }
                    File o = new File(des + "\\" + listOfFiles[i].getName());
                    o.createNewFile();
                    FileOutputStream dess = new FileOutputStream(o);
                    dess.write(content.getBytes());
                    dess.flush();
                    dess.close();
                } else if (listOfFiles[i].isDirectory()) {

                    File f = new File(des + "\\" + listOfFiles[i].getName());
                    f.mkdir();
                    File srrc = new File(src + "\\" + listOfFiles[i].getName());
                    v++;
                    shared = listOfFiles[i].getName();
                    cpr(srrc, des);
                }
            }
        }

        public void rm(String s) {
            File file = new File(path + "\\" + s);
            if (file.exists()) {
                file.delete();
                echo(s + " deleted Successfully");
            } else {
                echo(s + " Not Found!");
            }

        }

        public void cat(String s1) throws IOException {
            File f1 = new File(path + "\\" + s1);
            if (f1.exists()) {
                FileInputStream file = new FileInputStream(path + "\\" + s1);
                int i = 0;
                String s = "";
                while ((i = file.read()) != -1) {
                    s += (char) i;
                }
                file.close();
                echo(s);
            } else {
                echo("not available files");
            }

        }

        public void cat(String s1, String s2) throws IOException {
            File f1 = new File(path + "\\" + s1);
            File f2 = new File(path + "\\" + s2);

            int i = 0, j = 0;
            String s = "";
            if (f1.exists() && f2.exists()) {
                FileInputStream file1 = new FileInputStream(path + "\\" + s1);
                FileInputStream file2 = new FileInputStream(path + "\\" + s2);

                while ((i = file1.read()) != -1) {
                    s += (char) i;
                }
                s += '\n';
                file1.close();
                while ((j = file2.read()) != -1) {
                    s += (char) j;
                }
                file2.close();
                echo(s);
            } else {
                echo("not available files");
            }
        }

        public void write(Terminal obj, String s, String file, String e) throws IOException {
            checkCount = 1;
            checkCount2 = 1;
            File f1 = new File(path + "\\" + file);
            if (f1.exists()) {
                FileOutputStream out = new FileOutputStream(f1);
                if (s.equals("pwd")) {
                    byte[] output = obj.pwd().getBytes();
                    out.write(output);
                } else if (s.equals("ls")) {
                    byte[] output = obj.ls().getBytes();
                    out.write(output);
                } else if (s.equals("echo")) {
                    byte[] output = obj.echo(e).getBytes();
                    out.write(output);
                }
            } else {
                File f = new File(path + "\\" + file);
                f.createNewFile();
            }
        }

        public void writeAppend(Terminal obj, String s, String file, String e) throws IOException {
            checkCount = 1;
            checkCount2 = 1;

            FileInputStream fin = new FileInputStream(path + "\\" + file);
            int i = 0;
            String str = "";
            while ((i = fin.read()) != -1) {
                str += (char) i;
            }
            FileOutputStream fout = new FileOutputStream(path + "\\" + file);
            File f1 = new File(path + "\\" + file);
            if (f1.exists()) {

                String ob1 = obj.pwd();
                String ob2 = obj.ls();
                String ob3 = obj.echo(e);

                String fn1 = str + "\n" + ob1;
                String fn2 = str + "\n" + ob2;
                String fn3 = str + "\n" + ob3;

                if (s.equals("pwd")) {
                    fout.write(fn1.getBytes());
                } else if (s.equals("ls")) {
                    fout.write(fn2.getBytes());
                } else if (s.equals("echo")) {
                    fout.write(fn3.getBytes());
                }
            } else {
                File f = new File(path + "\\" + file);
                f.createNewFile();
            }
        }

        public void chooseCommandAction() throws IOException {
            Scanner input = new Scanner(System.in);
            Terminal T = new Terminal();
            String options = "";
            while (!(options.equals("exit"))) {
                parser = new Parser();
                System.out.print("> ");
                options = input.nextLine();
                parser.parse(options);
                String command = parser.getCommandName();
                String[] args = parser.getArgs();
                /////////////////////////////////////
                if (parser.parse(options)) {
                    switch (command) {
                        case "echo"://echo ahmed sayed hassan > file
                            if (Arrays.toString(args).contains(">") && args[args.length - 2].equals(">")) {
                                String sub = "";
//                            System.out.println(args[args.length-2]);
                                for (int i = 0; i < args.length - 2; i++) {
                                    sub += args[i];
                                    if (i != args.length - 3) {
                                        sub += " ";
                                    } else if (i == args.length - 2) {
                                        break;
                                    }
                                }
//                            System.out.println(sub);
                                T.write(T, "echo", args[args.length - 1], sub);

                            } else if (Arrays.toString(args).contains(">>") && args[args.length - 2].equals(">>")) {
                                String sub = "";
                                for (int i = 0; i < args.length - 2; i++) {
                                    sub += args[i];
                                    if (i != args.length - 3) {
                                        sub += " ";
                                    } else if (i == args.length - 2) {
                                        break;
                                    }
                                }
                                T.writeAppend(T, "echo", args[args.length - 1], sub);
                            }
                            String e = "";
                            for (int i = 0; i < args.length; i++) {
                                e += args[i];
                                e += " ";
                            }
                            T.echo(e);
                            break;
                        case "pwd"://done
                            if (Arrays.toString(args).contains(">") && args[0].equals(">")) {
                                T.write(T, "pwd", args[1], "");
                            } else if (Arrays.toString(args).contains(">>") && args[0].equals(">>")) {
                                T.writeAppend(T, "pwd", args[1], "");
                            } else {
                                T.echo(T.pwd());
                            }
                            break;
                        case "cd"://done
                            if (args.length != 0 && args[0].equals("..")) {
                                String[] array = new String[1];
                                array[0] = "..";
                                T.cd(array);
                            } else {
                                if (args.length == 0) {
                                    T.echo(T.cd());
                                } else {
                                    String[] array = new String[1];
                                    String p = Arrays.toString(args);
                                    p = p.substring(1, p.length() - 1);
                                    array[0] = p;
                                    T.cd(array);
                                }
                            }
                            break;
                        case "ls":
                            if (args.length != 0 && args[0].equals("-r")) {
                                T.lsr();
                            } else if (args.length == 0) {
                                T.ls();
                            } else if (Arrays.toString(args).contains(">") && args[0].equals(">")) {
                                T.write(T, "ls", args[1], "");
                            } else if (Arrays.toString(args).contains(">>") && args[0].equals(">>")) {
                                T.writeAppend(T, "ls", args[1], "");
                            }
                            break;
                        case "exit":
                            return;
                        case "mkdir":
                            String p = "";
                            for (int i = 0; i < args.length; i++) {
                                p += args[i];
                                if (i != args.length - 1) {
                                    p += " ";
                                } else if (i == args.length - 1) {
                                    break;
                                }
                            }
                            T.mkdir(p);
                            break;
                        case "rmdir":
                            String delete = "";
                            for (int i = 0; i < args.length; i++) {
                                delete += args[i];
                                if (i != args.length - 1) {
                                    delete += " ";
                                } else if (i == args.length - 1) {
                                    break;
                                }
                            }
//                        System.out.println(delete);
                            T.rmdir(delete);
                            break;
                        case "touch":
                            String touch = "";
                            for (int i = 0; i < args.length; i++) {
                                touch += args[i];
                                if (i != args.length - 1) {
                                    touch += " ";
                                } else if (i == args.length - 1) {
                                    break;
                                }
                            }
//                        System.out.println(touch);
                            T.touch(touch);
                            break;
                        case "cp":
                            if (args.length != 0 && args[0].equals("-r")) {
                                File f1 = new File(args[1]);
                                File f2 = new File(args[2]);
                                T.cpr(f1, f2);
                            } else {
                                File f1 = new File(args[0]);
                                File f2 = new File(args[1]);
                                T.cp(f1, f2);
                            }
                            break;
                        case "rm":
                            String rm = "";
                            for (int i = 0; i < args.length; i++) {
                                rm += args[i];
                                if (i != args.length - 1) {
                                    rm += " ";
                                } else if (i == args.length - 1) {
                                    break;
                                }
                            }
//                        System.out.println(rm);
                            T.rm(rm);
                            break;
                        case "cat":
                            if (args.length == 1) {
                                T.cat(args[0]);
                            } else if (args.length == 2) {
                                T.cat(args[0], args[1]);
                            }
                            break;
                        default:
                            T.echo("invalid!");
                            break;
                    }
                } else {
                    echo("this command incorrect ");
                    echo("list of available commands");
                    echo(parser.help());
                }

            }
        }

    }

    public static void main(String[] args) throws IOException {
        Terminal T = new Terminal();
        T.chooseCommandAction();
    }
}