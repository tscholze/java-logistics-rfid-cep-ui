package de.hsa.database.services;

import de.hsa.database.dummy.DummyDataGenerator;
import de.hsa.database.entities.Product;
import de.hsa.database.entities.Rule;
import de.hsa.database.entities.Tag;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 6:07 PM
 */
public class DummyService {
    private TagService tagService = new TagService();
    private ProductService productService = new ProductService();
    private RuleService ruleService = new RuleService();
    private EventService eventService = new EventService();

    public void generateDummyData() {
        // DummyDataGenerator.generateDatabaseEntries();
        DummyDataGenerator.generateDataBaseEntries2();
    }

    public void dumpDummyData() {
        tagService.dumpTagList();
        productService.dumpProductList();
        ruleService.dumpRuleList();
        eventService.dumpEventList();
    }
}
