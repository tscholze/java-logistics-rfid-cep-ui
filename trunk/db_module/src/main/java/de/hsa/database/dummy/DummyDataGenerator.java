package de.hsa.database.dummy;

import de.hsa.database.entities.Event;
import de.hsa.database.entities.Product;
import de.hsa.database.entities.Rule;
import de.hsa.database.entities.Tag;
import de.hsa.database.services.EventService;
import de.hsa.database.services.ProductService;
import de.hsa.database.services.RuleService;
import de.hsa.database.services.TagService;
import de.hsa.database.util.TagEpcList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/31/12
 * Time: 2:52 PM
 */

public class DummyDataGenerator
{

    public static void generateDataBaseEntries2()
    {
        TagService tagService = new TagService();
        ProductService productService = new ProductService();
        EventService eventService = new EventService();
        RuleService ruleService = new RuleService();


        // ------------ Rule ------------ //
        System.out.println("Pruefe ob Rule existiert.");
        Rule rule = null;
        if (ruleService.checkIsUnique("Hanuta ist boese"))
        {
            System.out.println("-- Rule nicht da, lege sie an.");
            // Rule welche alles wa skommt weiter gibt.
            rule = new Rule("Hanuta ist boese");
            rule.setSyntax("select * from TagEvent");
            rule.setDescription("Gibt alles aus dem Stream was kommt.");
            rule.setActive(true);
            ruleService.insert(rule);
        }
        else
        {
            System.out.println("-- Rule  da, lade sie.");
            rule = ruleService.loadByName("Hanuta ist boese");
        }

        // ------------ Event ------------ //
        System.out.println("Lege Event an.");

        Event event = new Event(rule);
        eventService.insert(event);

        // ------------ GummyBears ------------ //
        System.out.println("Lege GBs an.");
        Product gbProduct = new Product("Gummibaeren");
        productService.insert(gbProduct);
        List<String>gummyList = TagEpcList.getGummyBearEpcList();
        
        for (String epc : gummyList)
        {
            tagService.insert(new Tag(epc, gbProduct, event));
        }

        // ------------ Hanutas ------------ //
        System.out.println("Lege Hanutas an.");
        Product hanutaProduct = new Product("Kinder Country");
        productService.insert(hanutaProduct);
        List<String>hanutaList = TagEpcList.getHanutaEpcList();

        for (String epc : hanutaList)
        {
            tagService.insert(new Tag(epc, hanutaProduct, event));
        }

        // ------------ Knoppers ------------ //
        System.out.println("Lege Knoppers an.");
        Product knoppers = new Product("Knoppers");
        productService.insert(knoppers);
        List<String> knoppersList = TagEpcList.getKnoppersEpcList();

        for (String epc : knoppersList)
        {
            tagService.insert(new Tag(epc, knoppers, event));
        }

        // ------------ Duplo ------------ //
        System.out.println("Lege Duplo an.");
        Product duplo = new Product("Pseudo Duplo");
        productService.insert(duplo);
        List<String> duploList = TagEpcList.getDuploEpcList();

        for (String epc : duploList)
        {
            tagService.insert(new Tag(epc, duplo, event));
        }

        // ------------ Snickers ------------ //
        System.out.println("Lege Snickers an.");
        Product snickers = new Product("Ritter Sport Mini");
        productService.insert(snickers);
        List<String> snickersList = TagEpcList.getSnickersEpcList();

        for (String epc : snickersList)
        {
            tagService.insert(new Tag(epc, snickers, event));
        }

        // ------------ Mars ------------ //
        System.out.println("Lege Mars an.");
        Product mars = new Product("Pseudo Twix");
        productService.insert(mars);
        List<String> marsList = TagEpcList.getMarsEpcList();

        for (String epc : marsList)
        {
            tagService.insert(new Tag(epc, mars, event));
        }

        // ------------ Bonbons ------------ //
        System.out.println("Lege Bonbons an.");
        Product bonbon = new Product("Lollipops");
        productService.insert(bonbon);
        List<String> bonbonList = TagEpcList.getBonbonEpcList();

        for (String epc : bonbonList)
        {
            tagService.insert(new Tag(epc, bonbon, event));
        }

        // ------------ Besucher 1 ------------ //
        System.out.println("Lege Besucher 1 an.");
        Product visitor1 = new Product("Besucher 1");
        productService.insert(visitor1);
        tagService.insert(new Tag(TagEpcList.getVisitor1Epc(), visitor1, event));

        // ------------ Besucher 2 ------------ //
        System.out.println("Lege Besucher 2 an.");
        Product visitor2 = new Product("Besucher 2");
        productService.insert(visitor2);
        tagService.insert(new Tag(TagEpcList.getVisitor2Epc(), visitor2, event));

        // ------------ Besucher 3 ------------ //
        System.out.println("Lege Besucher 3 an.");
        Product visitor3 = new Product("Besucher 3");
        productService.insert(visitor3);
        tagService.insert(new Tag(TagEpcList.getVisitor3Epc(), visitor3, event));
    }

