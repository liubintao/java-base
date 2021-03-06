package com.robust.basis.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: robust
 * @CreateDate: 2019/1/17 19:21
 * @Version: 1.0
 */
public class ReadBigFile {

    static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\bintao\\Desktop\\log");
        execute(path);
    }

    private static void execute(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Files.list(path).forEach(p -> {
                singleExecute(p);
            });
        } else {
            singleExecute(path);
        }
    }

    private static void singleExecute(Path path) {
        try {
            FileChannel channel = FileChannel.open(path);
            int len = (int) channel.size();
            MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                    len);
            byte[] b = new byte[1024];
            long begin = System.currentTimeMillis();

            for (int offset = 0; offset < len; offset += 1024) {

                if (len - offset > BUFFER_SIZE) {
                    buff.get(b);
                } else {
                    buff.get(new byte[len - offset]);
                }
                process(b);
            }
            long end = System.currentTimeMillis();
            System.out.println("time is:" + (end - begin));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void process(byte[] bytes) {
        String str = new String(bytes);
        String[] ss = str.split("\n");
        Stream.of(ss).filter(s ->
                        s.contains("IMS00017")
//                                        && s.contains("20190116114526")
//                        s.contains("IDC001-18.73.51.3-inmanage-1547610949608P8lOBzI")
        ).forEach(str1 -> {
            System.out.println(str1);
        });
    }
}
