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
import org.futurepages.annotations.ModuleMakeEntity;
import org.futurepages.util.Is;
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

            String nameOnForm = "";
            String nameOnExplore = "";
            boolean showOnExplore = true;
            boolean createSelect = false;
            boolean searchParam = false;
            if(f.isAnnotationPresent(ModuleMakeAttribute.class)){
                ModuleMakeAttribute mma = f.getAnnotation(ModuleMakeAttribute.class);
                nameOnForm = mma.nameOnForm();
                nameOnExplore = mma.nameOnExplore();
                showOnExplore = mma.showOnExplore();
                if(tipo.equals("Calendar")){
                    tipo = tipo + mma.useCalendarLike().name();
                }
                createSelect = mma.createSelect();
                searchParam = mma.searchParam();
            }

            map.put("nome", f.getName());
            map.put("nomeCapitalized", The.capitalizedWord(f.getName()));
            map.put("tipo", tipo);
            map.put("nameOnForm", Is.empty(nameOnForm) ? The.capitalizedWord(f.getName()) : nameOnForm);
            map.put("nameOnExplore", nameOnExplore);
            map.put("showOnExplore", showOnExplore);
            map.put("createSelect", createSelect);
            map.put("searchParam", searchParam);
            atributoList.add(map);
        }

        return atributoList;
    }

    public static ArrayList listMapBeans(ArrayList<String> beanList) throws ClassNotFoundException, NoSuchFieldException {
        ArrayList beanMapList = new ArrayList();
        for (String bean : beanList) {
            Class<?> classe = Class.forName(bean);
            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            Map<String, Object> map = new HashMap();

            map.putAll(GenerateUtils.listMapBeanElements(nomes, classe));

            beanMapList.add(map);
        }
        return beanMapList;
    }

    public static Map<String, Object> listMapBeanElements(String[] nomes, Class<?> c) throws NoSuchFieldException {
        Map<String, Object> map = new HashMap();
        ModuleMakeEntity mme = null;

        String nomeDoBean = nomes[nomes.length - 1];
        String nomeDaVariavelDoBean = The.uncapitalizedWord(nomeDoBean);
        String atributoChave = GenerateUtils.atributoChave(c);

        map.put("nomeDoBean", nomeDoBean);
        map.put("canonicalName", c.getCanonicalName());
        map.put("nomeDoBeanUnCapitalized", The.uncapitalizedWord(nomeDoBean));
        map.put("nomeDaVariavelDoBean", nomeDaVariavelDoBean);
        map.put("atributoChave", atributoChave);
        map.put("atributoChaveCapitalized", The.capitalizedWord(atributoChave));
        map.put("atributoChaveTipo", c.getDeclaredField(atributoChave).getType().getCanonicalName());
        map.put("atributoChaveTipoCapitalized", The.capitalizedWord(c.getDeclaredField(atributoChave).getType().getCanonicalName()));

        if(c.isAnnotationPresent(ModuleMakeEntity.class)){
            mme = c.getAnnotation(ModuleMakeEntity.class);
        }
        String entityName = mme == null ? nomeDoBean : (Is.empty(mme) ? nomeDoBean : mme.name());
        map.put("entityName", entityName);

        map.put("atributoList", GenerateUtils.listMapAtributoTipo(c));

        return map;
    }

    public static Map<String, Object> listMapModuleElements(String[] nomes){

        Map<String, Object> map = new HashMap();
        String nomeDoModulo = null;
        String nomeDoSubmodulo = null;
        if (nomes.length == 5) {
            nomeDoSubmodulo = nomes[nomes.length - 2];
            nomeDoModulo = nomes[nomes.length - 4];
        } else {
            nomeDoModulo = nomes[nomes.length - 3];
        }
//            map.put("list", getNames());
        map.put("nomeDoModulo", nomeDoModulo);
        map.put("nomeDoModuloCapitalized", The.capitalizedWord(nomeDoModulo));
        map.put("nomeDoSubmodulo", nomeDoSubmodulo);
        if(!Is.empty(nomeDoSubmodulo)) {
            map.put("nomeDoSubmoduloCapitalized", The.capitalizedWord(nomeDoSubmodulo));
        }

        return map;
    }
}