package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.futurepages.annotations.ModuleMakeAttribute;
import org.futurepages.util.The;

import javax.persistence.Temporal;

/**
 *
 * @author Jorge Rafael
 */
public class GenerateUtils {

    public static String criaArquivo(CharSequence charSequence, String diretorio, String nomeArquivo) throws IOException {

        FileWriter fileWriter = null;
        File fileDir = new File(diretorio);
        File arquivo = null;
        try {
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            arquivo = new File(fileDir.getPath() +"/"+ nomeArquivo);
            fileWriter = new FileWriter(arquivo);
            fileWriter.write(charSequence.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Arquivo gerado: " + arquivo.getPath();
    }

    public static String[] caminhoClasse(String nome) {
        return nome.split("\\.");
    }

    public static String atributoChave(Class<?> c) {
        for (Field f : c.getDeclaredFields()) {
            for (Annotation a : f.getDeclaredAnnotations()) {
                if (a.annotationType().getCanonicalName().equalsIgnoreCase("javax.persistence.Id")) {
                    return f.getName();
                }
            }
        }
        return null;
    }

    public static ArrayList listMapAtributoTipo(Class<?> c) {
        ArrayList atributoList = new ArrayList();
        for (Field f : c.getDeclaredFields()) {

            HashMap map = new HashMap();
            String tipo = f.getType().getSimpleName();

            String nameForForm = "";
            String nameForExplore = "";
            boolean showInExplore = true;
            boolean createSelect = false;
            if(f.isAnnotationPresent(ModuleMakeAttribute.class)){
                ModuleMakeAttribute mma = f.getAnnotation(ModuleMakeAttribute.class);
                nameForForm = mma.nameForForm();
                nameForExplore = mma.nameForExplore();
                showInExplore = mma.showInExplore();
                if(tipo.equals("Calendar")){
                    tipo = tipo + mma.useCalendarLike().name();
                }
//                createSelect = mma.createSelect();
            }

            map.put("nome", f.getName());
            map.put("nomeCapitalized", The.capitalizedWord(f.getName()));
            map.put("tipo", tipo);
            map.put("nameForForm", nameForForm);
            map.put("nameForExplore", nameForExplore);
            map.put("showInExplore", showInExplore);
            map.put("createSelect", createSelect);
            atributoList.add(map);
        }
        return atributoList;
    }

    public static ArrayList listMapBeans(ArrayList<String> beanList) throws ClassNotFoundException {
        ArrayList beanMapList = new ArrayList();
        for (String bean : beanList) {
            Class<?> classe = Class.forName(bean);
            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            Map<String, String> map = new HashMap();
            map.put("nomeDoBean", nomes[nomes.length - 1]);
            map.put("nomeDaVariavelDoBean", The.uncapitalizedWord(nomes[nomes.length - 1]));
            beanMapList.add(map);
        }
        return beanMapList;
    }
}
