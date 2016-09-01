package gerar;

import creators.src.ActionGenerator;
import creators.src.AuxiliarGenerator;
import creators.src.DaoGenerator;
import creators.src.ModuleManagerGenerator;
import creators.src.RolesEnumGenerator;
import creators.src.ValidatorGenerator;
import creators.web.CreateGenerator;
import creators.web.ExploreGenerator;
import creators.web.FormGenerator;
import creators.web.FunctionsGenerator;
import creators.web.LayoutGenerator;
import creators.web.MenuGenerator;
import creators.web.SubmenuGenerator;
import creators.web.UpdateGenerator;
import modules.estoque.beans.ClasseMaterial;
import modules.estoque.beans.Material;
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
import utils.VelocityInitializer;
import utils.VelocityUtil;

public class Example {

    public Example() {}

    public static void geraEntidade(String beanPath){
        // Java
        ActionGenerator ag = new ActionGenerator();
        ag.generate(beanPath);
        DaoGenerator dg = new DaoGenerator();
        dg.generate(beanPath);
        ValidatorGenerator vg = new ValidatorGenerator();
        vg.generate(beanPath);

        // Web
        ExploreGenerator eg = new ExploreGenerator();
        eg.generate(beanPath);
        CreateGenerator cg = new CreateGenerator();
        cg.generate(beanPath);
        UpdateGenerator ug = new UpdateGenerator();
        ug.generate(beanPath);
        FormGenerator fm = new FormGenerator();
        fm.generate(beanPath);
    }

    public static void main(String[] args) {
        VelocityInitializer.getInstance().initializeVelocity();

        ArrayList<String> beanList = new ArrayList();
        beanList.add(Material.class.getName());
        beanList.add(ClasseMaterial.class.getName());
        for (String bean : beanList) {
            geraEntidade(bean);
        }

        // Java
        ModuleManagerGenerator mg = new ModuleManagerGenerator();
        mg.generate(beanList);
        RolesEnumGenerator re = new RolesEnumGenerator();
        re.generate(beanList);
        AuxiliarGenerator auxiliar = new AuxiliarGenerator();
        auxiliar.generate(beanList.get(0));

        // Web
        MenuGenerator menu = new MenuGenerator();
        menu.generate(beanList);
        SubmenuGenerator submenu = new SubmenuGenerator();
        submenu.generate(beanList);
        FunctionsGenerator fg = new FunctionsGenerator();
        fg.generate(beanList);
        LayoutGenerator tg = new LayoutGenerator();
        tg.generate(beanList);
    }
}