    @Deprecated
    public static void generateDatabaseEntries()
    {
        TagService tagService = new TagService();
        ProductService productService = new ProductService();
        EventService eventService = new EventService();
        RuleService ruleService = new RuleService();

        List<String> epcList = TagEpcList.getEpcList();

        // 8x Hanuta
        int startHanuta = 1;
        int endHanuta = 8;

        // 8x Knoppers
        int startKnoppers = 9;
        int endKnoppers = 17;

        // 4x einfacher Arbeiter
        int startSimpleWorker = 18;
        int endSimpleWorker = 21;

        // 1x Kappo
        int startBossWorker = 22;
        int endBossWorker = 22;

        // ------------ Rule ------------ //
        System.out.println("Pruefe ob Rule existiert.");
        Rule rule = null;
        if (ruleService.checkIsUnique("Hanuta ist boese"))
        {
            System.out.println("-- Rule nicht da, lege sie an.");
            // Rule welche alles wa skommt weiter gibt.
            rule = new Rule("Hanuta ist boese");
            rule.setSyntax("select * from TagEvent");
            rule.setDescription("Gibt alles aus dem Stream was kommt.");
            rule.setActive(true);
            ruleService.insert(rule);
        }
        else
        {
            System.out.println("-- Rule  da, lade sie.");
            rule = ruleService.loadByName("Hanuta ist boese");
        }

        // ------------ Event ------------ //
        System.out.println("Lege Event an.");

        Event event = new Event(rule);
        eventService.insert(event);

        // ------------ Hanuta ------------ //
        System.out.println("Lege 8x Hanuta Tag-Objekte inkl. Product an.");
        Product hanutaProduct = new Product("Hanuta");
        productService.insert(hanutaProduct);
        for (int i = startHanuta; i <= endHanuta; i++)
        {
            tagService.insert(new Tag(epcList.get(i), hanutaProduct, event));
        }

        // ------------ Knoppers ------------ //
        System.out.println("Lege 8x Knoppers Tag-Objekte inkl. Product an.");
        Product knoppersProduct = new Product("Knoppers");
        productService.insert(knoppersProduct);
        for (int i = startKnoppers; i <= endKnoppers; i++)
        {
            tagService.insert(new Tag(epcList.get(i), knoppersProduct, event));
        }

        // ------------ einfacher Mensch ------------ //
        System.out.println("Lege 4x einfacher Mensch Tag-Objekte inkl. Product an.");
        Product humanProduct = new Product("einfacher Arbeiter");
        productService.insert(humanProduct);
        for (int i = startSimpleWorker; i <= endSimpleWorker; i++)
        {
            tagService.insert(new Tag(epcList.get(i), humanProduct, event));
        }

        // ------------ Boss Mensch ------------ //
        System.out.println("Lege 1x Boss Mensch Tag-Objekt inkl. Product an.");
        Product bossHumanProduct = new Product("Kappo Arbeiter");
        productService.insert(bossHumanProduct);
        for (int i = startBossWorker; i <= endBossWorker; i++)
        {
            tagService.insert(new Tag(epcList.get(i), bossHumanProduct, event));
        }

        // ------------ Hanuta ------------ //
        System.out.println("Lege Fakeprodukt an Product an.");
        Product fakeProduct = new Product("Lakritze");
        productService.insert(fakeProduct);
        
       
        
        for (int i = endBossWorker+1; i < epcList.size(); i++)
        {
            tagService.insert(new Tag(epcList.get(i), fakeProduct, event));
        }
    }
}
