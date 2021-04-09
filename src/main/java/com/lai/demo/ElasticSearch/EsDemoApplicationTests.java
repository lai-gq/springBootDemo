package com.lai.demo.ElasticSearch;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * @Description:创建索引，会根据Item类的@Document注解信息来创建
     * @Author: https://blog.csdn.net/chen_2890
     * @Date: 2018/9/29 0:51
     */
    @RequestMapping(value = "/testCreateIndex",method = RequestMethod.GET)
    public void testCreateIndex() {
        System.out.println("ES=====testCreateIndex开始===");
        elasticsearchTemplate.createIndex(Item.class);//根据类的信息自动生成
        //elasticsearchTemplate.createIndex("",Item.class);//也可以手动指定indexName和Settings
        System.out.println("ES=====testCreateIndex结束===");
    }

    /**
     * @Description:删除索引
     * @Author: https://blog.csdn.net/chen_2890
     * @Date: 2018/9/29 0:50
     */
    @RequestMapping(value = "/testDeleteIndex",method = RequestMethod.GET)
    public void testDeleteIndex() {
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    /**
     * @Description:定义新增方法
     * @Author: http://localhost:8081/springboot/ESinsert
     * 运行insert()，去页面查询看看：
     */
    @RequestMapping(value = "/ESinsert",method = RequestMethod.GET)
    public void insert() {
        Item item = new Item(1L, "小米手机7", " 手机","小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
        System.out.println("insert添加成功");
    }
    /**
     * @Description:定义批量新增方法
     * @Author: http://localhost:8081/springboot/ESinsertList
     */
    @RequestMapping(value = "/ESinsertList",method = RequestMethod.GET)
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "三星S560", " 手机", "三星", 5899.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V30", " 手机", "华为", 1199.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(6L, "华为P30", " 手机", "锤子", 3259.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(7L, "小米6S", " 手机", "小米", 4497.00, "http://image.baidu.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
        System.out.println("insert批量添加成功");
    }

    /**
     * @Description:定义修改方法
     * @Author: https://blog.csdn.net/chen_2890
     */
    @RequestMapping(value = "/ESupdate",method = RequestMethod.GET)
    public void update(){
        Item item = new Item(1L, "苹果XSMax", " 手机","小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);//elasticsearch中本没有修改，它的修改原理是该是先删除在新增，修改和新增是同一个接口，区分的依据就是id。
        System.out.println("update修改成功");
    }

    /**
     * @Description:定义查询方法,含对价格的降序、升序查询
     * @Author: http://localhost:8081/springboot/testQueryAll
     */
    @RequestMapping(value = "/testQueryAll",method = RequestMethod.GET)
    public void testQueryAll(){
        // 查找所有
        //Iterable<Item> list = this.itemRepository.findAll();
        // 对某字段排序查找所有 Sort.by("price").descending() 降序，Sort.by("price").ascending():升序
        Iterable<Item> list = this.itemRepository.findAll(Sort.by("price").ascending());
        for (Item item:list){
            System.out.println(item);
        }
    }

    /**
     * @Description:matchQuery底层采用的是词条匹配查询(自定义查询)
     * @Author: http://localhost:8081/springboot/testMatchQuery
     */
    @RequestMapping(value = "/testMatchQuery",method = RequestMethod.GET)
    public void testMatchQuery(){
        // 构建查询条件(Spring提供的一个查询条件构建器，帮助构建json格式的请求体)
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        //queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机3"));
        //queryBuilder.withQuery(QueryBuilders.termQuery("price",99.0));//termQuery:功能更强大，除了匹配字符串以外，还可以匹配
        queryBuilder.withQuery(QueryBuilders.fuzzyQuery("title","三星"));//模糊查询
        //QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title","华为")).must(QueryBuilders.matchQuery("brand","华为"))//布尔查询
        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("获取的总条数:" + total);
        for (Item item : items) {
            System.out.println(item);
        }
    }

    /**
     * @Description:分页查询
     * @Author: https://blog.csdn.net/chen_2890
     */
    @RequestMapping(value = "/searchByPage",method = RequestMethod.GET)
    public void searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        // 分页：
        int page = 2;
        int size = 3;
        queryBuilder.withPageable(PageRequest.of(page,size));
        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);// 总条数
        System.out.println("总页数 = " + items.getTotalPages()); // 总页数
        System.out.println("当前页：" + items.getNumber()); // 当前页
        System.out.println("每页大小：" + items.getSize());// 每页大小
        for (Item item : items) {
            System.out.println(item);
        }
    }


    /**
     * @Description:按照品牌brand进行分组
     * @Author: https://blog.csdn.net/chen_2890
     */
    @RequestMapping(value = "/testAgg",method = RequestMethod.GET)
    public void testAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }

    }

    /**
     * @Description:嵌套聚合，求平均值
     * @Author: https://blog.csdn.net/chen_2890
     */
    @Test
    public void testSubAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }
}