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
import modules.estoque.beans.UnidadeMedida;
import utils.VelocityInitializer;

import java.util.ArrayList;

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
        beanList.add(UnidadeMedida.class.getName());
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