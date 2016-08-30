package gerar;

import creators.src.ActionGenerator;
import creators.src.DaoGenerator;
import creators.src.ValidatorGenerator;
import creators.web.CreateGenerator;
import creators.web.ExploreGenerator;
import creators.web.FormGenerator;
import creators.web.UpdateGenerator;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import utils.VelocityUtil;

public class Example {

//    private VelocityEngine ve;
    public Example() {}
    
    public void compactoExample(String templateFile) {
        
        
        
        Map<String, Object> context = new HashMap();
        context.put("nomeDoModulo", "global");
        context.put("nomeDoBean", "Banco");
        context.put("nomeDaVariavelDoBean", "banco");
        
        CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

        System.out.println("Result :\n");
        System.out.println(result.toString());
//        try {
//            
//
//            BufferedWriter writer = writer = new BufferedWriter(
//                    new OutputStreamWriter(System.out));
//
//            if (template != null) {
//                template.merge(context, writer);
//            }
//
//            /*
//             *  flush and cleanup
//             */
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
    
    public void example(String templateFile) {
        try {

            Properties p = new Properties();
            p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//            VelocityEngine ve = new VelocityEngine();
//            ve.init(p);
            /* Ou */
//            VelocityEngine ve = new VelocityEngine();
//            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//            ve.init();

//            Velocity.init("velocity.properties");
            /* OU */
            Velocity.init(p);
            VelocityContext context = new VelocityContext();
//            context.put("list", getNames());
            context.put("nomeDoModulo", "global");
            context.put("nomeDoBean", "Banco");
            context.put("nomeDaVariavelDoBean", "banco");

            Template template = null;

            try {
                template = Velocity.getTemplate(templateFile);
//                template = ve.getTemplate(templateFile);
            } catch (ResourceNotFoundException rnfe) {
                System.out.println("gerar.Example : error : cannot find template " + templateFile);
            } catch (ParseErrorException pee) {
                System.out.println("gerar.Example : Syntax error in template " + templateFile + ":" + pee);
            }

            BufferedWriter writer = writer = new BufferedWriter(
                    new OutputStreamWriter(System.out));

            if (template != null) {
                template.merge(context, writer);
            }

            /*
             *  flush and cleanup
             */
            writer.flush();
            writer.close();

//            Template t = ve.getTemplate("templates/email_html_new.vm");
//        StringWriter writer = new StringWriter();
//        t.merge(context, writer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList getNames() {
        ArrayList list = new ArrayList();

        list.add("ArrayList element 1");
        list.add("ArrayList element 2");
        list.add("ArrayList element 3");
        list.add("ArrayList element 4");

        return list;
    }
    
    public static void geraEntidade(String classPath){
        ActionGenerator ag = new ActionGenerator();
        ag.generate(classPath);
        DaoGenerator dg = new DaoGenerator();
        dg.generate(classPath);
        ValidatorGenerator vg = new ValidatorGenerator();
        vg.generate(classPath);
        ExploreGenerator eg = new ExploreGenerator();
        eg.generate(classPath);
        CreateGenerator cg = new CreateGenerator();
        cg.generate(classPath);
        UpdateGenerator ug = new UpdateGenerator();
        ug.generate(classPath);
        FormGenerator fm = new FormGenerator();
        fm.generate(classPath);
    }
    
/*
    
    public static void main(String[] args) {
        gerar.Example t = new gerar.Example();
        VelocityInitializer.getInstance().initializeVelocity();
//        geraEntidade("modules.tjpi.beans.infraestrutura.TelecomProblema");
        geraEntidade("modules.infraestrutura.beans.TelecomProblema");
        
        
        
        
        
        
//        t.compactoExample("templates/example.vm");
//        t.compactoExample("templates/src/action.vm");
//        t.example("templates/example.vm");
//        gerar.Example t = new gerar.Example("templates/src/action.vm");
//        testeReflection();
        
    }

    */
    public static void testeReflection() {
        Class<?> cl = null;

        try {
            cl = Class.forName("modules.tjpi.beans.TipoFuncao");
            for (Constructor<?> c : cl.getConstructors()) {
                System.out.println("Construtor da Classe: " + c.getName());

                for (Class<?> attType : c.getParameterTypes()) {
                    System.out.println(" +--- Tipos de Argumentos do construtor: " + attType.getSimpleName());
                }
            }

            System.out.println("\n");
            for (Field f : cl.getDeclaredFields()) {
                for (Annotation a : f.getDeclaredAnnotations()) {
                    System.out.println("Annotation: " + a.annotationType().getCanonicalName());
                }
                switch (f.getModifiers()) {
                    case Modifier.PUBLIC:
                        System.out.println(Modifier.toString(Modifier.PUBLIC)
                                + " " + f.getType().getSimpleName()
                                + " " + f.getName() + ";");
                        break;
                    case Modifier.PRIVATE:
                        System.out.println(Modifier.toString(Modifier.PRIVATE)
                                + " " + f.getType().getSimpleName()
                                + " " + f.getName() + ";");
                        break;
                    case Modifier.FINAL:
                        System.out.println(Modifier.toString(Modifier.FINAL)
                                + " " + f.getType().getSimpleName()
                                + " " + f.getName() + ";");
                        break;
                    default:
                        break;
                }
            }

            System.out.println("\n");
            for (Method m : cl.getDeclaredMethods()) {
                System.out.println("Nome do método: " + m.getName());
                for (Annotation annotation : m.getDeclaredAnnotations()) {
                    System.out.println("  +--- Anotação do método: " + annotation.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionCreator() {
        String templateFile = "templates/src/action.vm";
        VelocityEngine ve;
//        VelocityInitializer.getInstance().initializeVelocity();
        try {

            ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();

            VelocityContext context = new VelocityContext();

            context.put("nomeDoModulo", "global");
            context.put("nomeDoBean", "Banco");
            context.put("nomeDaVariavelDoBean", "banco");

            Template template = null;

            try {
//                template = Velocity.getTemplate(templateFile);
                template = ve.getTemplate(templateFile);
            } catch (ResourceNotFoundException rnfe) {
                System.out.println("gerar.Example : error : cannot find template " + templateFile);
            } catch (ParseErrorException pee) {
                System.out.println("gerar.Example : Syntax error in template " + templateFile + ":" + pee);
            }

            BufferedWriter writer = writer = new BufferedWriter(
                    new OutputStreamWriter(System.out));

            if (template != null) {
                template.merge(context, writer);
            }

            /*
             *  flush and cleanup
             */
            writer.flush();
            writer.close();

//            Template t = ve.getTemplate("templates/email_html_new.vm");
//        StringWriter writer = new StringWriter();
//        t.merge(context, writer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
