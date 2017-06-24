package com.cherkasov.classloader;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/*
Аргументом для класса MyClassLoader является абсолютный путь к пакету,
например, "C:\java\data\second".
Имя пакета может содержать File.separator.
В этом пакете находятся только скомпилированные классы.
*/
public class MyClassLoader {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;
    public MyClassLoader(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader solution = new MyClassLoader("C:\\java\\projects\\JavaRushHomeWork\\out\\production\\JavaRushHomeWork\\com\\javarush\\test\\level36\\lesson10\\bonus01\\data\\second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
//        String modulePath = pathToAnimals;
        /**
         * Создаем загрузчик модулей.
         */
        ModuleLoader loader = new ModuleLoader(packageName, ClassLoader.getSystemClassLoader());

        /**
         * Получаем список доступных модулей.
         */
        File dir = new File(packageName);
        String[] modules = dir.list();

//        System.out.println("Modules: " + Arrays.toString(modules));

        /**
         * Загружаем и исполняем каждый модуль.
         */

//        Set<Animal> set = new HashSet<>();

//        System.out.println("Загружаем и исполняем каждый модуль");

        for (String module : modules) {
            try {
                String moduleName = module.split(".class")[0];
                Class clazz = loader.loadClass(moduleName);


                Class[] interfaces = clazz.getInterfaces();
                for (Class anInterface : interfaces) {
//                    System.out.println(anInterface);
//                    System.out.println(anInterface.getSimpleName());
                    if (anInterface.getSimpleName().equals("HiddenClass") ) {
//                        Animal newObject = (Animal) clazz.newInstance();
                        hiddenClasses.add(clazz);
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

//        System.out.println(hiddenClasses);
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {

        for (Class hiddenClass : hiddenClasses) {
            if (hiddenClass.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                try {
                    Constructor[] constructors = hiddenClass.getDeclaredConstructors();
                    for (Constructor constructor : constructors) {
                        Class[] paramTypes = constructor.getParameterTypes();
                        if (paramTypes.length == 0) {
                            constructor.setAccessible(true);
                            HiddenClass result = (HiddenClass) constructor.newInstance();
//                            constructor.setAccessible(false);
                            return result;
                        }
                    }
//                    hiddenClass.getConstructors()[0].setAccessible(true);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    public static class ModuleLoader extends ClassLoader {

        /**
         * Путь до директории с модулями.
         */
        private String pathtobin;

        public ModuleLoader(String pathtobin, ClassLoader parent) {

            super(parent);
            this.pathtobin = pathtobin;
        }

        @Override
        public Class<?> findClass(String className) throws ClassNotFoundException {

            try {
                /**
                 * Получем байт-код из файла и загружаем класс в рантайм
                 */
                byte b[] = fetchClassFromFS(pathtobin + File.separator + className + ".class");
                return defineClass(null, b, 0, b.length);

            } catch (FileNotFoundException ex) {
                return super.findClass(className);
            } catch (IOException ex) {
                return super.findClass(className);
            }

        }

        private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException {

            InputStream is = new FileInputStream(new File(path));

            // Get the size of the file
            long length = new File(path).length();

            if (length > Integer.MAX_VALUE) {
                // File is too large
            }

            // Create the byte array to hold the data
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + path);
            }

            // Close the input stream and return bytes
            is.close();
            return bytes;

        }

    }
}
