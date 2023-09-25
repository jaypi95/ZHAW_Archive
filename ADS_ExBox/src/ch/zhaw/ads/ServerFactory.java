package ch.zhaw.ads; /**
 * ch.zhaw.ads.ServerFactory -- Praktikum Experimentierkasten --
 *
 * @author K. Rege
 * @version 1.0 -- Factory zur Erstellung von Server Objekten
 * @version 2.0 -- Dynamisches Nachladen
 * @version 2.01 -- Fix deprecated Functions
 */


import java.io.*;


/* Classloader that handles file path of class
 */
class MyClassLoader extends ClassLoader {
	private String path;

	MyClassLoader(ClassLoader parent) {
		super(parent);
	}

    /* @name filename of class
       return content of file as array of bytes; if file does not exist return null
    */

	private byte[] getBytes(String name) {
		try {
			RandomAccessFile file = new RandomAccessFile(name, "r");
			byte data[] = new byte[(int) file.length()];
			file.readFully(data);
			file.close();
			return data;
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * @name filename of class
	 */
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		// System.out.println("load:" + name + " " + resolve);
		Class<?> clazz;
		byte[] classData = getBytes(name);
		if (classData != null) {
			clazz = defineClass(null, classData, 0, classData.length);
			path = name.substring(0,
					name.length() - clazz.getName().length() - ".class".length());
			return clazz;
		}
		if (!resolve) {
			classData = getBytes(
					path + name.replace(".", File.separator) + ".class");
			if (classData != null) {
				return defineClass(null, classData, 0, classData.length);
			}
		}
		return findSystemClass(name);
	}
}


public class ServerFactory {

	public static Class<?> loadClass(String name)  throws Exception {
		MyClassLoader myClassLoader = new MyClassLoader(
				MyClassLoader.class.getClassLoader());
		Class<?> clazz = myClassLoader.loadClass(name, true);
		return clazz;
	}

	public static CommandExecutor createServer(String name) throws Exception {
		return (CommandExecutor) loadClass(name).getConstructor(new Class[]{}).newInstance();
	}
}